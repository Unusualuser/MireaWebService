package ru.mirea.intro.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.intro.service.TestService;
import ru.mirea.intro.web.to.Meta;
import ru.mirea.intro.web.to.RequestDto;
import ru.mirea.intro.web.to.Response;

@RestController
@RequestMapping("api/mirea")
public class MireaController {

    private final TestService testService;

    public MireaController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/post-method")
    public ResponseEntity<Response<String>> postMethod(@RequestBody RequestDto requestDto) {
        try {
            return new ResponseEntity<>(new Response<>(new Meta(0, "All good!"), testService.testServicePostMethod(requestDto)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(new Meta(1, e.toString()), null), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/get-method")
    public ResponseEntity<Response<RequestDto>> getMethod(@RequestParam Long id) {
        try {
            return new ResponseEntity<>(new Response<>(new Meta(0, "All good!"), testService.testServiceGetMethod(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(new Meta(1, e.toString()), null), HttpStatus.CONFLICT);
        }
    }
}
