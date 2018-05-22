package otus.objects;

public enum Enum {
    ONE("one-1"),
    TWO("two-1"),
    THREE("three-1");

    private final String string;

    Enum(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
