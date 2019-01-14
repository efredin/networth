package com.efredin.networth.currencies;

import static org.json.JSONObject.getNames;

import java.util.HashMap;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${fixer.apiKey}")
  private String fixerApiKey;

  @GetMapping()
  @Cacheable("getCurrencies")
  public Map<String, String> getCurrencies() {

    String url = "http://data.fixer.io/api/symbols?access_key=" + this.fixerApiKey;

    try {
      // retrieve a list of symbols from fixer api
      HttpResponse<JsonNode> response = Unirest.get(url).asJson();
      
      JSONObject data = response.getBody()
        .getObject()
        .getJSONObject("symbols");
      
      // map symbols
      Map<String, String> symbols = new HashMap<String, String>();
      for(String key:getNames(data)) {
        symbols.put(key, data.getString(key));
      }
      return symbols;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}