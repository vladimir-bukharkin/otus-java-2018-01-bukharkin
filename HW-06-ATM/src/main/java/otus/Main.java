package otus;

import otus.atm.ATM;
import otus.atm.BanknoteDenomination;
import otus.atm.exception.ATMException;

public class Main {
    public static void main(String[] args) throws ATMException {
        ATM atm = new ATM();
        // Банкомат принимает по одной банкноте
        atm.addBanknote(BanknoteDenomination.ONE);
        atm.addBanknote(BanknoteDenomination.ONE);
        atm.addBanknote(BanknoteDenomination.ONE);
        atm.addBanknote(BanknoteDenomination.FIVE);
        atm.addBanknote(BanknoteDenomination.FIFTY);
        atm.addBanknote(BanknoteDenomination.TEN);
        atm.addBanknote(BanknoteDenomination.TEN);

        atm.withdraw(23);
        atm.withdrawAll();
    }
}
