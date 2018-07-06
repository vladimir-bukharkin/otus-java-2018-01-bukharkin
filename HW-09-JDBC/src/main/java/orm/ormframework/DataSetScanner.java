package orm.ormframework;

import orm.ormframework.util.ClassScanner;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

class DataSetScanner {

    @SuppressWarnings("unchecked")
    static Set<Class<? extends DataSet>> findAll(String packageName) throws IOException {
        return ClassScanner.getClassesInPackage(packageName)
                .stream()
                .filter(c -> DataSet.class.isAssignableFrom(c) && c != DataSet.class)
                .map(c -> (Class<? extends DataSet>) c)
                .collect(Collectors.toSet());
    }

    static Set<Class<? extends DataSet>> findAll() throws IOException {
        return findAll("orm");
    }
}
