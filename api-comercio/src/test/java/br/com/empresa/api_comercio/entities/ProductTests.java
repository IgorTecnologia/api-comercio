package br.com.empresa.api_comercio.entities;

import org.junit.jupiter.api.*;

public class ProductTests {

    @Test
    public void productShouldHaveCorrectStructure() {

        Product entity = new Product();
        Category category = new Category();

        category.setId(1L);
        category.setName("Doces");

        entity.setId(1L);
        entity.setName("Pão sonho");
        entity.setPrice(5.0);
        entity.setDescription("Sabor doce de leite");
        entity.setImgUrl("www.img.com");

        entity.getCategories().add(category);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getCategories());

    }
}
