package otus;

import otus.atm.ATM;
import otus.atm.BanknoteDenomination;
import otus.terminal.ATMTerminal;

public class Main {
    public static void main(String[] args) throws Exception {

        ATM atm = new ATM();

//        atm.addBanknote(BanknoteDenomination.ONE);
//        atm.addBanknote(BanknoteDenomination.ONE);
//        atm.addBanknote(BanknoteDenomination.ONE);
//        atm.addBanknote(BanknoteDenomination.FIVE);
//        atm.addBanknote(BanknoteDenomination.FIFTY);
//        atm.addBanknote(BanknoteDenomination.TEN);
//        atm.addBanknote(BanknoteDenomination.TEN);

        try (ATMTerminal atmTerminal = new ATMTerminal(atm, System.out, System.in)) {
            atmTerminal.run();
        }
    }
}
