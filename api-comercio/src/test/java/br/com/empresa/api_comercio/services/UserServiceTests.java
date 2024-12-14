package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.entities.User;
import br.com.empresa.api_comercio.repositories.*;
import br.com.empresa.api_comercio.services.exception.ResourceNotFoundException;
import br.com.empresa.api_comercio.services.impl.UserServiceImpl;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private UserRepository repository;

    private UUID existingId;
    private UUID nonExistingId;
    private Long countTotalElements;

    @BeforeEach
    void setUp() throws Exception{

        Optional<User> obj = repository.findAll().stream().findFirst();;
        existingId = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found: " + obj.get().getId())).getId();

        nonExistingId = UUID.randomUUID();
        countTotalElements = 4L;
    }

    @Test
    public void findAllPagedShouldReturnAllUsers(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<UserDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnListFilteredByFirstName() {

        String firstName = "Mari";

        List<UserDTO> list = service.queryMethod(firstName);

        Assertions.assertNotNull(list);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        UserDTO dto = service.findById(existingId);

        Assertions.assertNotNull(dto);
    }

    @Test
    public void insertShouldSaveObjectWhenCorrectStructure(){

        UserDTO dto = Factory.createdUserDTO();

        service.insert(dto);

        Assertions.assertEquals(countTotalElements +1, repository.count());
    }

    @Test
    public void updateShouldSaveObjectWhenIdExisting(){

        UserDTO dto = Factory.createdUserDTO();

        service.insert(dto);

        Assertions.assertEquals(countTotalElements +1, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting(){

        service.deleteById(existingId);

        Assertions.assertEquals(countTotalElements -1, repository.count());
    }
}
