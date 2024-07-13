package br.com.empresa.api_comercio.entities;

import org.junit.jupiter.api.*;

public class CategoryTests {

    @Test
    public void categoryShouldHaveCorrectStructure(){

        Category entity = new Category();
        Product product = new Product();

        product.setId(1L);
        product.setName("Pães");
        product.setPrice(8.0);
        product.setDescription("Pães frescos e crocantes");
        product.setImgUrl("www.img.com");

        entity.setId(1L);
        entity.setName("Salgados");

        entity.getProducts().add(product);
        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getName());
        Assertions.assertNotNull(entity.getProducts());
    }
}
