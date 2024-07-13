package br.com.empresa.api_comercio.tests;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.*;

public class Factory {

    public static Category createdCategory(){

        Category entity = new Category(null,"Frios");
        return entity;
    }

    public static CategoryDTO createdCategoryDTO(){

        CategoryDTO dto = new CategoryDTO(1L,"Frios");
        return dto;
    }

    public static Product createdProduct(){

        Product entity = new Product(null,"Leite", 6.5, "Leite integral", "www.img.com");
        return entity;
    }

    public static ProductDTO createdProductDTO(){

        ProductDTO dto = new ProductDTO(1L,"Leite", 6.5, "Leite integral", "www.img.com");
        return dto;
    }

    public static Role createdRole(){

        Role entity = new Role(null,"Boss");
        return entity;
    }

    public static RoleDTO createdRoleDTO(){

        RoleDTO dto = new RoleDTO(1L,"Manager");
        return dto;
    }
    public static User createdUser(){

        User entity = new User(null, "Bruno", "Abulquerque", "bruno@gmail.com", "123456");
        return entity;
    }

    public static UserDTO createdUserDTO(){

        UserDTO dto = new UserDTO(1L, "Bruno", "Abulquerque", "bruno@gmail.com", "123456");
        return dto;
    }
}
