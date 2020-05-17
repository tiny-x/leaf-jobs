package com.leaf.jobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.leaf.jobs.model.User;

public class JacksonTest {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            User user = new User();
            user.setProfile("xx");
            user.setName("yefei");
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            System.out.println(jsonString);

        } catch (Exception e) {

        }
    }
}
