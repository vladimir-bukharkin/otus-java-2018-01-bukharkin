package otus.atm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otus.atm.exception.ATMException;

import java.util.HashMap;
import java.util.Map;

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

        atm.withdraw(0);

        Map<BanknoteDenomination, Integer> expected = new HashMap<BanknoteDenomination, Integer>() {{
            put(BanknoteDenomination.TEN, 1);
            put(BanknoteDenomination.ONE, 2);
        }};
        Assert.assertEquals(expected, atm.withdraw(12));

        Map<BanknoteDenomination, Integer> expectedRemain = new HashMap<BanknoteDenomination, Integer>() {{
            put(BanknoteDenomination.ONE, 8);
            put(BanknoteDenomination.FIVE, 10);
            put(BanknoteDenomination.TEN, 9);
            put(BanknoteDenomination.FIFTY, 10);
        }};
        Assert.assertEquals(expectedRemain, atm.getBanknoteCellBalance());
    }

    @Test(expected = ATMException.class)
    public void testWithdrawThrowNotEnoughMoneyException() throws ATMException {
        atm.addBanknote(BanknoteDenomination.FIVE, 10);
        atm.addBanknote(BanknoteDenomination.TEN, 10);
        atm.addBanknote(BanknoteDenomination.FIFTY, 10);
        atm.withdraw(16);
    }

    @Test(expected = ATMException.class)
    public void testWithdrawThrowNotEnoughMoneyException2() throws ATMException {
        atm.withdraw(1);
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

        Map<BanknoteDenomination, Integer> expected = new HashMap<BanknoteDenomination, Integer>() {{
            put(BanknoteDenomination.ONE, 10);
            put(BanknoteDenomination.FIVE, 10);
            put(BanknoteDenomination.TEN, 10);
            put(BanknoteDenomination.FIFTY, 10);
        }};

        Assert.assertEquals(expected, atm.withdrawAll());

        for (BanknoteDenomination banknoteDenomination : BanknoteDenomination.values()) {
            Assert.assertEquals(0, atm.getBanknoteCountInCell(banknoteDenomination));
        }
    }
}
