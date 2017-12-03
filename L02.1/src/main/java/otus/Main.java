package otus;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ObjSizeService objSizeService = new ObjSizeService();

        System.out.println("Object: " +
                objSizeService.calculate(Object::new) + " bytes");

        System.out.println("Empty string: " +
                objSizeService.calculate(() -> new String("")) + " bytes");

        System.out.println("Class with one int field: " +
                objSizeService.calculate(OneField::new) + " bytes");

        System.out.println("Class with two int fields: " +
                objSizeService.calculate(TwoField::new) + " bytes");

        System.out.println("Class with three int fields: " +
                objSizeService.calculate(ThreeField::new) + " bytes");

        System.out.println("Class with four int fields: " +
                objSizeService.calculate(FourField::new) + " bytes");


        System.out.println("\nПосмотрим на рост контейнера в зависимости от количества элементов в нем");
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            System.out.println("Object[" + i + "]: " +
                    objSizeService.calculate(() -> new Object[finalI]) + " bytes");
        }
    }


    private static class OneField {
        private int i = 0;
    }

    private static class TwoField {
        private int i = 0;
        private int i2 = 0;
    }

    private static class ThreeField {
        private int i = 0;
        private int i2 = 0;
        private int i3 = 0;
    }

    private static class FourField {
        private int i = 0;
        private int i2 = 0;
        private int i3 = 0;
        private int i4 = 0;
    }
}
