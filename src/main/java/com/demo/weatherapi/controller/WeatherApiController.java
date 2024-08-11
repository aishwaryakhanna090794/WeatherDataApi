package com.demo.weatherapi.controller;

import com.demo.weatherapi.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;


@RestController
public class WeatherApiController {

    @Autowired
    private final WeatherDataService weatherDataService;


    public WeatherApiController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }


    @Operation(summary = "Get Weather Forecast By City Id and access token provided in query string")
    @RequestMapping("/get/{cityId}/{appId}")
    public void getWeatherForecastByCityIdAndAppID(@PathVariable("cityId") String cityId,
                                                   @PathVariable("appId") String appId) throws Exception {

        if (cityId != null && appId != null) {

            weatherDataService.getWeatherForecastByCityIdAndAppID(cityId, appId);


        }
    }
}
