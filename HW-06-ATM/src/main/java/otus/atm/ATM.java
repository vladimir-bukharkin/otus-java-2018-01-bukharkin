package otus.atm;

import otus.atm.exception.ATMException;
import otus.atm.util.ATMUtil;

import java.util.*;

public class ATM {
    private final Map<BanknoteDenomination, Integer> cells = new HashMap<>();
    private final List<BanknoteDenomination> withdrawOrder = new ArrayList<>();

    public ATM() {
        initBillCells();
    }

    void addBanknote(BanknoteDenomination banknoteDenomination, int count) {
        synchronized (this) {
            cells.computeIfPresent(banknoteDenomination, (k, v) -> v += count);
        }
    }

    public void addBanknote(BanknoteDenomination banknoteDenomination) {
        addBanknote(banknoteDenomination, 1);
    }

    int getBanknoteCountInCell(BanknoteDenomination banknoteDenomination) {
        return cells.get(banknoteDenomination);
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

    Map<BanknoteDenomination, Integer> getBanknoteCellBalance() {
        return cells;
    }


    public Map<BanknoteDenomination, Integer> withdrawAll() {
        synchronized (this) {
            Map<BanknoteDenomination, Integer> result = new HashMap<>(cells);
            initToZeroCells();
            return result;
        }
    }

    private void initBillCells() {
        withdrawOrder.addAll(Arrays.asList(BanknoteDenomination.values()));
        withdrawOrder.sort(Comparator.comparingInt(BanknoteDenomination::getPar).reversed());
        initToZeroCells();
    }

    private void initToZeroCells() {
        for (BanknoteDenomination banknoteDenomination : BanknoteDenomination.values()) {
            cells.put(banknoteDenomination, 0);
        }
    }

    @Override
    public String toString() {
        return "ATM{" +
                "cells=" + cells +
                '}';
    }
}
