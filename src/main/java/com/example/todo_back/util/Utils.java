package com.example.todo_back.util;

import com.example.todo_back.dto.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Utils() {

    }

    public static Message getObject(final String message) throws Exception {
            Message m = objectMapper.readValue(message, Message.class);
            return m;

        }

    public static String getString(final Message message) throws Exception {
        return objectMapper.writeValueAsString(message);
    }
}
