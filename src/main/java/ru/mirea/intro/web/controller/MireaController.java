package ru.mirea.intro.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "Проверка на работоспоснобность приложения",
            notes = "Отправление запроса для проверки работоспособности")
    @GetMapping("/isHealth-method")
    public ResponseEntity<Response<Boolean>> isHealthMethod() {
        return new ResponseEntity<>(new Response<>(new Meta(0, "All good!"), true), HttpStatus.OK);
    }

    @ApiOperation(value = "Пост метод тестового веб-сервиса",
            notes = "Отправление пост-метода для добавления новой книги, на вход принимается объект книги")
    @PostMapping("/post-method")
    public ResponseEntity<Response<RequestDto>> postMethod(@ApiParam(value = "Модель запроса requestDto", required = true) @RequestBody RequestDto requestDto) {
        try {
            Request request = RequestMapper.REQUEST_MAPPER.requestDTOToRequest(requestDto);
            RequestDto testServiceResponse = RequestMapper.REQUEST_MAPPER.requestToRequestDto(testService.testServicePostMethod(request));
            return new ResponseEntity<>(new Response<>(new Meta(0, "All good!"), testServiceResponse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(new Meta(1, e.toString()), null), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Гет метод тестового веб-сервиса",
            notes = "Отправление гет-метода для получения книги по id")
    @GetMapping("/get-method")
    public ResponseEntity<Response<RequestDto>> getMethod(@ApiParam(value = "Идентификатор запроса", required = true) @RequestParam Long id) {
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
    public ResponseEntity<Response<RequestDto>> putMethod(@ApiParam(value = "Модель запроса requestDto", required = true) @RequestBody RequestDto requestDto) {
        try {
            Request request = RequestMapper.REQUEST_MAPPER.requestDTOToRequest(requestDto);
            RequestDto testServiceResponse = RequestMapper.REQUEST_MAPPER.requestToRequestDto(testService.testServicePutMethod(request));
            return new ResponseEntity<>(new Response<>(new Meta(0, "All good!"), testServiceResponse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(new Meta(1, e.toString()), null), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Делит метод тестового веб-сервиса",
            notes = "Отправление делит-метода для удаления книги по id")
    @DeleteMapping("/delete-method")
    public ResponseEntity<Response<String>> deleteMethod(@ApiParam(value = "Идентификатор запроса", required = true) @RequestParam Long id) {
        try {
            String testServiceResponse = testService.testServiceDeleteMethod(id);
            return new ResponseEntity<>(new Response<>(new Meta(0, "All good!"), testServiceResponse), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(new Meta(1, e.toString()), null), HttpStatus.CONFLICT);
        }
    }
}
