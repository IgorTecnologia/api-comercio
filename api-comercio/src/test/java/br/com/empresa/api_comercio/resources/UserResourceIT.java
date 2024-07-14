package br.com.empresa.api_comercio.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.*;
import br.com.empresa.api_comercio.services.*;
import br.com.empresa.api_comercio.services.exception.*;
import br.com.empresa.api_comercio.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserResource.class)
public class UserResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    private Long existingId;
    private Long nonExistingId;
    private String firstName;

    private PageImpl<UserDTO> page;

    public UserResourceIT() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception{

        existingId = 1L;
        nonExistingId = 4L;
        firstName = "Bruno";

        UserDTO userDTO = Factory.createdUserDTO();

        page = new PageImpl<>(List.of(userDTO));

        when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);

        when(service.queryMethod(firstName)).thenReturn(page.stream().toList());

        when(service.findById(existingId)).thenReturn(userDTO);
        doThrow(ResourceNotFoundException.class).when(service).findById(nonExistingId);

        when(service.insert(ArgumentMatchers.any())).thenReturn(userDTO);

        when(service.update(ArgumentMatchers.eq(existingId), ArgumentMatchers.any())).thenReturn(userDTO);
        when(service.update(ArgumentMatchers.eq(nonExistingId), ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(service).deleteById(existingId);
        doThrow(ResourceNotFoundException.class).when(service).deleteById(nonExistingId);
    }

    @Test
    public void findAllPagedShouldReturnAllUsersSortByName() throws Exception {

        ResultActions result = mockMvc.perform(get("/users?sort=firstName"));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.content").exists());
                result.andExpect(jsonPath("$.content[0].id").exists());
                result.andExpect(jsonPath("$.content[0].firstName").exists());
                result.andExpect(jsonPath("$.content[0].lastName").exists());
                result.andExpect(jsonPath("$.content[0].email").exists());
                result.andExpect(jsonPath("$.content[0].password").exists());
    }

    @Test
    public void queryMethodShouldReturnListFilteredByFirstName() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/firstName/{firstName}", firstName));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$[0].id").exists());
                result.andExpect(jsonPath("$[0].firstName").value(firstName));
                result.andExpect(jsonPath("$[0].lastName").exists());
                result.andExpect(jsonPath("$[0].email").exists());
                result.andExpect(jsonPath("$[0].password").exists());
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/{id}", existingId));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.firstName").exists());
                result.andExpect(jsonPath("$.lastName").exists());
                result.andExpect(jsonPath("$.email").exists());
                result.andExpect(jsonPath("$.password").exists());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/users/{id}", nonExistingId));
                result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

        UserDTO dto = Factory.createdUserDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/users")
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.firstName").exists());
                result.andExpect(jsonPath("$.lastName").exists());
                result.andExpect(jsonPath("$.email").exists());
                result.andExpect(jsonPath("$.password").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() throws Exception {

        UserDTO dto = Factory.createdUserDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/users/{id}", existingId)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.firstName").exists());
                result.andExpect(jsonPath("$.lastName").exists());
                result.andExpect(jsonPath("$.email").exists());
                result.andExpect(jsonPath("$.password").exists());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        UserDTO dto = Factory.createdUserDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/users/{id}", nonExistingId)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isNotFound());

    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting() throws Exception {

        ResultActions result = mockMvc.perform(delete("/users/{id}", existingId));
                result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(delete("/users/{id}", nonExistingId));
                result.andExpect(status().isNotFound());
    }
}
