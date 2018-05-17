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

    public static BanknoteDenomination getBanknoteDenomination(String den) {
        for (BanknoteDenomination b : BanknoteDenomination.values()) {
            if (b.toString().equals(den))
                return b;
        }
        return null;
    }
}
