package br.com.ifsp.pi.lixt.statistics;

import br.com.ifsp.pi.lixt.controller.*;
import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.business.productoflist.ProductOfListService;
import br.com.ifsp.pi.lixt.data.business.purchase.PurchaseService;
import br.com.ifsp.pi.lixt.data.business.purchaselocal.PurchaseLocal;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.dto.*;
import br.com.ifsp.pi.lixt.dto.specific.PurchaseLocalDataDto;
import br.com.ifsp.pi.lixt.instantiator.UserDtoInstantiator;
import br.com.ifsp.pi.lixt.mapper.*;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoints de histórico do local da compra")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PurchaseLocalHistoryTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthController authController;

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private PurchaseLocalController purchaseLocalController;

    @Autowired
    private ProductController productController;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ListOfItemsService listOfItemsService;

    @Autowired
    private ProductOfListService productOfListService;


    ObjectMapper mapper = new ObjectMapper();

    private UserDto userDto = new UserDto();
    private UserDto userDto2 = new UserDto();
    private List<CategoryDto> categories = new ArrayList<>();
    private List<PurchaseLocalDto> purchaseLocalsDto = new ArrayList<>();
    private List<PurchaseDto> purchasesDto = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<ListOfItemsDto> lists = new ArrayList<>();
    private List<ProductOfListDto> productOfListDtoList = new ArrayList<>();

    @BeforeAll
    void createTestConditions() throws Exception {
        userDto = (UserDto) this.authController.register(UserDtoInstantiator.createUser(
                "teste1",
                "teste1",
                "teste1@gmail.com",
                "123"),
                null).getBody();

        userDto.setPassword("123");

        this.authController.activeUser(this.userService.findFirstAccesTokenById(userDto.getId()), null);


        for (CategoryDto category : DataForPurchaseLocalHistory.createCategories()) {
            this.categories.add(this.categoryController.save(category));
        }

        for (ListOfItemsDto listOfItemsDto : DataForPurchaseLocalHistory.createListOfItems(userDto.getId())) {
            lists.add(
                    ListOfItemsMapper.entityToDto(
                    this.listOfItemsService.save(ListOfItemsMapper.dtoToEntity(listOfItemsDto)))
            );
        }

        for (Product product : DataForPurchaseLocalHistory.createProducts(this.categories)) {
            products.add(
                    ProductMapper.dtoToEntity(
                            (ProductDto) this.productController.save(ProductMapper.entityToDto(product)).getBody()
                    )
            );
        }

        for (ProductOfListDto productOfListDto :
                DataForPurchaseLocalHistory.createProductsOfList(this.products, this.lists)) {
            productOfListDtoList.add(
                    ProductOfListMapper.entityToDto(
                            this.productOfListService.save(ProductOfListMapper.dtoToEntity(productOfListDto))
                    )
            );
        }

        for (PurchaseLocal local : DataForPurchaseLocalHistory.createLocals()) {
            this.purchaseLocalsDto.add(
                    this.purchaseLocalController.save(PurchaseLocalMapper.entityToDto(local))
            );
        }

        for (PurchaseDto purchase :
                DataForPurchaseLocalHistory.generatePurchases(
                        this.purchaseLocalsDto.stream().map(e -> e.getId()).collect(Collectors.toList()),
                        this.productOfListDtoList,
                        this.lists,
                        this.userDto.getId()
                )) {

            StringBuilder json = new StringBuilder(mapper.writeValueAsString(purchase));
            json.replace(
                    json.lastIndexOf("\"purchaseDate\":")+15,
                    json.indexOf(",\"purchaseLocal\""),
                    "\""+purchase.getPurchaseDate()+"\""
            );

            purchasesDto.add(
                    RequestWithResponse.createPostRequestJson(mockMvc, "/purchase", json.toString(), this.userDto, PurchaseDto.class)
            );
        }
    }

    @Test
    @DisplayName("Estatísticas de compra por local")
    void recordsByLocal(){
        List<PurchaseLocalDataDto> data = this.purchaseLocalController.getAllPuchaseLocalData(userDto.getId());
        assertThat(data).hasSize(5);

        for (PurchaseLocalDataDto localData : data) {
            assertThat(localData).isNotNull();
            assertThat(localData.getPurchaseAmount()).isEqualTo(lists.size());
            assertThat(localData.getAverageValue()).isPositive();
            assertThat(localData.getTotalValue()).isPositive();
        }


    }

    @Test
    @DisplayName("Solicitar estatísticas para usuário sem compras")
    void emptyRecordsByLocal() {
        userDto2 = (UserDto) this.authController.register(UserDtoInstantiator.createUser(
                        "teste2",
                        "teste2",
                        "teste2@gmail.com",
                        "123"),
                null).getBody();

        userDto2.setPassword("123");

        this.authController.activeUser(this.userService.findFirstAccesTokenById(userDto2.getId()), null);

        List<PurchaseLocalDataDto> data = this.purchaseLocalController.getAllPuchaseLocalData(userDto2.getId());

        assertThat(data).isEmpty();
    }

    @AfterAll
    void deleteDataTest() {
        for (PurchaseDto purchase : this.purchasesDto) {
            this.purchaseService.deleteById(purchase.getId());
        }
        for (PurchaseLocalDto local: this.purchaseLocalsDto) {
            this.purchaseLocalController.deleteById(local.getId());
        }
        for (ProductOfListDto productOfListDto : this.productOfListDtoList) {
            this.productOfListService.deleteById(productOfListDto.getId());
        }
        for (Product product : this.products) {
            this.productController.deleteById(product.getId());
        }
        for (ListOfItemsDto listOfItemsDto : lists) {
            this.listOfItemsService.deleteById(listOfItemsDto.getId());
        }
        for (CategoryDto category : this.categories) {
            this.categoryController.deleteById(category.getId());
        }
        this.userService.deleteById(userDto.getId());
        this.userService.deleteById(userDto2.getId());
    }
}
