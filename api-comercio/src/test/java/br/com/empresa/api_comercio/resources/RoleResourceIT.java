package br.com.empresa.api_comercio.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Role;
import br.com.empresa.api_comercio.repositories.RoleRepository;
import br.com.empresa.api_comercio.services.exception.*;
import br.com.empresa.api_comercio.services.impl.RoleServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import com.fasterxml.jackson.databind.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleResource.class)
public class RoleResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoleServiceImpl service;

    @Autowired
    private RoleRepository repository;

    private UUID existingId;
    private UUID nonExistingId;
    private UUID dependentId;
    private String authority;

    private RoleDTO roleDTO;
    private PageImpl<RoleDTO> page;

    public RoleResourceIT() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception{

        Optional<Role> obj = repository.findAll().stream().findFirst();;
        UUID existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        nonExistingId = UUID.randomUUID();
        dependentId = existingId;
        authority = "Manager";

        RoleDTO roleDTO = Factory.createdRoleDTO();

        page = new PageImpl<>(List.of(roleDTO));

        when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);

        when(service.findById(existingId)).thenReturn(roleDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(service.queryMethod(authority)).thenReturn(page.stream().toList());

        when(service.insert(ArgumentMatchers.any())).thenReturn(roleDTO);

        when(service.update(ArgumentMatchers.eq(existingId), ArgumentMatchers.any())).thenReturn(roleDTO);
        when(service.update(ArgumentMatchers.eq(nonExistingId), ArgumentMatchers.any())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(service).deleteById(existingId);
        doThrow(ResourceNotFoundException.class).when(service).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(service).deleteById(dependentId);
    }

    @Test
    public void findAllPagedShouldReturnAllCategoriesSortByAuthority() throws Exception {

        ResultActions result = mockMvc.perform(get("/roles?sort=authority"));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.content").exists());
                result.andExpect(jsonPath("$.content[0].id").exists());
                result.andExpect(jsonPath("$.content[0].authority").exists());
    }

    @Test
    public void queryMethodShouldReturnListFilteredByAuthority() throws Exception {

        ResultActions result = mockMvc.perform(get("/roles/authority/{authority}", authority));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$[0].id").exists());
                result.andExpect(jsonPath("$[0].authority").exists());
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/roles/{id}", existingId));
                result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(get("/roles/{id}", nonExistingId));
                result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure() throws Exception {

            RoleDTO dto = Factory.createdRoleDTO();

            String jsonBody = objectMapper.writeValueAsString(dto);

            ResultActions result = mockMvc.perform(post("/roles")
                    .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON));
                                result.andExpect(status().isOk());

                    result.andExpect(jsonPath("$.id").exists());
                    result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting() throws Exception {

        RoleDTO dto = Factory.createdRoleDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/roles/{id}", existingId)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isOk());

                result.andExpect(jsonPath("$.id").exists());
                result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception{

        RoleDTO dto = Factory.createdRoleDTO();

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/roles/{id}", nonExistingId)
                .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
                            result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting() throws Exception {

        ResultActions result = mockMvc.perform(delete("/roles/{id}", existingId));
                result.andExpect(status().isOk());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting() throws Exception {

        ResultActions result = mockMvc.perform(delete("/roles/{id}", nonExistingId));
                result.andExpect(status().isNotFound());
    }
}
