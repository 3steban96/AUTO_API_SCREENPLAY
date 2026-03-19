package com.reservassofka.crud.questions;

import com.reservassofka.crud.models.CityInfo;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CityDetails implements Question<CityInfo> {

    public static CityDetails fromResponse() {
        return new CityDetails();
    }

    @Override
    public CityInfo answeredBy(Actor actor) {
        // Assuming the response structure is { "data": { "name": "...", "country": "..." } } or similar.
        // The controller returns ApiResponse.success(mapper.toResponse(city)), which looks like { "data": ... }
        // Let's extract from "data" object.
        String name = SerenityRest.lastResponse().jsonPath().getString("data.name");
        String country = SerenityRest.lastResponse().jsonPath().getString("data.country");
        return new CityInfo(name, country);
    }
}
