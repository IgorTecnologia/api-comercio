package br.com.empresa.api_comercio.entities;

import org.junit.jupiter.api.*;

public class RoleTests {

    @Test
    public void roleShouldHaveCorrectStructure(){

        Role entity = new Role();

        entity.setId(1L);
        entity.setAuthority("Boss");

        Assertions.assertNotNull(entity);
    }
}
