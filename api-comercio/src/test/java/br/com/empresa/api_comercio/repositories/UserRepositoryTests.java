package br.com.empresa.api_comercio.repositories;

import br.com.empresa.api_comercio.entities.*;
import br.com.empresa.api_comercio.tests.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.data.domain.*;

import java.util.*;

@DataJpaTest
public class UserRepositoryTests {

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalElements;

    @Autowired
    private UserRepository repository;

    @BeforeEach
    void setUp() throws Exception{

        existingId = 1L;
        nonExistingId = 4L;
        countTotalElements = 3L;
    }

    @Test
    public void findAllPagedShouldReturnAllUsers(){

        PageRequest pageable = PageRequest.of(0, 12);

        Page<User> page = repository.findAll(pageable);

        Assertions.assertNotNull(page);
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExisting(){

        Optional<User> obj = repository.findById(existingId);

        Assertions.assertNotNull(obj);
    }

    @Test
    public void saveShouldSaveObjectWhenCorrectStructure(){

        User entity = Factory.createdUser();

        repository.save(entity);

        Assertions.assertEquals(countTotalElements +1, repository.count());
    }

    @Test
    public void deleteByIdShouldDeleteObjectWhenIdExisting(){

        repository.deleteById(existingId);

        Assertions.assertEquals(countTotalElements -1, repository.count());
    }
}
