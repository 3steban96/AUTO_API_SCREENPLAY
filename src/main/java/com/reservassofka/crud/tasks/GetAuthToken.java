package com.reservassofka.crud.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public class GetAuthToken implements Task {

    private final String email;
    private final String password;

    public GetAuthToken(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static GetAuthToken withCredentials(String email, String password) {
        return Tasks.instrumented(GetAuthToken.class, email, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        String authUrl = variables.getProperty("restapi.authurl", "http://localhost:3001");

        io.restassured.response.Response response = SerenityRest.given()
                .baseUri(authUrl)
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}")
                .post("/auth/login");
        
        String token = response.jsonPath().getString("data.token");
        actor.remember("token", token);
    }
}
