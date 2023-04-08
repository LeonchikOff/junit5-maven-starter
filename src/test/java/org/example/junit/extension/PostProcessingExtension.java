package org.example.junit.extension;

import org.example.junit.extension.annotation.SomeAnnotation;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;

public class PostProcessingExtension implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        System.out.println("postProcessTestInstance");
        Field[] fields = testInstance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(SomeAnnotation.class)) {
                Class<?> value = field.getAnnotation(SomeAnnotation.class).value();
                field.setAccessible(true);
                field.set(testInstance, value.getConstructor().newInstance());
            }
        }
    }
}
