package com.reservassofka.crud.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/cities_crud.feature",
        glue = "com.reservassofka.crud.stepdefinitions",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class CitiesCrudRunner {
}
