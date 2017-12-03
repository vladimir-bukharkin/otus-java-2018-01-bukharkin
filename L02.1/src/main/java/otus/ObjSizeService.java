package otus;

public class ObjSizeService {

    // Количество прогонов программы
    private final int repSize = 1;

    // Количество создаваемых объектов
    private final int objsSize = 2_000_000;


    public long calculate(ObjCreator objCreator) throws InterruptedException {

        Runtime runtime = Runtime.getRuntime();

        long result = 0;

        for (int i = 0; i < repSize; i++) {

            System.gc();
            Thread.sleep(1000);

            Object[] array = new Object[objsSize];

            for (int j = 0; j < objsSize; j++) {
                array[j] = objCreator.createInstance();
            }
            result += (runtime.totalMemory() - runtime.freeMemory()) / objsSize;
        }
        return result / repSize;
    }
}
