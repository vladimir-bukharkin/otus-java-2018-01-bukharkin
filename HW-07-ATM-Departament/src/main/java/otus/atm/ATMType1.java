package otus.atm;

import otus.atm.exception.ATMException;
import otus.atm.util.ATMUtil;
import otus.atm.visitor.Visitor;

import java.util.*;

public class ATMType1 implements ATM {

    private final Map<BanknoteDenomination, Integer> cells = new HashMap<>();
    private final List<BanknoteDenomination> withdrawOrder = new ArrayList<>();
    private final ATMMemento initialState;

    public ATMType1(Map<BanknoteDenomination, Integer> cells) {
        this.cells.putAll(cells);
        withdrawOrder.addAll(cells.keySet());
        withdrawOrder.sort(Comparator.comparingInt(BanknoteDenomination::getPar).reversed());
        initialState = new ATMMemento(new ATMType1(this));
    }

    public ATMType1() {
        withdrawOrder.addAll(Arrays.asList(BanknoteDenomination.values()));
        initToZeroCells();
        withdrawOrder.sort(Comparator.comparingInt(BanknoteDenomination::getPar).reversed());
        initialState = new ATMMemento(new ATMType1(this));
    }

    private ATMType1(ATMType1 cloned) {
        this.cells.putAll(cloned.cells);
        this.withdrawOrder.addAll(cloned.withdrawOrder);
        this.initialState = cloned.initialState;
    }

    public void restoreInitialATMState() {
        ATMType1 initStateAtm = initialState.getSavedState();
        cells.clear();
        cells.putAll(initStateAtm.cells);
        withdrawOrder.clear();
        withdrawOrder.addAll(initStateAtm.withdrawOrder);
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
        return "ATMType1{" +
                "cells=" + cells +
                '}';
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
