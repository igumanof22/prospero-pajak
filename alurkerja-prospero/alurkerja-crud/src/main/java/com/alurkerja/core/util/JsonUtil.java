package com.alurkerja.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Logger;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(JsonUtil.class.getName());

    public static String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            return null;
        }
    }

    public static <E> E deserialize(String json, Class<E> cls) {
        try {
            return objectMapper.readValue(json, cls);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            return null;
        }
    }

    public static <E> E deserializeIgnoreUnknown(String json, Class<E> cls) {
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(json, cls);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            return null;
        }
    }

    public static <E> E deserialize(String json, TypeReference<E> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            return null;
        }
    }

    public static <E> E convert(Object map, Class<E> cls) {
        return objectMapper.convertValue(map, cls);
    }

    public static <E> E convert(Object map, TypeReference<E> typeReference) {
        return objectMapper.convertValue(map, typeReference);
    }
}
