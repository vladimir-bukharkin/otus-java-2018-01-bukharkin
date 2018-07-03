package otus.atm;

import otus.atm.exception.ATMException;
import otus.atm.visitor.ATMComponent;

import java.util.Map;

public interface ATM extends ATMComponent {

    public void putBanknote(BanknoteDenomination banknoteDenomination);

    public Map<BanknoteDenomination, Integer> withdraw(int amount) throws ATMException;

    public Map<BanknoteDenomination, Integer> withdrawAll();

    public int getTotalBalance();

    public Map<BanknoteDenomination, Integer> getBanknoteCellsBalance();

    public void restoreInitialATMState();
}
