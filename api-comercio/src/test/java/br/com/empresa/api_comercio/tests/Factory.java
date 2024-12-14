package br.com.empresa.api_comercio.tests;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.*;

import java.util.UUID;

public class Factory {

    public static Category createdCategory(){

        UUID id = UUID.randomUUID();
        Category entity = new Category(id,"Frios");
        return entity;
    }

    public static CategoryDTO createdCategoryDTO(){

        UUID id = UUID.randomUUID();
        CategoryDTO dto = new CategoryDTO(id,"Frios");
        return dto;
    }

    public static Product createdProduct(){

        UUID id = UUID.randomUUID();
        Product entity = new Product(id,"Leite", 6.5, "Leite integral", "www.img.com");
        return entity;
    }

    public static ProductDTO createdProductDTO(){

        UUID id = UUID.randomUUID();
        ProductDTO dto = new ProductDTO(id,"Leite", 6.5, "Leite integral", "www.img.com");
        return dto;
    }

    public static Role createdRole(){

        UUID id = UUID.randomUUID();
        Role entity = new Role(id,"Boss");
        return entity;
    }

    public static RoleDTO createdRoleDTO(){

        UUID id = UUID.randomUUID();
        RoleDTO dto = new RoleDTO(id,"Manager");
        return dto;
    }
    public static User createdUser(){

        UUID id = UUID.randomUUID();
        User entity = new User(id, "Bruno", "Abulquerque", "bruno@gmail.com", "123456");
        return entity;
    }

    public static UserDTO createdUserDTO(){

        UUID id = UUID.randomUUID();
        UserDTO dto = new UserDTO(id, "Bruno", "Abulquerque", "bruno@gmail.com", "123456");
        return dto;
    }
}
