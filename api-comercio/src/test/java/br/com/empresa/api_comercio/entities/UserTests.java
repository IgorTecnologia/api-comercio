package br.com.empresa.api_comercio.entities;

import org.junit.jupiter.api.*;

public class UserTests {

    @Test
    public void userShouldHaveCorrectStructure(){

        User entity = new User();
        Role role = new Role();

        role.setId(1L);
        role.setAuthority("Manager");

        entity.setId(1L);
        entity.setFirstName("Ana");
        entity.setLastName("Oliveira");
        entity.setEmail("ana@gmail.com");
        entity.setPassword("123456");

        entity.getRoles().add(role);

        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(entity.getRoles());
    }
}
