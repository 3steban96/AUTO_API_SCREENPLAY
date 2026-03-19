package com.reservassofka.crud.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetCity implements Task {

    private final String cityId;

    public GetCity(String cityId) {
        this.cityId = cityId;
    }

    public static GetCity byId(String cityId) {
        return Tasks.instrumented(GetCity.class, cityId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/locations/cities/{id}")
                        .with(request -> request
                                .header("Authorization", "Bearer " + actor.recall("token"))
                                .pathParam("id", cityId)
                        )
        );
    }
}
