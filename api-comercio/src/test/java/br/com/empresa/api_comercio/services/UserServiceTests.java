package br.com.empresa.api_comercio.services;

import br.com.empresa.api_comercio.dto.*;
import br.com.empresa.api_comercio.repositories.*;
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
    private UserService service;

    @Autowired
    private UserRepository repository;

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
    public void findAllPagedShouldReturnAllUsers(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<UserDTO> page = service.findAllPaged(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void queryMethodShouldReturnListFilteredByFirstName() {

        String firstName = "Vane";

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
