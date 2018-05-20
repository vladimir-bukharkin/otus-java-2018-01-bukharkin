package otus.terminal.command;

import otus.atm.ATMImpl;
import otus.atm.BanknoteDenomination;
import otus.terminal.ATMTerminal;

import java.io.IOException;
import java.util.Map;

public class WithdrawAllCommand implements ATMCommand {

    @Override
    public void execute(ATMTerminal terminal, ATMImpl atm) throws IOException {
        Map<BanknoteDenomination, Integer> res = atm.withdrawAll();
        terminal.printlnToTerminal("Ок... выданы банкноты:\n" +
                res +
                "\nTotal balance: " + atm.getTotalBalance() +
                "\nTotal banknote Balance: " + atm.toString());
    }
}
