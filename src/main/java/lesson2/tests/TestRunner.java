package lesson2.tests;

import lesson2.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {

    public static void run(Class<?> testClass) {
        final Object testObj = initTestObj(testClass);
        getDequeMethods(testClass).forEach(method -> {
            try {
                method.invoke(testObj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static List<Method> getDequeMethods(Class<?> testClass) {
        List<Method> outList = new ArrayList<>();
        List<Method> afterAll = new ArrayList<>();
        List<Method> beforeAll = new ArrayList<>();

//        PriorityQueue<Method> tests = new PriorityQueue<>(
//                Comparator.comparingInt(
//                        method -> method.getAnnotation(Test.class).order())); // почему-то не работает
        List<Method> tests = new ArrayList<>();

        Method afterEach = null;
        Method beforeEach = null;
        for (Method currentMethod : testClass.getDeclaredMethods()) {
            if (currentMethod.getAnnotation(AfterAll.class) != null) {
                afterAll.add(currentMethod);
            }
            if (currentMethod.getAnnotation(AfterEach.class) != null) {
                afterEach = currentMethod;
            }
            if (currentMethod.getAnnotation(BeforeEach.class) != null) {
                beforeEach = currentMethod;
            }
            if (currentMethod.getAnnotation(BeforeAll.class) != null) {
                beforeAll.add(currentMethod);
            }
            if (currentMethod.getAnnotation(Test.class) != null) {
                tests.add(currentMethod);
            }
        }
        tests.sort(Comparator.comparingInt(method -> method.getAnnotation(Test.class).order()));

        outList.addAll(beforeAll);
        for (Method test : tests) {
            if (beforeEach != null) {
                outList.add(beforeEach);
            }
            outList.add(test);
            if (afterEach != null) {
                outList.add(afterEach);
            }
        }
        outList.addAll(afterAll);
        return outList;
    }

    private static Object initTestObj(Class<?> testClass) {
        try {
            Constructor<?> noArgsConstructor = testClass.getConstructor();
            return noArgsConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Нет конструктора по умолчанию");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось создать объект тест класса");
        }
    }

}
