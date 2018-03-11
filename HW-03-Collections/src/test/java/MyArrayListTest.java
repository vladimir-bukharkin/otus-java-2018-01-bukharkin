import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

/**
 * Тестирование MyArrayList без проверки выбрасываемых исключений
 */
@RunWith(Parameterized.class)
public class MyArrayListTest<T> {

    private List<T> expectedList;

    private List<T> myList;

    @BeforeEach
    public void init() {
        expectedList = null;
        myList = null;
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> dataForTest() {
        Owl owl1 = new Owl("otus", 2);

        return Arrays.asList(new Object[][]{
                {new Integer[] {4, 5 , 6, -10},
                        new Integer[]{-124, 324, 0, 5, 6, null}},
                {new String[] {"werwef", "ergtertg" , "sdgfrger", ""},
                        new String[] {"ergtertg", null, "retregdg", ""}},
                {new Owl[] {owl1, new Owl("otus2", 16) , new Owl("otus3", 32)},
                        new Owl[] {new Owl("otus3", 32), new Owl("otus4", 1024), owl1, null}}
        });
    }


    @Parameterized.Parameter
    public T[] values;

    @Parameterized.Parameter(1)
    public T[] additionalValues;

    @Test
    public void instance() {
        Assert.assertTrue(isArraysEquals());

        expectedList = new ArrayList<>();
        myList = new MyArrayList<>();
        Assert.assertTrue(isArraysEquals());

        List<T> inputValuesList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputValuesList);
        myList = new MyArrayList<>(inputValuesList);
        Assert.assertTrue(isArraysEquals());

        Set<T> inputValuesSet = new HashSet<>(inputValuesList);
        expectedList = new ArrayList<>(inputValuesSet);
        myList = new MyArrayList<>(inputValuesSet);
        Assert.assertTrue(isArraysEquals());
    }

    @Test
    public void size() {
        expectedList = new ArrayList<>();
        myList = new MyArrayList<>();
        for (T value : values) {
            expectedList.add(value);
            myList.add(value);
            Assert.assertEquals(expectedList.size(), myList.size());
        }
        expectedList.remove(0);
        myList.remove(0);
        Assert.assertEquals(expectedList.size(), myList.size());
    }

    @Test
    public void isEmpty() {
        expectedList = new ArrayList<>();
        myList = new MyArrayList<>();
        Assert.assertEquals(expectedList.isEmpty(), myList.isEmpty());
        for (T value : values) {
            expectedList.add(value);
            myList.add(value);
            Assert.assertEquals(expectedList.isEmpty(), myList.isEmpty());
        }
        expectedList.clear();
        myList.clear();
        Assert.assertEquals(expectedList.isEmpty(), myList.isEmpty());
    }

