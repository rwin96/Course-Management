package course.management.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SimpleDTOConvertor {
    public static Object convertThisToThat(Object source, Class target) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = target.getConstructor();
        Object result = constructor.newInstance();

        Class sourceClass = source.getClass();

        Field[] targetFields = target.getDeclaredFields();
        Field[] sourceFields = sourceClass.getDeclaredFields();

        for (Field targetField : targetFields) {
            targetField.setAccessible(true);
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                if (targetField.getName().equals(sourceField.getName()) && targetField.getType().isAssignableFrom(sourceField.getType())) {
                    targetField.set(result, sourceField.get(source));
                    sourceField.setAccessible(false);
                    break;
                }
                sourceField.setAccessible(false);
            }
            targetField.setAccessible(false);
        }

        return result;
    }
}
