package com.reservassofka.crud.tasks;

import com.reservassofka.crud.models.CityInfo;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Put;

public class UpdateCity implements Task {

    private final String cityId;
    private final CityInfo cityInfo;

    public UpdateCity(String cityId, CityInfo cityInfo) {
        this.cityId = cityId;
        this.cityInfo = cityInfo;
    }

    public static UpdateCity withDetails(String cityId, String newName, String newCountry) {
        return Tasks.instrumented(UpdateCity.class, cityId, new CityInfo(newName, newCountry));
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to("/locations/cities/{id}")
                        .with(request -> request
                                .header("Authorization", "Bearer " + actor.recall("token"))
                                .pathParam("id", cityId)
                                .contentType(ContentType.JSON)
                                .body(cityInfo)
                        )
        );
    }
}
