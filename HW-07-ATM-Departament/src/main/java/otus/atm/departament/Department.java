package otus.atm.departament;

import otus.atm.ATM;
import otus.atm.visitor.BalanceVisitor;
import otus.atm.visitor.RestoreInitStateVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Department {

    private final List<ATM> atms = new ArrayList<>();

    public void addAtm(ATM atm) {
        atms.add(atm);
    }

    public void getBalance() {
        BalanceVisitor balanceVisitor = new BalanceVisitor();
        atms.forEach(a -> a.accept(balanceVisitor));
//        atms.stream().mapToLong(ATM::getTotalBalance).sum();
    }

    public void restoreAllATMInitState() {
        RestoreInitStateVisitor restoreInitStateVisitor = new RestoreInitStateVisitor();
        atms.forEach(a -> a.accept(restoreInitStateVisitor));
//        atms.forEach(ATM::restoreInitialATMState);
    }

    public List<ATM> getAtms() {
        return Collections.unmodifiableList(atms);
    }
}
