package br.com.empresa.api_comercio.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Category;
import br.com.empresa.api_comercio.entities.Product;
import br.com.empresa.api_comercio.repositories.ProductRepository;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.services.impl.ProductServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceIT {

    @Autowired
    private ProductServiceImpl service;

    @Autowired
    private ProductRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findAllPagedShouldReturnAllProductsSortByName() throws Exception {

        ResultActions result = mockMvc.perform(get("/products?sort=name"));
            result.andExpect(status().isOk());

            result.andExpect(jsonPath("$.content").exists());
            result.andExpect(jsonPath("$.content[0].id").exists());
            result.andExpect(jsonPath("$.content[0].name").exists());
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting() throws Exception {

        Optional<Product> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(get("/products/{id}", id));

            result.andExpect(status().isOk());

            result.andExpect(jsonPath("$.id").exists());
            result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(get("/products/{id}",id));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception{

        ProductDTO dto = Factory.createdProductDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/products")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() throws Exception{

        Optional<Product> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ProductDTO dto = Factory.createdProductDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/products/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ProductDTO dto = Factory.createdProductDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/products/{id}", id)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting() throws Exception {

        Optional<Product> obj = repository.findAll().stream().findFirst();

        UUID id = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        ResultActions result = mockMvc.perform(delete("/products/{id}", id));
                result.andExpect(status().isOk());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        UUID id = UUID.randomUUID();

        ResultActions result = mockMvc.perform(delete("/products/{id}", id));
                result.andExpect(status().isNotFound());
    }
}
