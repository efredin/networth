package com.efredin.networth.currencies;

import static org.json.JSONObject.getNames;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
  
  @Value("${fixer.apiKey}")
  private String fixerApiKey;

  /** Get a map of currencies */
  @Cacheable("getCurrencies")
  public Map<String, String> getCurrencies() throws CurrencyException {

    String url = "http://data.fixer.io/api/symbols?access_key=" + this.fixerApiKey;

    try {
      // retrieve a list of symbols from fixer api
      HttpResponse<JsonNode> response = Unirest.get(url).asJson();
      
      JSONObject data = response.getBody()
        .getObject()
        .getJSONObject("symbols");
      
      // map symbols
      Map<String, String> symbols = new TreeMap<String, String>();
      for(String key:getNames(data)) {
        symbols.put(key, data.getString(key));
      }
      return symbols;
    } catch (Exception e) {
      throw new CurrencyException("Unable to load currencies from fixer", e);
    }
  }

  /**
   * Get the conversion rate from one currency to another
   * @param from Source currency symbol
   * @param to Destination currency symbol
   */
  public double conversionRate(String from, String to) throws CurrencyException {
        // get conversion rate from fixer
        // note: fixer free api does not allow for direct conversion and forces
        // EUR as the base currency
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("access_key", this.fixerApiKey);
        params.put("symbols", to + "," + from);

        String url = "http://data.fixer.io/api/latest";

        try {
          // retrieve a list of symbols from fixer api
          HttpResponse<JsonNode> response = Unirest.get(url).queryString(params).asJson();
          JSONObject rates = response.getBody()
            .getObject()
            .getJSONObject("rates");
          
          double fromRate = rates.getDouble(from);
          double toRate = rates.getDouble(to);
          return 1d / fromRate * toRate;
          
        } catch (Exception e) {
          throw new CurrencyException("Unable to get converstion rate", e);
        }
    }
}