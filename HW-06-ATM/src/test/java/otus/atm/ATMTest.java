package otus.atm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otus.atm.exception.ATMException;

public class ATMTest {

    private ATM atm;

    @Before
    public void setUp() {
        atm = new ATM();
    }

    @Test
    public void testAddBanknote() {
        atm.addBanknote(BanknoteDenomination.ONE);
        atm.addBanknote(BanknoteDenomination.ONE);
        Assert.assertEquals(2, atm.getBanknoteCountInCell(BanknoteDenomination.ONE));
        
        atm.addBanknote(BanknoteDenomination.FIVE);
        Assert.assertEquals(1, atm.getBanknoteCountInCell(BanknoteDenomination.FIVE));
        Assert.assertEquals(0, atm.getBanknoteCountInCell(BanknoteDenomination.TEN));
        Assert.assertEquals(0, atm.getBanknoteCountInCell(BanknoteDenomination.FIFTY));
    }

    @Test
    public void testWithdraw() throws ATMException {
        atm.addBanknote(BanknoteDenomination.ONE, 10);
        atm.addBanknote(BanknoteDenomination.FIVE, 10);
        atm.addBanknote(BanknoteDenomination.TEN, 10);
        atm.addBanknote(BanknoteDenomination.FIFTY, 10);

        atm.withdraw(15);
    }

    @Test(expected = ATMException.class)
    public void testWithdrawNegativeAmount() throws ATMException {
        atm.addBanknote(BanknoteDenomination.ONE, 10);

        atm.withdraw(-1);
    }

    @Test
    public void testWithdrawAll() {
        atm.addBanknote(BanknoteDenomination.ONE, 10);
        atm.addBanknote(BanknoteDenomination.FIVE, 10);
        atm.addBanknote(BanknoteDenomination.TEN, 10);
        atm.addBanknote(BanknoteDenomination.FIFTY, 10);

        Assert.assertEquals(10 + 10*5 + 10*10 + 10*50, atm.withdrawAll());

        for (BanknoteDenomination banknoteDenomination : BanknoteDenomination.values()) {
            Assert.assertEquals(0, atm.getBanknoteCountInCell(banknoteDenomination));
        }
    }
}
