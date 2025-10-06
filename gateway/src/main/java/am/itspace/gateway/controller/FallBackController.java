package am.itspace.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class FallBackController {

  @GetMapping("/fallback/products")
  public ResponseEntity<List<String>> productsFallback() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Collections.singletonList("Product Service is unavailable please try after some time"));
  }
}
