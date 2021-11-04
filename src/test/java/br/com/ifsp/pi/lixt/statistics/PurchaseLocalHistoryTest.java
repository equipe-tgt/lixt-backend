package br.com.ifsp.pi.lixt.statistics;

import br.com.ifsp.pi.lixt.controller.*;
import br.com.ifsp.pi.lixt.data.business.itemofpurchase.ItemOfPurchase;
import br.com.ifsp.pi.lixt.data.business.itemofpurchase.ItemOfPurchaseService;
import br.com.ifsp.pi.lixt.data.business.list.ListOfItemsService;
import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.business.productoflist.ProductOfListService;
import br.com.ifsp.pi.lixt.data.business.purchase.PurchaseService;
import br.com.ifsp.pi.lixt.data.business.purchaselist.PurchaseList;
import br.com.ifsp.pi.lixt.data.business.purchaselist.PurchaseListService;
import br.com.ifsp.pi.lixt.data.business.purchaselocal.PurchaseLocal;
import br.com.ifsp.pi.lixt.data.business.user.UserService;
import br.com.ifsp.pi.lixt.data.dto.json.ListOfItemsDtoDataJson;
import br.com.ifsp.pi.lixt.dto.*;
import br.com.ifsp.pi.lixt.facade.PurchaseFacade;
import br.com.ifsp.pi.lixt.instantiator.UserDtoInstantior;
import br.com.ifsp.pi.lixt.mapper.*;
import br.com.ifsp.pi.lixt.utils.tests.requests.RequestOauth2;
import br.com.ifsp.pi.lixt.utils.tests.response.RequestWithResponse;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.*;
import org.locationtech.jts.io.ParseException;
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
    private ListOfItemsController listOfItemsController;

    @Autowired
    private ProductController productController;

    @Autowired
    private PurchaseController purchaseController;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ProductOfListController productOfListController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ListOfItemsService listOfItemsService;

    @Autowired
    private ProductOfListService productOfListService;

    @Autowired
    private PurchaseListService purchaseListService;

    @Autowired
    private ItemOfPurchaseService itemOfPurchaseService;

    ObjectMapper mapper = new ObjectMapper();

    private UserDto userDto = new UserDto();
    private List<CategoryDto> categories = new ArrayList<>();
    private List<PurchaseLocalDto> purchaseLocalsDto = new ArrayList<>();
    private List<PurchaseDto> purchasesDto = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<List<PurchaseDto>> puchases = new ArrayList<>();
    private List<ListOfItemsDto> lists = new ArrayList<>();
    private List<ProductOfListDto> productOfListDtoList = new ArrayList<>();

    @BeforeAll
    void createTestConditions() throws Exception {
        userDto = (UserDto) this.authController.register(UserDtoInstantior.createUser(
                "teste1",
                "teste1",
                "teste1@gmail.com",
                "123"),
                null).getBody();

        userDto.setPassword("123");

        this.authController.activeUser(this.userService.findFirstAccesTokenById(userDto.getId()), null);

        String token = RequestOauth2.authenticate(mockMvc, userDto);

        for (CategoryDto category : DataForPurchaseLocalHistory.createCategories()) {
            this.categories.add(this.categoryController.save(category));
        }

        for (ListOfItemsDto listOfItemsDto : DataForPurchaseLocalHistory.createListOfItems(userDto.getId())) {
            //lists.add(this.listOfItemsController.save(listOfItemsDto));
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
            /*productOfListDtoList.add(
                    this.productOfListController.save(productOfListDto)
            );*/
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

            //String json = mapper.writeValueAsString(purchase);
            purchasesDto.add(
                    RequestWithResponse.createPostRequestJson(mockMvc, "/purchase", mapper.writeValueAsString(purchase), this.userDto, PurchaseDto.class)
            );
            /*purchasesDto.add(
                    //this.purchaseController.save(purchase)
                    PurchaseMapper.entityToDto(
                            this.purchaseService.save(PurchaseMapper.dtoToEntity(purchase))
                    )
            );
            List< PurchaseList > purchaseLists = new ArrayList<>();

            for(var i=0; i<purchase.getPurchaseLists().size(); i++) {
                var purchaseListTemp = PurchaseListMapper.dtoToEntity(purchase.getPurchaseLists().get(i));
                purchaseListTemp.setPurchaseId(purchasesDto.get(purchasesDto.size()-1).getId());
                purchaseListTemp.setItemsOfPurchase(null);
                purchaseLists.add(purchaseListTemp);
            }
            purchasesDto.get(purchasesDto.size()-1).setPurchaseLists(
                    (List<PurchaseListDto>) PurchaseListMapper.dtoToEntity(
                            (PurchaseListDto) this.purchaseListService.saveAll(purchaseLists))
            );

            for(var i=0; i<purchase.getPurchaseLists().size(); i++) {

                var purchaseList = PurchaseListMapper.dtoToEntity(purchase.getPurchaseLists().get(i));
                List<ItemOfPurchase> itemsOfPurchase = new ArrayList<>();

                for(var j=0; j<purchaseList.getItemsOfPurchase().size(); j++) {
                    var itemOfPurchaseTemp = purchaseList.getItemsOfPurchase().get(j);
                    itemOfPurchaseTemp.setPurcharseListId(purchasesDto.get(purchasesDto.size()-1).getPurchaseLists().get(i).getId());
                    itemsOfPurchase.add(itemOfPurchaseTemp);
                }

                purchasesDto.get(purchasesDto.size()-1).getPurchaseLists().get(i).setItemsOfPurchase(
                        (List<ItemOfPurchaseDto>) ItemOfPurchaseMapper.entityToDto(
                                (ItemOfPurchase) this.itemOfPurchaseService.saveAll(itemsOfPurchase))
                );
            }*/
        }
    }

    @Test
    @DisplayName("Estatísticas de compra por local")
    void recordByLocal(){
        assertThat(this.purchaseLocalController.getAllPuchaseLocalData(userDto.getId())).isNotEmpty();
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
            this.productOfListController.deleteById(productOfListDto.getId());
        }
        for (Product product : this.products) {
            this.productController.deleteById(product.getId());
        }
        for (ListOfItemsDto listOfItemsDto : lists) {
            this.listOfItemsController.deleteById(listOfItemsDto.getId());
        }
        for (CategoryDto category : this.categories) {
            this.categoryController.deleteById(category.getId());
        }
        this.userService.deleteById(userDto.getId());
    }
}
