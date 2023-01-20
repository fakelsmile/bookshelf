package com.fakelsmile.bookshelf.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestUtil {
    private TestUtil() {
    }
    public final static ObjectMapper objectMapper = new ObjectMapper();

    public static String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
