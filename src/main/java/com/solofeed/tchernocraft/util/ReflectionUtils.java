package com.solofeed.tchernocraft.util;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reflection utilities
 */
public final class ReflectionUtils {
    /**
     * private constructor
     */
    private ReflectionUtils(){
        throw new IllegalAccessError("ReflectionUtils constructor must never be called");
    }

    /**
     * Gets all specified annotated classes
     * @param CLAZZ annotation
     * @return all annotated mathching classes
     */
    public static Set<Class<?>> getClasses(final String ITEMS_LOCATION, final Class<? extends Annotation> CLAZZ) {
        //FIXME: replace with Reflections lib when forge will update its old deprecated guava 17.0 lib dependency ...
        Set<Class<?>> classes = new HashSet<>();
        final ClassLoader loader = ReflectionUtils.class.getClassLoader();
        try {
            ClassPath classpath = ClassPath.from(loader);
            classes = classpath.getTopLevelClasses(ITEMS_LOCATION)
                    .stream()
                    .map(ClassPath.ClassInfo::load)
                    .filter(c -> c.isAnnotationPresent(CLAZZ))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
