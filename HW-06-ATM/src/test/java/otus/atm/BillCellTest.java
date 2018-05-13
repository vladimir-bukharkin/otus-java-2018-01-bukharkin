package otus.atm;

import org.junit.Assert;
import org.junit.Test;
import otus.atm.exception.BillCellException;

public class BillCellTest {

    @Test
    public void addBanknote() throws BillCellException {
        BillCell billCell = new BillCell(BanknoteDenomination.FIVE);
        billCell.addBanknote();
        Assert.assertEquals(1, billCell.getBanknoteCount());
        billCell.addSeveralBanknotes(7);
        Assert.assertEquals(8, billCell.getBanknoteCount());
    }

    @Test
    public void withdrawBanknote() throws BillCellException {
        BillCell billCell = new BillCell(BanknoteDenomination.FIVE, 10);
        Assert.assertEquals(2, billCell.withdrawBanknotes(2));
        Assert.assertEquals(8, billCell.getBanknoteCount());

        Assert.assertEquals(4, billCell.withdrawBanknotes(4));
        Assert.assertEquals(4, billCell.getBanknoteCount());

        Assert.assertEquals(4, billCell.withdrawBanknotes(4));
        Assert.assertEquals(0, billCell.getBanknoteCount());
    }

    @Test(expected = BillCellException.class)
    public void withdrawMoreThenBanknoteCountThrowException() throws BillCellException {
        BillCell billCell = new BillCell(BanknoteDenomination.FIVE, 10);
        billCell.withdrawBanknotes(11);
    }
}