    @Test
    public void contains() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>();
        myList = new MyArrayList<>();
        for (T value : additionalValues) {
            Assert.assertEquals(expectedList.contains(value), myList.contains(value));
        }
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        for (T value : additionalValues) {
            Assert.assertEquals(expectedList.contains(value), myList.contains(value));
        }
    }

    @Test
    public void toArray() {
        T[] lessSizeArray = (T[]) new Object[values.length - 2];
        T[] sizeArray = (T[]) new Object[values.length];
        T[] moreSizeArray = (T[]) new Object[values.length + 2];

        expectedList = new ArrayList<>();
        myList = new MyArrayList<>();
        Assert.assertArrayEquals(expectedList.toArray(), myList.toArray());
        Assert.assertArrayEquals(expectedList.toArray(lessSizeArray), myList.toArray(lessSizeArray));
        Assert.assertArrayEquals(expectedList.toArray(sizeArray), myList.toArray(sizeArray));
        Assert.assertArrayEquals(expectedList.toArray(moreSizeArray), myList.toArray(moreSizeArray));

        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        Assert.assertArrayEquals(expectedList.toArray(), myList.toArray());
        Assert.assertArrayEquals(expectedList.toArray(lessSizeArray), myList.toArray(lessSizeArray));
        Assert.assertArrayEquals(expectedList.toArray(sizeArray), myList.toArray(sizeArray));
        Assert.assertArrayEquals(expectedList.toArray(moreSizeArray), myList.toArray(moreSizeArray));
    }

    @Test
    public void add() {
        expectedList = new ArrayList<>();
        myList = new MyArrayList<>();
        for (T value : values) {
            expectedList.add(value);
            myList.add(value);
        }
        for (T value : additionalValues) {
            expectedList.add(value);
            myList.add(value);
        }
        Assert.assertTrue(isArraysEquals());

        List<T> inputValuesList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputValuesList);
        myList = new MyArrayList<>(inputValuesList);
        for (T value : values) {
            expectedList.add(value);
            myList.add(value);
        }
        Assert.assertTrue(isArraysEquals());
    }

    @Test
    public void remove() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        for (T value : additionalValues) {
            expectedList.remove(value);
            myList.remove(value);
            Assert.assertTrue(isArraysEquals());
        }
        for (T value : values) {
            expectedList.remove(value);
            myList.remove(value);
            Assert.assertTrue(isArraysEquals());
        }

        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        for (int i = 0; i < expectedList.size(); i++) {
            expectedList.remove(i);
            myList.remove(i);
            Assert.assertTrue(isArraysEquals());
        }
    }

    @Test
    public void removeAll() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);

        List<T> removeList = Arrays.asList(additionalValues);
        expectedList.removeAll(removeList);
        myList.removeAll(removeList);
        Assert.assertTrue(isArraysEquals());

        expectedList.removeAll(inputList);
        myList.removeAll(inputList);
        Assert.assertTrue(isArraysEquals());
    }

    @Test
    public void clear() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        expectedList.clear();
        myList.clear();

        Assert.assertTrue(isArraysEquals());
    }

    @Test
    public void retainAll() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);

        List<T> removeList = Arrays.asList(additionalValues);
        expectedList.retainAll(removeList);
        myList.retainAll(removeList);
        Assert.assertTrue(isArraysEquals());

        expectedList.retainAll(inputList);
        myList.retainAll(inputList);
        Assert.assertTrue(isArraysEquals());
    }

    @Test
    public void addAll() {
        List<T> inputValuesList = Arrays.asList(values);
        expectedList = new ArrayList<>();
        expectedList.addAll(inputValuesList);
        myList = new MyArrayList<>();
        myList.addAll(inputValuesList);
        Assert.assertTrue(isArraysEquals());

        expectedList.addAll(2, inputValuesList);
        myList.addAll(2, inputValuesList);
        Assert.assertTrue(isArraysEquals());

        Set<T> inputValuesSet = new HashSet<>(inputValuesList);
        expectedList.addAll(inputValuesSet);
        myList.addAll(inputValuesSet);
        Assert.assertTrue(isArraysEquals());

        expectedList.addAll(0, inputValuesList);
        myList.addAll(0, inputValuesList);
    }

    @Test
    public void containsAll() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        Assert.assertEquals(expectedList.containsAll(inputList), myList.containsAll(inputList));

        List<T> additionalList = Arrays.asList(additionalValues);
        Assert.assertEquals(expectedList.containsAll(additionalList), myList.containsAll(additionalList));
    }

    @Test
    public void get() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(expectedList.get(i), myList.get(i));
        }
    }

    @Test
    public void set() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        for (int i = 0; i < expectedList.size(); i++) {
            for (int j = expectedList.size()-1; j >= 0; j--) {
                expectedList.set(i, values[j]);
                myList.set(i, values[j]);
                Assert.assertEquals(expectedList.get(i), myList.get(i));
            }
        }
    }

    @Test
    public void indexOf() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(expectedList.indexOf(values[i]), myList.indexOf(values[i]));
        }
    }

    @Test
    public void lastIndexOf() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        for (int i = 0; i < expectedList.size(); i++) {
            Assert.assertEquals(expectedList.lastIndexOf(values[i]), myList.lastIndexOf(values[i]));
        }
    }

    @Test
    public void iterator() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        Iterator<T> expectedIterator = expectedList.iterator();
        Iterator<T> myArrayIterator = myList.iterator();
        while (expectedIterator.hasNext()) {
            Assert.assertEquals(expectedIterator.hasNext(), myArrayIterator.hasNext());
            Assert.assertEquals(expectedIterator.next(), myArrayIterator.next());
        }
        Assert.assertEquals(expectedIterator.hasNext(), myArrayIterator.hasNext());
        while (expectedIterator.hasNext()) {
            expectedIterator.remove();
            myArrayIterator.remove();
            Assert.assertTrue(isArraysEquals());
        }
    }

    @Test
    public void listIterator() {
        List<T> inputList = Arrays.asList(values);
        expectedList = new ArrayList<>(inputList);
        myList = new MyArrayList<>(inputList);
        ListIterator<T> expectedIterator = expectedList.listIterator();
        ListIterator<T> myArrayIterator = myList.listIterator();
        while (expectedIterator.hasNext()) {
            Assert.assertEquals(expectedIterator.hasNext(), myArrayIterator.hasNext());
            Assert.assertEquals(expectedIterator.next(), myArrayIterator.next());
        }
        Assert.assertEquals(expectedIterator.hasNext(), myArrayIterator.hasNext());
        while (expectedIterator.hasNext()) {
            expectedIterator.remove();
            myArrayIterator.remove();
            Assert.assertTrue(isArraysEquals());
        }

        expectedIterator = expectedList.listIterator(2);
        myArrayIterator = myList.listIterator(2);
        expectedIterator.add(values[0]);
        myArrayIterator.add(values[0]);
        Assert.assertTrue(isArraysEquals());

        while (expectedIterator.hasPrevious()) {
            Assert.assertEquals(expectedIterator.hasPrevious(), myArrayIterator.hasPrevious());
            Assert.assertEquals(expectedIterator.previous(), myArrayIterator.previous());
            Assert.assertTrue(isArraysEquals());
        }
        Assert.assertEquals(expectedIterator.hasPrevious(), myArrayIterator.hasPrevious());
    }


    private static class Owl {
        private String name;
        private long age;

        public Owl(String name, long age) {
            this.name = name;
            this.age = age;
        }
    }

    private boolean isArraysEquals() {
        if (myList != null && expectedList != null && myList.size() == expectedList.size()) {
            for (int i = 0; i < expectedList.size(); i++) {
                T expectedValue = expectedList.get(i);
                T resValue = myList.get(i);
                if (!(expectedValue == null ? resValue == null : expectedValue.equals(resValue)))
                    return false;
            }
            return true;
        } else return myList == null && expectedList == null;
    }
}

