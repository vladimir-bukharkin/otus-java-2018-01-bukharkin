package otus.atm.visitor;

import otus.atm.ATMType1;
import otus.atm.ATMType2;

public class RestoreInitStateVisitor implements Visitor {

    @Override
    public void visit(ATMType1 atmType1) {
        atmType1.restoreInitialATMState();
    }

    @Override
    public void visit(ATMType2 atmType2) {
        atmType2.restoreInitialATMState();
    }
}
