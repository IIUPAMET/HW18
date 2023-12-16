package com.example.demo.controller;

import com.example.demo.service.StealService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/picture")
public class StealController {

  private final StealService stealService;

  public StealController(StealService stealService) {
    this.stealService = stealService;
  }

  @PostMapping("/steal")
  @ResponseStatus(HttpStatus.CREATED)
  public void steal(@RequestBody StealRequest request) {
    stealService.stealPicturesFromNasa(request.sol);
  }

  @Data
  private static class StealRequest {
    private Integer sol;
  }
}
