package ru.mirea.intro.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mirea.intro.exception.NoSuchRequest;
import ru.mirea.intro.service.TestService;
import ru.mirea.intro.service.model.Book;
import ru.mirea.intro.service.model.Request;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestServiceImplTest {
    @Autowired
    TestService testService;

    @DisplayName("Testing for NoSuchIdException in get method")
    @Test
    void testServiceGetMethodException() {
        Assertions.assertThrows(NoSuchRequest.class, () -> testService.testServiceGetMethod(12345L));
    }

    @DisplayName("Testing for NoSuchRequestException in put method")
    @Test
    void testServicePutMethodException() {
        Assertions.assertThrows(NoSuchRequest.class, () -> testService.testServicePutMethod(new Request(1539393L, "запрос", new ArrayList<>())));
    }

    @DisplayName("Testing for NoSuchIdException in delete method")
    @Test
    void testServiceDeleteMethodException() {
        Assertions.assertThrows(NoSuchRequest.class, () -> testService.testServiceDeleteMethod(999L));
    }

    @DisplayName("Testing for normal response")
    @Test
    @Transactional
    void testServiceGetMethod() throws NoSuchRequest {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L, "Толстой", "Война и Мир"));
        Request request = new Request(1L, "Первый запрос", bookList);
        Assertions.assertEquals(testService.testServiceGetMethod(1L), request);
    }

    @DisplayName("Testing for normal post")
    @Test
    @Transactional
    void testServicePostMethod() {
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(3L, "Пост", "метод"));
        Request request = new Request(3L, "Пост запрос", bookList);
        Assertions.assertEquals(request, testService.testServicePostMethod(request));
    }

    @DisplayName("Testing for normal put")
    @Test
    @Transactional
    void testServicePutMethod() throws NoSuchRequest {
        Request putRequest = testService.testServiceGetMethod(1L);
        putRequest.setRequestValue("I know");
        Assertions.assertEquals(putRequest, testService.testServicePutMethod(putRequest));
    }

    @DisplayName("Testing for normal delete")
    @Test
    @Transactional
    void testServiceDeleteMethod() throws NoSuchRequest {
        Assertions.assertEquals("Row was successfully deleted!", testService.testServiceDeleteMethod(1L));
    }
}