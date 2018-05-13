package otus.atm;

import otus.atm.exception.ATMException;
import otus.atm.exception.BillCellException;

import java.util.*;

public class ATM {
    private final Map<BanknoteDenomination, BillCell> billCells = new HashMap<>();

    private final Map<BanknoteDenomination, Integer> banknoteCountMap = new HashMap<>();
    private final List<BanknoteDenomination> withdrawOrder = new ArrayList<>();

    public ATM() {
        initBillCells();
    }

    BillCell getBillCell(BanknoteDenomination banknoteDenomination) {
        return billCells.get(banknoteDenomination);
    }

    void addBanknote(BanknoteDenomination banknoteDenomination, int count) throws BillCellException {
        billCells.get(banknoteDenomination).addSeveralBanknotes(count);
        banknoteCountMap.computeIfPresent(banknoteDenomination, (k, v) -> v += count);
    }

    public void addBanknote(BanknoteDenomination banknoteDenomination) throws BillCellException {
        addBanknote(banknoteDenomination, 1);
    }

    public int withdraw(int amount) throws ATMException {
        if (amount < 0){
            throw new ATMException("Illegal amount: " + amount);
        } else {
            return 0;
        }
    }

    public int withdrawAll() throws BillCellException {
        int resultAmount = 0;
        for (Map.Entry<BanknoteDenomination, Integer> withdrawEntry : banknoteCountMap.entrySet()) {
            int count = withdrawEntry.getValue();
            if (count > 0) {
                BanknoteDenomination banknoteDenomination = withdrawEntry.getKey();
                resultAmount += billCells.get(banknoteDenomination).withdrawBanknotes(count) * banknoteDenomination.getPar();
            }
        }
        initToZeroBanknoteCountMap();
        return resultAmount;
    }

    private void initToZeroBanknoteCountMap() {
        for (BanknoteDenomination banknoteDenomination : BanknoteDenomination.values()) {
            banknoteCountMap.put(banknoteDenomination, 0);
        }
    }

    private void initBillCells() {
        for (BanknoteDenomination banknoteDenomination : BanknoteDenomination.values()) {
            billCells.put(banknoteDenomination, new BillCell(banknoteDenomination));
            withdrawOrder.add(banknoteDenomination);
        }
        initToZeroBanknoteCountMap();
        withdrawOrder.sort(Comparator.comparingInt(BanknoteDenomination::getPar));
    }
}
