package ru.mirea.intro.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mirea.intro.dao.repository.RequestRepository;
import ru.mirea.intro.exception.NoSuchRequest;
import ru.mirea.intro.service.TestService;
import ru.mirea.intro.service.model.Book;
import ru.mirea.intro.service.model.Request;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestServiceImplTest {
    @Autowired
    TestService testService;
    @Autowired
    RequestRepository requestRepository;

    @DisplayName("Testing for NoSuchIdException")
    @Test
    void testServiceGetMethodException() {
        Assertions.assertThrows(NoSuchRequest.class, () -> testService.testServiceGetMethod(12345L));
    }

    @DisplayName("Testing for normal response")
    @Test
    @Transactional
    void testServiceGetMethod() throws NoSuchRequest {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(16L, "Толстой", "Война и Мир"));
        Request request = new Request(199L, "Второй запрос", bookList);
        System.out.println(request);
        System.out.println(testService.testServiceGetMethod(199L));
        Assertions.assertTrue(request.equals(testService.testServiceGetMethod(199L)));
    }

    @DisplayName("Testing for normal post")
    @Test
    @Transactional
    void testServicePostMethod() throws NoSuchRequest {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(456L, "Толстой Тест", "Война и Мир Тест"));
        Request request = new Request(new Random().nextLong(), "Первый запрос из теста", bookList);
        Assertions.assertEquals("Successfully inserted row!", testService.testServicePostMethod(request));
    }

    @DisplayName("Testing for normal put")
    @Test
    @Transactional
    void testServicePutMethod() throws NoSuchRequest {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(456L, "Лев Толстой Тест", "Война и Мир Тест"));
        Request request = new Request(new Random().nextLong(), "Первый запрос из теста", bookList);
        Assertions.assertEquals("Row was successfully updated!", testService.testServicePutMethod(request));
    }

    @DisplayName("Testing for normal delete")
    @Test
    @Transactional
    void testServiceDeleteMethod() throws NoSuchRequest {
        Assertions.assertEquals("Row was successfully deleted!", testService.testServiceDeleteMethod(199L));
        Assertions.assertFalse(requestRepository.existsById(199L));
    }
}