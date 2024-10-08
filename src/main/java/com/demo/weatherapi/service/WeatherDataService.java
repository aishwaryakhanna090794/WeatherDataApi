package com.demo.weatherapi.service;

import com.demo.weatherapi.models.WeatherData;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherDataService {

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Autowired
    WeatherData weatherData;
    private static final String TABLE_NAME = "WeatherData";

    public void getWeatherForecastByCityIdAndAppID(String cityId, String appid) {

        String websiteResponse = "http://api.openweathermap.org/data/2.5/weather?q=" + cityId + "&appid=" + appid;

        System.out.println(websiteResponse);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(websiteResponse, String.class);
        JSONObject root = new JSONObject(result);
        JSONObject main = root.getJSONObject("main");
        weatherData.setTemp(String.valueOf(main.getFloat("temp")));
        System.out.println("temp" + weatherData.getTemp());
        weatherData.setCity(cityId);

        insertWeatherData(weatherData);
    }


    public void insertWeatherData(WeatherData weatherData) {

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Id", AttributeValue.builder().s(String.valueOf(Math.random())).build());
        item.put("city", AttributeValue.builder().s(weatherData.getCity()).build());
        item.put("temperature", AttributeValue.builder().s(weatherData.getTemp()).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build();

        PutItemResponse response = dynamoDbClient.putItem(request);
        // Handle the response if needed
        System.out.println("Item inserted with response: " + response);
    }


}
