package com.leaf.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;

public class JsonSpiltTest {

    public static void main(String[] args) throws Exception {
        String a = "[{\n" +
                "  \"name\" : \"yefei\",\n" +
                "  \"age\" : null,\n" +
                "  \"profile\" : \"xx\"\n" +
                "}," +
                "{\n" +
                "  \"name\" : \"yefei\",\n" +
                "  \"age\" : 10,\n" +
                "  \"profile\" : \"xx\"\n" +
                "}," +
                "{\n" +
                "  \"name\" : \"yefei\",\n" +
                "  \"age\" : \"aa\",\n" +
                "  \"profile\" : \"xx\"\n" +
                "}]";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(a);
        for (JsonNode node : jsonNode) {
            System.out.println(node.toString());
        }
    }
}
