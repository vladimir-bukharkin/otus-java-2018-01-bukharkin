package otus.atm;

import otus.atm.exception.ATMException;

import java.util.*;

public class ATM {
    private final Map<BanknoteDenomination, Integer> cells = new HashMap<>();
    private final List<BanknoteDenomination> withdrawOrder = new ArrayList<>();

    private final Object lock = new Object();

    public ATM() {
        initBillCells();
    }

    synchronized void addBanknote(BanknoteDenomination banknoteDenomination, int count) {
        cells.computeIfPresent(banknoteDenomination, (k, v) -> v += count);
    }

    public void addBanknote(BanknoteDenomination banknoteDenomination) {
        addBanknote(banknoteDenomination, 1);
    }

    int getBanknoteCountInCell(BanknoteDenomination banknoteDenomination) {
        return cells.get(banknoteDenomination);
    }

    public Map<BanknoteDenomination, Integer> withdraw(int amount) throws ATMException {
        synchronized (lock) {
            if (amount < 0) {
                throw new ATMException("Illegal amount: " + amount);
            } else {
                return new HashMap<>();
            }
        }
    }

    public int withdrawAll() {
        synchronized (lock) {
            int resultAmount = 0;
            for (Map.Entry<BanknoteDenomination, Integer> withdrawEntry : cells.entrySet()) {
                int count = withdrawEntry.getValue();
                if (count > 0) {
                    BanknoteDenomination banknoteDenomination = withdrawEntry.getKey();
                    resultAmount += cells.get(banknoteDenomination) * banknoteDenomination.getPar();
                }
            }
            initToZeroCells();
            return resultAmount;
        }
    }

    private void initBillCells() {
        withdrawOrder.addAll(Arrays.asList(BanknoteDenomination.values()));
        withdrawOrder.sort(Comparator.comparingInt(BanknoteDenomination::getPar));
        initToZeroCells();
    }

    private void initToZeroCells() {
        for (BanknoteDenomination banknoteDenomination : BanknoteDenomination.values()) {
            cells.put(banknoteDenomination, 0);
        }
    }
}
