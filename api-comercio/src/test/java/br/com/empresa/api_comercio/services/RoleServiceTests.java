package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.*;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.exception.*;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.dao.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@SpringBootTest
@Transactional
public class RoleServiceTests {

    @Autowired
    private RoleService service;

    @Autowired
    private RoleRepository repository;

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
    public void findAllPagedShouldReturnAllRoles(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<RoleDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        RoleDTO dto = service.findById(existingId);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void queryMethodShouldReturnListFilteredByAuthority(){

        String authority = "Employee";

        List<RoleDTO> list = service.queryMethod(authority);

        Assertions.assertNotNull(list);
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        RoleDTO dto = Factory.createdRoleDTO();

        service.insert(dto);

        Assertions.assertEquals(countTotalElements +1, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting(){

        RoleDTO dto = Factory.createdRoleDTO();

        service.update(existingId, dto);

        Assertions.assertEquals(countTotalElements, repository.count());
    }

    @Test
    public void deleteByIdShouldThrowResourceNotFoundExceptionWhenIdNonExisting(){

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteById(nonExistingId);
        });
    }
}
