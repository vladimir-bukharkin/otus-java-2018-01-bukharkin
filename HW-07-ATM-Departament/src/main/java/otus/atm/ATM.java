package otus.atm;

import otus.atm.exception.ATMException;

import java.util.Map;

public interface ATM {

    public void putBanknote(BanknoteDenomination banknoteDenomination);

    public Map<BanknoteDenomination, Integer> withdraw(int amount) throws ATMException;

    public Map<BanknoteDenomination, Integer> withdrawAll();

    public int getTotalBalance();

    public Map<BanknoteDenomination, Integer> getBanknoteCellsBalance();

    public void restoreInitialATMState();
}
