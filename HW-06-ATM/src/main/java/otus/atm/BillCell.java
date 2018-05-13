package otus.atm;

import otus.atm.exception.BillCellException;

class BillCell {

    private final BanknoteDenomination banknoteDenomination;
    private int count = 0;

    BillCell(BanknoteDenomination banknoteDenomination) {
        this.banknoteDenomination = banknoteDenomination;
    }

    BillCell(BanknoteDenomination banknoteDenomination, int banknoteCount) {
        this.banknoteDenomination = banknoteDenomination;
        this.count = banknoteCount;
    }

    void addBanknote() {
        count++;
    }

    void addSeveralBanknotes(int count) throws BillCellException {
        if (count < 0) {
            throw new BillCellException("Internal Error: Illegal banknote count: " + count);
        }
        this.count += count;
    }

    int getBanknoteCount() {
        return count;
    }

    int withdrawBanknotes(int count) throws BillCellException {
        if (getBanknoteCount() < count) {
            throw new BillCellException("Internal error: Banknote deficiency");
        } else if (count < 0) {
            throw new BillCellException("Internal Error: Illegal banknote count: " + count);
        } else{
            this.count -= count;
            return count;
        }
    }

    BanknoteDenomination getBanknoteDenomination() {
        return banknoteDenomination;
    }
}
