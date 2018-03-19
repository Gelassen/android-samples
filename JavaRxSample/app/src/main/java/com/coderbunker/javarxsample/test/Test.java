package com.coderbunker.javarxsample.test;


import com.coderbunker.javarxsample.dto.AirCompaniesItem;
import com.coderbunker.javarxsample.dto.CityItem;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static List<CityItem> generateTestData() {
        List<CityItem> result = new ArrayList<>();
        result.add(generateCityItem("Moscow"));
        result.add(generateCityItem("Shanghai"));
        result.add(generateCityItem("Amsterdam"));
        result.add(generateCityItem("Berlin"));
        result.add(generateCityItem("London"));
        return result;
    }

    private static CityItem generateCityItem(String city) {
        CityItem cityItem = new CityItem();
        cityItem.setCity(city);
        return cityItem;
    }

    public static List<AirCompaniesItem> generateAirCompaniesData() {
        List<AirCompaniesItem> result = new ArrayList<>();
        result.add(generateAirCompanyItem("AirJet"));
        result.add(generateAirCompanyItem("Aeroflot"));
        result.add(generateAirCompanyItem("Japan airlines"));
        return result;
    }

    private static AirCompaniesItem generateAirCompanyItem(String airCompany) {
        AirCompaniesItem airCompaniesItem = new AirCompaniesItem();
        airCompaniesItem.setAirCompany(airCompany);
        return airCompaniesItem;
    }
}
