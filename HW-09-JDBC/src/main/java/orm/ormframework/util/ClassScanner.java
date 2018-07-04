package orm.ormframework.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ClassScanner {
    public static Set<Class<?>> getClassesInPackage(String packageName) throws IOException {
        Set<Class<?>> result = new HashSet<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ClassPath classPath = ClassPath.from(classLoader);
        ImmutableSet<ClassPath.ClassInfo> classInfoSet =  classPath.getTopLevelClassesRecursive(packageName);

        classInfoSet.iterator().forEachRemaining(c -> result.add(c.load()));
        return result;
    }
}
