package com.example.demo.controller;

import com.example.demo.service.StealService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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
  public void steal(@RequestParam Integer sol) {
    stealService.stealPicturesFromNasa(sol);
  }
}
