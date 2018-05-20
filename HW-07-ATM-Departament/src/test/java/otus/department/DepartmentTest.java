package otus.department;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import otus.atm.ATM;
import otus.atm.ATMImpl;
import otus.atm.BanknoteDenomination;
import otus.atm.departament.Department;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DepartmentTest {

    private Department department;
    private ATM atm1;
    private ATM atm2;

    @Before
    public void setUp() {
        atm1 = new ATMImpl(new HashMap<BanknoteDenomination, Integer>() {{
            put(BanknoteDenomination.ONE, 10);
            put(BanknoteDenomination.TEN, 10);
        }});
        atm2 = new ATMImpl(new HashMap<BanknoteDenomination, Integer>() {{
            put(BanknoteDenomination.FIFTY, 1);
            put(BanknoteDenomination.FIVE, 5);
        }});

        department = new Department();
        department.addAtm(atm1);
        department.addAtm(atm2);
    }

    @Test
    public void testAddATM() {
        Assert.assertEquals(Arrays.asList(atm1, atm2), department.getAtms());
    }

    @Test
    public void testGetBalance() {
        Assert.assertEquals(10 + 10*10 + 50 + 5*5, department.getBalance());
    }

    @Test
    public void testRestoreAllATMInitState() {
        Map<BanknoteDenomination, Integer> expectedAtm1 = new HashMap<>(atm1.getBanknoteCellsBalance());
        Map<BanknoteDenomination, Integer> expectedAtm2 = new HashMap<>(atm2.getBanknoteCellsBalance());

        atm1.putBanknote(BanknoteDenomination.ONE);
        atm1.putBanknote(BanknoteDenomination.ONE);
        atm1.putBanknote(BanknoteDenomination.ONE);

        atm2.withdrawAll();
        Assert.assertNotEquals(expectedAtm1, atm1.getBanknoteCellsBalance());
        Assert.assertNotEquals(expectedAtm2, atm2.getBanknoteCellsBalance());

        department.restoreAllATMInitState();
        Assert.assertEquals(expectedAtm1, atm1.getBanknoteCellsBalance());
        Assert.assertEquals(expectedAtm2, atm2.getBanknoteCellsBalance());
    }
}
