package br.com.empresa.api_comercio.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.services.*;
import br.com.empresa.api_comercio.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceIT {

    @Autowired
    private ProductService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalElements;

    @BeforeEach
    void setUp() throws Exception{

    existingId = 1L;
    nonExistingId = 6L;
    countTotalElements = 4L;
    }

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

        Long id = 2L;

        ResultActions result = mockMvc.perform(get("/products/{id}", id));

            result.andExpect(status().isOk());

            result.andExpect(jsonPath("$.id").exists());
            result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/products/{id}",nonExistingId));

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

        ProductDTO dto = Factory.createdProductDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        Long id = 2L;

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

        ProductDTO dto = Factory.createdProductDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting() throws Exception {

        ResultActions result = mockMvc.perform(delete("/products/{id}", existingId));
                result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(delete("/products/{id}", nonExistingId));
                result.andExpect(status().isNotFound());
    }
}
