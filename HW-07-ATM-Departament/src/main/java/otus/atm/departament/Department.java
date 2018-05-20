package otus.atm.departament;

import otus.atm.ATM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Department {
    private final List<ATM> atms = new ArrayList<>();

    public void addAtm(ATM atm) {
        atms.add(atm);
    }

    public long getBalance() {
        return atms.stream().mapToLong(ATM::getTotalBalance).sum();
    }

    public void restoreAllATMInitState() {
        atms.forEach(ATM::restoreInitialATMState);
    }

    public List<ATM> getAtms() {
        return Collections.unmodifiableList(atms);
    }
}
