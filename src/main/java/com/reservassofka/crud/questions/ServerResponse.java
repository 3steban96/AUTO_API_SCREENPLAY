package com.reservassofka.crud.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ServerResponse implements Question<Integer> {

    public static ServerResponse statusCode() {
        return new ServerResponse();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return SerenityRest.lastResponse().statusCode();
    }
}
