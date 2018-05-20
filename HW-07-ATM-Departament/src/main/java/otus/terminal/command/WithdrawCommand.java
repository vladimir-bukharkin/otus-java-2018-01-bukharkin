package otus.terminal.command;

import otus.atm.ATMImpl;
import otus.atm.BanknoteDenomination;
import otus.atm.exception.ATMException;
import otus.terminal.ATMTerminal;

import java.io.IOException;
import java.util.Map;

public class WithdrawCommand implements ATMCommand {

    @Override
    public void execute(ATMTerminal terminal, ATMImpl atm) throws IOException {
        terminal.printlnToTerminal("Введите сумму: ");

        String in = terminal.readLineFromTerminal();
        try {
            Integer amount = Integer.valueOf(in);
            Map<BanknoteDenomination, Integer> res = atm.withdraw(amount);
            terminal.printlnToTerminal("Ок... выданы банкноты:\n" +
                    res +
                    "\nTotal balance: " + atm.getTotalBalance() +
                    "\nTotal banknote Balance: " + atm.toString());
        } catch (NumberFormatException e) {
            terminal.printlnToTerminal("Error: incorrect number format");
        } catch (ATMException e) {
            terminal.printlnToTerminal("Error: " + e.getMessage());
        }
    }
}
