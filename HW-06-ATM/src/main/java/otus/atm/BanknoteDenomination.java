package otus.atm;

public enum BanknoteDenomination {
    ONE(1),
    FIVE(5),
    TEN(10),
    FIFTY(50);

    private final int par;

    BanknoteDenomination(int par) {
        this.par = par;
    }

    public int getPar() {
        return par;
    }
}
