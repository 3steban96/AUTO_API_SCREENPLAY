package com.reservassofka.crud.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/api_qr_checkin.feature",
        tags = "@api",
        glue = {"com.reservassofka.crud"},
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class ApiQrCheckinRunner {
}
