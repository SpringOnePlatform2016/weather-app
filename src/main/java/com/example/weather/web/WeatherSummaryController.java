package com.example.weather.web;

import com.example.weather.WeatherAppProperties;
import com.example.weather.integration.ows.Weather;
import com.example.weather.integration.ows.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class WeatherSummaryController {

    private final WeatherService weatherService;

    private final WeatherAppProperties properties;

    public WeatherSummaryController(WeatherService weatherService, WeatherAppProperties properties) {
        this.weatherService = weatherService;
        this.properties = properties;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Mono<String> conferenceWeather(Model model) {
        model.addAttribute("summary", getSummary());
        return Mono.just("index.html");
    }

    private Object getSummary() {
        List<WeatherSummary> summary = new ArrayList<>();
        for (String location : this.properties.getLocations()) {
            String country = location.split("/")[0];
            String city = location.split("/")[1];
            Weather weather = this.weatherService.getWeather(country, city).block();
            summary.add(createWeatherSummary(country, city, weather));
        }
        return summary;
    }


    private WeatherSummary createWeatherSummary(String country, String city,
                                                Weather weather) {
        // cough cough
        if ("Las Vegas".equals(city)) {
            weather.setWeatherId(666);
        }
        return new WeatherSummary(country, city, weather);
    }

}
