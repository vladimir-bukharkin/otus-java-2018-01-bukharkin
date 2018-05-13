package otus.atm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otus.atm.exception.BillCellException;

public class ATMTest {

    private ATM atm;

    @Before
    public void setUp() {
        atm = new ATM();
    }

    @Test
    public void atmInitTest() {
        for (BanknoteDenomination banknoteDenomination : BanknoteDenomination.values()) {
            Assert.assertEquals(banknoteDenomination, atm.getBillCell(banknoteDenomination).getBanknoteDenomination());
        }
    }

    @Test
    public void addBanknote() {
        atm.addBanknote(BanknoteDenomination.ONE);
        atm.addBanknote(BanknoteDenomination.ONE);
        Assert.assertEquals(2, atm.getBillCell(BanknoteDenomination.ONE).getBanknoteCount());
        
        atm.addBanknote(BanknoteDenomination.FIVE);
        Assert.assertEquals(1, atm.getBillCell(BanknoteDenomination.FIVE).getBanknoteCount());
        Assert.assertEquals(0, atm.getBillCell(BanknoteDenomination.TEN).getBanknoteCount());
        Assert.assertEquals(0, atm.getBillCell(BanknoteDenomination.FIFTY).getBanknoteCount());
    }

    @Test
    public void withdraw() {
        atm.addBanknote(BanknoteDenomination.ONE, 10);
        atm.addBanknote(BanknoteDenomination.FIVE, 10);
        atm.addBanknote(BanknoteDenomination.TEN, 10);
        atm.addBanknote(BanknoteDenomination.FIFTY, 10);

        atm.withdraw(15);
    }

    @Test
    public void withdrawNegativeAmount() {
        atm.addBanknote(BanknoteDenomination.ONE, 10);

        atm.withdraw(-1);
    }

    @Test
    public void withdrawAll() throws BillCellException {
        atm.addBanknote(BanknoteDenomination.ONE, 10);
        atm.addBanknote(BanknoteDenomination.FIVE, 10);
        atm.addBanknote(BanknoteDenomination.TEN, 10);
        atm.addBanknote(BanknoteDenomination.FIFTY, 10);

        Assert.assertEquals(10 + 10*5 + 10*10 + 10*50, atm.withdrawAll());

        for (BanknoteDenomination banknoteDenomination : BanknoteDenomination.values()) {
            Assert.assertEquals(0, atm.getBillCell(banknoteDenomination).getBanknoteCount());
        }
    }
}