package otus;

import otus.atm.ATM;
import otus.atm.ATMType1;
import otus.atm.ATMType2;
import otus.atm.BanknoteDenomination;
import otus.atm.departament.Department;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {

        ATM atm1 = new ATMType1(new HashMap<BanknoteDenomination, Integer>() {{
            put(BanknoteDenomination.ONE, 10);
            put(BanknoteDenomination.TEN, 10);
        }});
        ATM atm2 = new ATMType1(new HashMap<BanknoteDenomination, Integer>() {{
            put(BanknoteDenomination.FIFTY, 1);
            put(BanknoteDenomination.FIVE, 5);
        }});
        ATM atm3 = new ATMType2();

        Department department = new Department();
        department.addAtm(atm1);
        department.addAtm(atm2);
        department.addAtm(atm3);

        department.getBalance();

        atm1.withdraw(8);
        System.out.println("\nATM 1 withdraw 8");
        atm2.putBanknote(BanknoteDenomination.FIVE);
        System.out.println("ATM 2 put FIVE Banknote");

        department.getBalance();

        System.out.println("\nRestore all atm initial states");
        department.restoreAllATMInitState();

        department.getBalance();
    }
}
