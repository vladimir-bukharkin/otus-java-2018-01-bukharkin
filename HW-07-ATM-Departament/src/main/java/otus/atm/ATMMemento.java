package otus.atm;

import java.util.HashMap;
import java.util.Map;

class ATMMemento {
    private final Map<BanknoteDenomination, Integer> cells;

    ATMMemento(Map<BanknoteDenomination, Integer> cells) {
        this.cells = new HashMap<>(cells);
    }

    Map<BanknoteDenomination, Integer> getSavedState() {
        return cells;
    }
}
