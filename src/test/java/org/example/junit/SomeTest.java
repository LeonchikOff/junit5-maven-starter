package org.example.junit;

import org.example.junit.extension.*;
import org.example.junit.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({GlobalExtension.class,
        PostProcessingExtension.class,
        UserServiceParamResolver.class,
        ExecutionConditionExtension.class,
        ThrowableExtension.class
})
public class SomeTest {


    private final UserService userService;
//
//    @SomeAnnotation(UserService.class)
//    public UserService userService;


    public SomeTest(UserService userService) {
        this.userService = userService;
    }

    @BeforeAll
    void beforeAllTest() {
        System.out.println("beforeAllTest");
    }

    @BeforeEach
    void beforeEachTest() {
        System.out.println("beforeEachTest");
    }

    @Test
    void someTest() {
        System.out.println("someTest");
        System.out.println(userService);
    }

    @Test
    void anotherSomeTest() {
        System.out.println("anotherSomeTest");
        System.out.println(userService);
        if(true) {
            throw new RuntimeException();
        }
    }

    @AfterEach
    void afterEachTest() {
        System.out.println("afterEachTest");
    }


    @AfterAll
    void afterAllTest() {
        System.out.println("afterAllTest");
    }
}
