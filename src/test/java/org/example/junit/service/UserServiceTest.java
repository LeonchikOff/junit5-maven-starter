package org.example.junit.service;

import org.example.junit.model.User;
import org.example.junit.paramresolver.UserServiceParamResolver;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Tag("userService")
@TestMethodOrder(MethodOrderer.Random.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(UserServiceParamResolver.class)
class UserServiceTest {

    private static final User L = User.of(1, "L", "123");
    private static final User E = User.of(2, "E", "234");
    private static final User O = User.of(3, "O", "345");
    private static final User N = User.of(4, "N", "456");
    private static final User LEON = User.of(5, "LEON", "1993");

    private UserService userService;

    @BeforeAll
    void initialization() {
        System.out.println("BeforeAll: " + this);
    }

    @BeforeEach
    void prepare(UserService userService) {
        System.out.println("BeforeEach: " + this);
        this.userService = userService;

    }

    @Test
    void getAllUsersEmptyIfNoUsersAdded() {
        System.out.println("(Test 1) getAllUsersEmptyIfNoUsersAdded: " + this);
        userService = new UserService();
        List<User> users = userService.getAll();
        assertTrue(users.isEmpty(), "List<User> should be empty");
    }

    @Test
    void usersConvertedToMapById() {
        userService.add(L, E, O, N, LEON);
        Map<Integer, User> allConvertedUsersById = userService.getAllConvertedById();

        MatcherAssert.assertThat(allConvertedUsersById, IsMapContaining.hasKey(LEON.getId()));

        assertAll(
                () -> assertThat(allConvertedUsersById)
                        .containsKeys(L.getId(), E.getId(), O.getId(), N.getId(), LEON.getId()),
                () -> assertThat(allConvertedUsersById)
                        .containsValues(L, E, O, N, LEON)
        );

    }

    @Test
    void alterationSizeIfAddUsers() {
        System.out.println("(Test 2) alterationSizeIfAddUsers: " + this);
        userService.add(L);
        userService.add(E);
        userService.add(O);
        userService.add(N);
        userService.add(LEON);
        List<User> users = userService.getAll();
        assertThat(users).hasSize(5);

//        assertEquals(5, users.size());
    }


    @AfterEach
    void abolish() {
        System.out.println("AfterEach: " + this);
    }

    @AfterAll
    void destroy() {
        System.out.println("AfterAll: " + this);
    }

    private static Stream<Arguments> providerStreamArgumentForLoginTest() {
        return Stream.of(
                Arguments.of("L", "", Optional.empty()),
                Arguments.of("E", "234", Optional.of(E)),
                Arguments.of("", "345", Optional.empty()),
                Arguments.of("N", "456", Optional.of(N))
        );
    }

    @Nested
    @DisplayName("Test user login functionality")
    @Tag("login")
    class LoginTest {

        @Test
        void throwExceptionIfUsernameOrPasswordIsNull() {
            assertAll(
                    () -> assertThat(
                            assertThrows(
                                    IllegalArgumentException.class, () -> userService.login(null, "password")
                            )).hasMessage("username or password is null"),
                    () -> assertThrows(
                            IllegalArgumentException.class, () -> userService.login("username", null)));
        }

        @Test
        @DisplayName("Success login if user exists")
        void loginSuccessIfUserIsExists() {
            userService.add(LEON);
            Optional<User> userOptional = userService.login(LEON.getUsername(), LEON.getPassword());
            assertThat(userOptional).isPresent();
            userOptional.ifPresent(user -> assertThat(user).isEqualTo(LEON));

//        assertTrue(userOptional.isPresent());
//        userOptional.ifPresent(user -> assertEquals(LEON, user));
        }

        @Test
        void loginFailedIfUserPasswordIsNotCorrect() {
            userService.add(LEON);
            Optional<User> optionalUser = userService.login(LEON.getUsername(), "1991");
            assertTrue(optionalUser.isEmpty());
        }

        @Test
        void loginFailedIfUserDoesNotExist() {
            userService.add(LEON);
            Optional<User> optionalUser = userService.login("leon", LEON.getPassword());
            assertTrue(optionalUser.isEmpty());
        }


        @ParameterizedTest
        @MethodSource("org.example.junit.service.UserServiceTest#providerStreamArgumentForLoginTest")
//        @CsvFileSource(resources = "/login-test-data.csv", delimiter = ',', numLinesToSkip = 1)
        @DisplayName("login param test")
        void loginParametrizedTest(String username, String password, Optional<User> expectedUserOptional) {
            userService.add(L, E, O, N);
            Optional<User> optionalUser = userService.login(username, password);
            assertThat(optionalUser).isEqualTo(expectedUserOptional);
        }
    }
}
