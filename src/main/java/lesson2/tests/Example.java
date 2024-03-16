package lesson2.tests;

import lesson2.annotations.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Example {
    @BeforeAll
    public void beforeAll() {
        System.out.println("Перед всеми методами");
    }

    @AfterAll
    public void afterAll() {
        System.out.println("После всех методов");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Перед методом");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("После метода");
    }

    @Test(order = 1)
    public void test1() {
        System.out.println("test1");
    }


    @Test(order = 2)
    public void test3() {
        System.out.println("test3");
    }

    @Test(order = 3)
    public void test2() {
        System.out.println("test2");
    }

}
