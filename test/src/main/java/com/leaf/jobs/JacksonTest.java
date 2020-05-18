package com.leaf.jobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.leaf.jobs.model.User;

import java.util.List;

public class JacksonTest {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            User user = new User();
            user.setProfile("xx");
            user.setName("yefei");
            user.setAge("11");
            String jsonString = mapper.writeValueAsString(user);
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            System.out.println(jsonString);
            TypeFactory typeFactory = mapper.getTypeFactory();
            List<String> a = mapper.readValue("[\"a\", \"B\"]", typeFactory.constructCollectionType(List.class, String.class));
            System.out.println(a);

            String xx = "\"a\"";
            String s = mapper.readValue(xx, String.class);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
