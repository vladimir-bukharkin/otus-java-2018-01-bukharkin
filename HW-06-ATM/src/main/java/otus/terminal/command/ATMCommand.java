package otus.terminal.command;

import otus.atm.ATM;
import otus.terminal.ATMTerminal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ATMCommand {

    String COMMAND_WITHDRAW = "withdraw";
    String COMMAND_WITHDRAW_ALL = "withdraw_all";
    String COMMAND_PUT_BANKNOTE = "put_banknote";

    public static Map<String, ATMCommand> getAvailableCommands() {

        return new HashMap<String, ATMCommand>() {{
            put(COMMAND_WITHDRAW, new WithdrawCommand());
            put(COMMAND_WITHDRAW_ALL, new WithdrawAllCommand());
            put(COMMAND_PUT_BANKNOTE, new PutBanknoteCommand());
        }};
    }

    public static List<String> getAvailableCommandNames() {
        return new ArrayList<String>() {{
            add(COMMAND_WITHDRAW);
            add(COMMAND_WITHDRAW_ALL);
            add(COMMAND_PUT_BANKNOTE);
        }};
    }

    public void execute(ATMTerminal terminal, ATM atm);
}
