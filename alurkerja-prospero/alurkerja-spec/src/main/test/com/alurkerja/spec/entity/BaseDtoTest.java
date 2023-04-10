package com.alurkerja.spec.entity;
import com.alurkerja.spec.spec.SpecService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

class BaseDtoTest {

    @Test
    void specifications() throws ClassNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse((new Gson()).toJson (SpecService.specifications((new TestDto()).getClass()         )));
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
    }
}