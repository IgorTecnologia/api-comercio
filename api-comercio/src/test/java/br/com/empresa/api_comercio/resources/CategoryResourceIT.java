package br.com.empresa.api_comercio.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.*;
import br.com.empresa.api_comercio.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryResourceIT {

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryRepository repository;

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
        nonExistingId = 4L;
        countTotalElements = 3L;
    }

    @Test
    public void findAllPagedShouldReturnAllCategoriesSortByName() throws Exception{

        PageRequest pageable = PageRequest.of(0,12);

        ResultActions result = mockMvc.perform(get("/categories?sort=name"));

        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].name").exists());
    }

    @Test
    public void queryMethodShouldReturnListFilteredByName() throws Exception {

        String name = "Fri";

        ResultActions result = mockMvc.perform(get("/categories/name/{name}", name));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$[0].id").exists());
        result.andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting() throws Exception{

        ResultActions result = mockMvc.perform(get("/categories/{id}", existingId));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/categories/{id}", nonExistingId));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        CategoryDTO dto = Factory.createdCategoryDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/categories")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() throws Exception {

        CategoryDTO dto = Factory.createdCategoryDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/categories/{id}", existingId)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting() throws Exception {

        ResultActions result = mockMvc.perform(delete("/categories/{id}", existingId));
                result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        Long id = 5L;

        ResultActions result = mockMvc.perform(delete("/categories/{id}", id));
                result.andExpect(status().isNotFound());
    }
}
