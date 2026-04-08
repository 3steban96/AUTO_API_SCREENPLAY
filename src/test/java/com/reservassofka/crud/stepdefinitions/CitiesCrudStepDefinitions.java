package com.reservassofka.crud.stepdefinitions;

import com.reservassofka.crud.models.CityInfo;
import com.reservassofka.crud.questions.CityDetails;
import com.reservassofka.crud.questions.ServerResponse;
import com.reservassofka.crud.tasks.CreateCity;
import com.reservassofka.crud.tasks.DeleteCity;
import com.reservassofka.crud.tasks.GetCity;
import com.reservassofka.crud.tasks.UpdateCity;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

public class CitiesCrudStepDefinitions {

    private String apiUrl;

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        apiUrl = variables.getProperty("restapi.baseurl", "http://localhost:3004");
    }

    @Given("the API client is ready to manage cities")
    public void theApiClientIsReadyToManageCities() {
        theActorCalled("API Client").whoCan(CallAnApi.at(apiUrl));
        theActorInTheSpotlight().attemptsTo(
                com.reservassofka.crud.tasks.GetAuthToken.withCredentials("admin@sofka.com.co", "password1234")
        );
    }

    @When("they create a new city with name {string} and country {string}")
    public void theyCreateANewCityWithNameAndCountry(String name, String country) {
        String uniqueName = name + " " + System.currentTimeMillis();
        theActorInTheSpotlight().attemptsTo(
                CreateCity.withDetails(uniqueName, country)
        );
        theActorInTheSpotlight().remember("expectedCityName", uniqueName);
        theActorInTheSpotlight().remember("expectedCountryName", country);
        String createdCityId = SerenityRest.lastResponse().jsonPath().getString("data.id");
        theActorInTheSpotlight().remember("cityId", createdCityId);
    }

    @Then("the city creation response should be successfully generated")
    public void theCityCreationResponseShouldBeSuccessfullyGenerated() {
        theActorInTheSpotlight().should(
                seeThat("The status code is 201", ServerResponse.statusCode(), equalTo(201))
        );
    }

    @When("they retrieve the newly created city")
    public void theyRetrieveTheNewlyCreatedCity() {
        String cityId = theActorInTheSpotlight().recall("cityId");
        theActorInTheSpotlight().attemptsTo(
                GetCity.byId(cityId)
        );
    }

    @Then("the retrieved city details should match {string} and {string}")
    public void theRetrievedCityDetailsShouldMatchAnd(String name, String country) {
        String expectedName = theActorInTheSpotlight().recall("expectedCityName");
        String expectedCountry = theActorInTheSpotlight().recall("expectedCountryName");
        theActorInTheSpotlight().should(
                seeThat("The status code is 200", ServerResponse.statusCode(), equalTo(200)),
                seeThat("The city details", CityDetails.fromResponse(), equalTo(new CityInfo(expectedName, expectedCountry)))
        );
    }

    @When("they update the city name to {string} and country to {string}")
    public void theyUpdateTheCityNameToAndCountryTo(String newName, String newCountry) {
        String uniqueNewName = newName + " " + System.currentTimeMillis();
        String cityId = theActorInTheSpotlight().recall("cityId");
        theActorInTheSpotlight().attemptsTo(
                UpdateCity.withDetails(cityId, uniqueNewName, newCountry)
        );
        theActorInTheSpotlight().remember("expectedCityName", uniqueNewName);
        theActorInTheSpotlight().remember("expectedCountryName", newCountry);
    }

    @Then("the city update response should be successfully generated")
    public void theCityUpdateResponseShouldBeSuccessfullyGenerated() {
        theActorInTheSpotlight().should(
                seeThat("The status code is 200", ServerResponse.statusCode(), equalTo(200))
        );
    }

    @When("they retrieve the updated city")
    public void theyRetrieveTheUpdatedCity() {
        theyRetrieveTheNewlyCreatedCity();
    }

    @When("they delete the city")
    public void theyDeleteTheCity() {
        String cityId = theActorInTheSpotlight().recall("cityId");
        theActorInTheSpotlight().attemptsTo(
                DeleteCity.byId(cityId)
        );
    }

    @Then("the city deletion response should be successful")
    public void theCityDeletionResponseShouldBeSuccessful() {
        theActorInTheSpotlight().should(
                seeThat("The status code is 204", ServerResponse.statusCode(), equalTo(204))
        );
    }

    @When("they try to retrieve the deleted city")
    public void theyTryToRetrieveTheDeletedCity() {
        theyRetrieveTheNewlyCreatedCity();
    }

    @Then("the system should indicate that the city was not found")
    public void theSystemShouldIndicateThatTheCityWasNotFound() {
        theActorInTheSpotlight().should(
                seeThat("The status code is 404", ServerResponse.statusCode(), equalTo(404))
        );
    }
}
