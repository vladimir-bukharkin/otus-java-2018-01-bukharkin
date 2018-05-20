package otus.atm;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otus.atm.exception.ATMException;

import java.util.HashMap;
import java.util.Map;

public class ATMImplTest {

    private ATMImpl atm;

    @Before
    public void setUp() {
        atm = new ATMImpl();
    }

    @Test
    public void testPutBanknote() {
        atm.putBanknote(BanknoteDenomination.ONE);
        atm.putBanknote(BanknoteDenomination.ONE);
        Assert.assertEquals(2, atm.getBanknoteCountInCell(BanknoteDenomination.ONE));
        
        atm.putBanknote(BanknoteDenomination.FIVE);
        Assert.assertEquals(1, atm.getBanknoteCountInCell(BanknoteDenomination.FIVE));
        Assert.assertEquals(0, atm.getBanknoteCountInCell(BanknoteDenomination.TEN));
        Assert.assertEquals(0, atm.getBanknoteCountInCell(BanknoteDenomination.FIFTY));
    }

    @Test
    public void testWithdraw() throws ATMException {
        atm.putBanknote(BanknoteDenomination.ONE, 10);
        atm.putBanknote(BanknoteDenomination.FIVE, 10);
        atm.putBanknote(BanknoteDenomination.TEN, 10);
        atm.putBanknote(BanknoteDenomination.FIFTY, 10);

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
        Assert.assertEquals(expectedRemain, atm.getBanknoteCellsBalance());
    }

    @Test(expected = ATMException.class)
    public void testWithdrawThrowNotEnoughMoneyException() throws ATMException {
        atm.putBanknote(BanknoteDenomination.FIVE, 10);
        atm.putBanknote(BanknoteDenomination.TEN, 10);
        atm.putBanknote(BanknoteDenomination.FIFTY, 10);
        atm.withdraw(16);
    }

    @Test(expected = ATMException.class)
    public void testWithdrawThrowNotEnoughMoneyException2() throws ATMException {
        atm.withdraw(1);
    }


    @Test(expected = ATMException.class)
    public void testWithdrawNegativeAmount() throws ATMException {
        atm.putBanknote(BanknoteDenomination.ONE, 10);

        atm.withdraw(-1);
    }

    @Test
    public void testWithdrawAll() {
        atm.putBanknote(BanknoteDenomination.ONE, 10);
        atm.putBanknote(BanknoteDenomination.FIVE, 10);
        atm.putBanknote(BanknoteDenomination.TEN, 10);
        atm.putBanknote(BanknoteDenomination.FIFTY, 10);

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

    @Test
    public void testRestoreATMInitState() {
        atm.putBanknote(BanknoteDenomination.ONE, 10);
        atm.putBanknote(BanknoteDenomination.FIVE, 10);

        Map<BanknoteDenomination, Integer> expected = new HashMap<BanknoteDenomination, Integer>() {{
            put(BanknoteDenomination.ONE, 0);
            put(BanknoteDenomination.FIVE, 0);
            put(BanknoteDenomination.TEN, 0);
            put(BanknoteDenomination.FIFTY, 0);
        }};

        atm.restoreInitialATMState();
        Assert.assertEquals(expected, atm.getBanknoteCellsBalance());
    }
}
