package otus.atm;

class ATMMemento {
    private final ATMImpl atm;

    ATMMemento(ATMImpl atm) {
        this.atm = atm;
    }

    ATMImpl getSavedState() {
        return atm;
    }
}
