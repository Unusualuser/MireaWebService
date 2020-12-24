package ru.mirea.intro.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.intro.mapper.RequestMapper;
import ru.mirea.intro.service.TestService;
import ru.mirea.intro.service.model.Request;
import ru.mirea.intro.web.to.Meta;
import ru.mirea.intro.web.to.RequestDto;
import ru.mirea.intro.web.to.Response;

@RestController
@RequestMapping("api/mirea")
@Api(tags = "Методы для тестирования нашего приложения")
public class MireaController {
    private final TestService testService;

    public MireaController(TestService testService) {
        this.testService = testService;
    }

    @ApiOperation(value = "Пост метод тестового веб-сервиса",
            notes = "Отправление пост-метода для добавления новой книги, на вход принимается объект книги")
    @PostMapping("/post-method")
    public ResponseEntity<Response<String>> postMethod(@RequestBody RequestDto requestDto) {
        try {
            Request request = RequestMapper.REQUEST_MAPPER.requestDTOToRequest(requestDto);
            String testServiceResponse = testService.testServicePostMethod(request);
            return new ResponseEntity<>(new Response<>(new Meta(0, "All good!"), testServiceResponse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(new Meta(1, e.toString()), null), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Гет метод тестового веб-сервиса",
            notes = "Отправление гет-метода для получения книги по id")
    @GetMapping("/get-method")
    public ResponseEntity<Response<RequestDto>> getMethod(@RequestParam Long id) {
        try {
            Request request = testService.testServiceGetMethod(id);
            RequestDto requestDto = RequestMapper.REQUEST_MAPPER.requestToRequestDto(request);
            return new ResponseEntity<>(new Response<>(new Meta(0, "All good!"), requestDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(new Meta(1, e.toString()), null), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Пут метод тестового веб-сервиса",
            notes = "Отправление пут-метода для изменения параметров книги, на вход принимается объект книги")
    @PutMapping("/put-method")
    public ResponseEntity<Response<String>> putMethod(@RequestBody RequestDto requestDto) {
        try {
            Request request = RequestMapper.REQUEST_MAPPER.requestDTOToRequest(requestDto);
            String testServiceResponse = testService.testServicePutMethod(request);
            return new ResponseEntity<>(new Response<>(new Meta(0, "All good!"), testServiceResponse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(new Meta(1, e.toString()), null), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Делит метод тестового веб-сервиса",
            notes = "Отправление делит-метода для удаления книги по id")
    @DeleteMapping("/delete-method")
    public ResponseEntity<Response<String>> deleteMethod(@RequestParam Long id) {
        try {
            String testServiceResponse = testService.testServiceDeleteMethod(id);
            return new ResponseEntity<>(new Response<>(new Meta(0, "All good!"), testServiceResponse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(new Meta(1, e.toString()), null), HttpStatus.CONFLICT);
        }
    }
}
