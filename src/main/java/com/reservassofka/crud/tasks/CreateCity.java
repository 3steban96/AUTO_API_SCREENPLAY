package com.reservassofka.crud.tasks;

import com.reservassofka.crud.models.CityInfo;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class CreateCity implements Task {

    private final CityInfo cityInfo;

    public CreateCity(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public static CreateCity withDetails(String name, String country) {
        return Tasks.instrumented(CreateCity.class, new CityInfo(name, country));
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/locations/cities")
                        .with(request -> request
                                .header("Authorization", "Bearer " + actor.recall("token"))
                                .contentType(ContentType.JSON)
                                .body(cityInfo)
                        )
        );
    }
}
