package otus;

import otus.atm.ATM;
import otus.atm.ATMImpl;
import otus.atm.BanknoteDenomination;
import otus.atm.departament.Department;
import otus.terminal.ATMTerminal;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {

        ATM atm1 = new ATMImpl(new HashMap<BanknoteDenomination, Integer>() {{
            put(BanknoteDenomination.ONE, 10);
            put(BanknoteDenomination.TEN, 10);
        }});
        ATM atm2 = new ATMImpl(new HashMap<BanknoteDenomination, Integer>() {{
            put(BanknoteDenomination.FIFTY, 1);
            put(BanknoteDenomination.FIVE, 5);
        }});

        Department department = new Department();
        department.addAtm(atm1);
        department.addAtm(atm2);

        System.out.println("ATM1 initial balance: " + atm1.getTotalBalance());
        System.out.println("ATM2 initial balance: " + atm2.getTotalBalance());
        System.out.println("Department initial balance: " + department.getBalance() + "\n");

        atm1.withdraw(8);
        System.out.println("ATM1 withdraw 8. Total Balance: " + atm1.getTotalBalance());
        atm2.putBanknote(BanknoteDenomination.FIVE);
        System.out.println("ATM2 put FIVE Banknote. Total Balance: " + atm2.getTotalBalance() + "\n");

        department.restoreAllATMInitState();
        System.out.println("Restore all atm initial states");

        System.out.println("ATM1 balance: " + atm1.getTotalBalance());
        System.out.println("ATM2 balance: " + atm2.getTotalBalance());
        System.out.println("Department balance: " + department.getBalance() + "\n");
    }
}
