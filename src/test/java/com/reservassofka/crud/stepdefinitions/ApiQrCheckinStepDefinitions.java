package com.reservassofka.crud.stepdefinitions;

import com.reservassofka.crud.questions.ServerResponse;
import com.reservassofka.crud.tasks.GetAuthToken;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class ApiQrCheckinStepDefinitions {

    private String apiUrl;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        apiUrl = variables.getProperty("restapi.baseurl", "http://localhost:3000");
    }

    @Given("un token de autorización válido para {string}")
    public void unTokenDeAutorizacionValidoPara(String email) {
        theActorCalled(email).whoCan(CallAnApi.at(apiUrl));
        theActorInTheSpotlight().attemptsTo(
            GetAuthToken.withCredentials(email, "password1234")
        );
    }

    @Given("una reserva creada para {string} en estado PENDING con ID {string}")
    public void unaReservaCreadaPara(String spaceName, String reservationId) {
        theActorInTheSpotlight().remember("reservationId", reservationId);
    }

    @Given("la hora de la reserva expiró hace {string} minutos")
    public void horaReservaExpiroHace(String minutos) {
        theActorInTheSpotlight().remember("gracePeriodExpired", true);
    }

    @When("el cliente envía una petición POST a {string} con el {string} válido")
    public void enviarPostRequest(String endpoint, String tokenType) {
        theActorInTheSpotlight().attemptsTo(
            Post.to(endpoint)
                .with(request -> request
                    .header("Authorization", "Bearer " + theActorInTheSpotlight().recall("token"))
                    .body("{\"qrToken\":\"VALID_MOCK_TOKEN_123\"}")
                )
        );
    }

    @When("el cliente envía una petición POST a {string} con un token adulterado")
    public void enviarPostRequestAdulterado(String endpoint) {
        theActorInTheSpotlight().attemptsTo(
            Post.to(endpoint)
                .with(request -> request
                    .header("Authorization", "Bearer " + theActorInTheSpotlight().recall("token"))
                    .body("{\"qrToken\":\"INVALID.MOCK.TOKEN\"}")
                )
        );
    }

    @Then("el status code de la respuesta debería ser {int}")
    public void statusCodeDeLaRespuesta(int expectedCode) {
        theActorInTheSpotlight().should(seeThat("El código de estado de la respuesta", ServerResponse.statusCode(), equalTo(expectedCode)));
    }

    @Then("el body de la respuesta debe contener")
    public void elBodyDeLaRespuestaDebeContener(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        theActorInTheSpotlight().should(
                seeThatResponse("El cuerpo de la respuesta contiene los valores esperados",
                        response -> {
                            response.body("message", equalTo(data.get("message")));
                            response.body("status", equalTo(data.get("status")));
                        }
                )
        );
    }

    @Then("el body de la respuesta de error debe contener")
    public void elBodyDeLaRespuestaDeErrorDebeContener(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        theActorInTheSpotlight().should(
                seeThatResponse("El cuerpo de la respuesta de error contiene los valores esperados",
                        response -> {
                            response.body("error", equalTo(data.get("error")));
                            response.body("message", equalTo(data.get("message")));
                        }
                )
        );
    }
}
