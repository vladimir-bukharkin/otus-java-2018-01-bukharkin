package otus.atm;

import otus.atm.exception.ATMException;
import otus.atm.util.ATMUtil;

import java.util.*;

public class ATMImpl implements ATM {

    private final Map<BanknoteDenomination, Integer> cells = new HashMap<>();
    private final List<BanknoteDenomination> withdrawOrder = new ArrayList<>();
    private final ATMMemento initialState;

    public ATMImpl(Map<BanknoteDenomination, Integer> cells) {
        if (cells == null) {
            withdrawOrder.addAll(Arrays.asList(BanknoteDenomination.values()));
            initToZeroCells();
        } else {
            this.cells.putAll(cells);
            withdrawOrder.addAll(cells.keySet());
        }
        withdrawOrder.sort(Comparator.comparingInt(BanknoteDenomination::getPar).reversed());
        initialState = new ATMMemento(this.cells);
    }

    public ATMImpl() {
        this(null);
    }

    public void putBanknote(BanknoteDenomination banknoteDenomination) {
        putBanknote(banknoteDenomination, 1);
    }

    public Map<BanknoteDenomination, Integer> withdraw(int amount) throws ATMException {
        synchronized (this) {
            if (amount < 0) {
                throw new ATMException("Error: Illegal amount: " + amount);
            } else {
                return withdrawBySuperGridAlgorithm(amount);
            }
        }
    }

    public int getTotalBalance() {
        return ATMUtil.getAmountOfBanknotesSet(cells);
    }

    public Map<BanknoteDenomination, Integer> getBanknoteCellsBalance() {
        return cells;
    }

    public Map<BanknoteDenomination, Integer> withdrawAll() {
        synchronized (this) {
            Map<BanknoteDenomination, Integer> result = new HashMap<>(cells);
            initToZeroCells();
            return result;
        }
    }

    public void restoreInitialATMState() {
        cells.clear();
        cells.putAll(initialState.getSavedState());
    }


    void putBanknote(BanknoteDenomination banknoteDenomination, int count) {
        synchronized (this) {
            cells.computeIfPresent(banknoteDenomination, (k, v) -> v += count);
        }
    }

    int getBanknoteCountInCell(BanknoteDenomination banknoteDenomination) {
        return cells.get(banknoteDenomination);
    }


    private Map<BanknoteDenomination, Integer> withdrawBySuperGridAlgorithm(int amount) throws ATMException {
        Map<BanknoteDenomination, Integer> result = new HashMap<>();

        int remain = amount;
        for (BanknoteDenomination denomination : withdrawOrder) {
            int banknoteCount = cells.get(denomination);
            if (banknoteCount > 0 && remain >= denomination.getPar()) {
                int needToWithdrawBanknote = remain / denomination.getPar();
                int withdrawBanknote = needToWithdrawBanknote > banknoteCount ? banknoteCount : needToWithdrawBanknote;
                result.put(denomination, withdrawBanknote);
                remain -= withdrawBanknote * denomination.getPar();
                if (remain == 0) {
                    break;
                }
            }
        }
        if (remain == 0) {
            result.forEach((k, v) -> cells.computeIfPresent(k, (k2, v2) -> v2 -= v));
            return result;
        } else {
            throw new ATMException("Error: Not enough money");
        }
    }


    private void initToZeroCells() {
        for (BanknoteDenomination banknoteDenomination : BanknoteDenomination.values()) {
            cells.put(banknoteDenomination, 0);
        }
    }

    @Override
    public String toString() {
        return "ATMImpl{" +
                "cells=" + cells +
                '}';
    }
}
