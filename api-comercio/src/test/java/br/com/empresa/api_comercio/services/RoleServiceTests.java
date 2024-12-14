package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.Product;
import br.com.empresa.api_comercio.entities.Role;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.exception.*;
import br.com.empresa.api_comercio.services.impl.RoleServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@SpringBootTest
@Transactional
public class RoleServiceTests {

    @Autowired
    private RoleServiceImpl service;

    @Autowired
    private RoleRepository repository;

    private UUID existingId;
    private UUID nonExistingId;
    String authority;
    private Long countTotalElements;

    @BeforeEach
    void setUp() throws Exception{

        Optional<Role> obj = repository.findAll().stream().findFirst();;
        existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        nonExistingId = UUID.randomUUID();
        authority = "Manager";
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
