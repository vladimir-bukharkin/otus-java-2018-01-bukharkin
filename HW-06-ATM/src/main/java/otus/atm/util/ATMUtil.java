package otus.atm.util;

import otus.atm.BanknoteDenomination;

import java.util.Map;

public class ATMUtil {

    public static int getAmountOfBanknotesSet(Map<BanknoteDenomination, Integer> banknotes) {
        int result = 0;
        for (Map.Entry<BanknoteDenomination, Integer> denominationCount : banknotes.entrySet()) {
            result += denominationCount.getValue() * denominationCount.getKey().getPar();
        }
        return result;
    }
}
