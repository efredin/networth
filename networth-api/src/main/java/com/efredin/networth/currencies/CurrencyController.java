package com.efredin.networth.currencies;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/currencies")
@CrossOrigin(origins = "*")
public class CurrencyController {

  @Autowired
  private CurrencyService currencyService;

  @GetMapping()
  @Cacheable("getCurrencies")
  public Map<String, String> getCurrencies() {
    try {
      return this.currencyService.getCurrencies();
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}