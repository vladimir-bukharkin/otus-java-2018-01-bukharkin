package otus.atm;

class ATMMemento {
    private final ATMType1 atm;

    ATMMemento(ATMType1 atm) {
        this.atm = atm;
    }

    ATMType1 getSavedState() {
        return atm;
    }
}
