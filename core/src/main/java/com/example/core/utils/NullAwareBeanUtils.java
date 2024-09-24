package com.example.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

@UtilityClass
public class NullAwareBeanUtils {
    public static void copyNonNullProperties(Object source, Object target) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value != null) {
                    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String[] getNullPropertyNames(Object source) {
        final Field[] fields = source.getClass().getDeclaredFields();
        return Arrays.stream(fields)
                .filter(f -> {
                    try {
                        f.setAccessible(true);
                        return f.get(source) == null;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(Field::getName)
                .toArray(String[]::new);
    }
}
