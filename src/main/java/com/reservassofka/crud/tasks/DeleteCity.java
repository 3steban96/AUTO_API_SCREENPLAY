package com.reservassofka.crud.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Delete;

public class DeleteCity implements Task {

    private final String cityId;

    public DeleteCity(String cityId) {
        this.cityId = cityId;
    }

    public static DeleteCity byId(String cityId) {
        return Tasks.instrumented(DeleteCity.class, cityId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from("/locations/cities/{id}")
                        .with(request -> request
                                .header("Authorization", "Bearer " + actor.recall("token"))
                                .pathParam("id", cityId)
                        )
        );
    }
}
