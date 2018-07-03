package otus.atm.visitor;

import otus.atm.ATMType1;
import otus.atm.ATMType2;

public class BalanceVisitor implements Visitor {

    @Override
    public void visit(ATMType1 atmType1) {
        System.out.println("ATM balance of type 1: " + atmType1.getTotalBalance());
    }

    @Override
    public void visit(ATMType2 atmType2) {
        System.out.println("ATM balance of type 2: " + atmType2.getTotalBalance());
    }
}
