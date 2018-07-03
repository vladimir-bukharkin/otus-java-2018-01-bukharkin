package otus.atm.visitor;

import otus.atm.ATMType1;
import otus.atm.ATMType2;

public interface Visitor {

    void visit(ATMType1 atmType1);

    void visit(ATMType2 atmType2);
}
