package otus.atm;

import otus.atm.exception.ATMException;
import otus.atm.visitor.Visitor;

import java.util.*;

// Чисто для примера
public class ATMType2 implements ATM {

    private boolean isRestored = false;

    @Override
    public void putBanknote(BanknoteDenomination banknoteDenomination) {
    }

    @Override
    public Map<BanknoteDenomination, Integer> withdraw(int amount) throws ATMException {
        return null;
    }

    @Override
    public Map<BanknoteDenomination, Integer> withdrawAll() {
        return null;
    }

    @Override
    public int getTotalBalance() {
        return 0;
    }

    @Override
    public Map<BanknoteDenomination, Integer> getBanknoteCellsBalance() {
        return null;
    }

    @Override
    public void restoreInitialATMState() {
        isRestored = true;
        System.out.println("ATMType2 restored");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public boolean isRestored() {
        return isRestored;
    }
}
