package br.com.ifsp.pi.lixt.controller;

import br.com.ifsp.pi.lixt.data.business.globalComment.GlobalComment;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.CategoryDto;
import br.com.ifsp.pi.lixt.dto.GlobalCommentDto;
import br.com.ifsp.pi.lixt.dto.ProductDto;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantior;
import br.com.ifsp.pi.lixt.mapper.GlobalCommentMapper;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Testar endpoints na classe GlobalCommentController")
public class GlobalCommentControllerTest {

    @Autowired
    GlobalCommentController globalCommentController;

    @Autowired
    private CategoryController categoryController;

    private CategoryDto category;

    private ProductDto product;

    private GlobalCommentDto globalCommentDto;

    @Autowired
    private ProductController productController;

    @BeforeAll
    void createGlobalComments() {
        category = categoryController.save(CategoryDto.builder().name("alimento").build());
        product = this.productController.save(ProductMapper.entityToDto(ProductDtoInstantior.createProduct("Arroz", category, MeasureType.KG, 5)));

        globalCommentDto = this.globalCommentController.save(GlobalCommentMapper.entityToDto(new GlobalComment((long) 1, "Comentário global", (long) 1, (long) 1)));
    }

    @Test
    @DisplayName("manipular comentários")
    void getByGlobalCommentId() {

        this.globalCommentDto.setContent("Comentário global atualizado");
        this.globalCommentController.update(this.globalCommentDto, this.globalCommentDto.getId());

        assertAll(
                () -> assertThat(this.globalCommentController.findAll().size()).isEqualTo(1),
                () -> assertThat(this.globalCommentController.findById(this.globalCommentDto.getId()).getContent()).isEqualTo("Comentário global atualizado")
        );

        this.globalCommentController.deleteById(this.globalCommentDto.getId());

        assertThat(this.globalCommentController.findAll().size()).isEqualTo(0);
    }

    @AfterAll
    void deleteProducts() {
        this.productController.deleteById(product.getId());
        this.categoryController.deleteById(category.getId());
    }
}

