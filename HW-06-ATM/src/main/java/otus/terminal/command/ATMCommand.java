package otus.terminal.command;

import otus.atm.ATM;
import otus.terminal.ATMTerminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ATMCommand {

    String COMMAND_WITHDRAW = "withdraw";
    String COMMAND_WITHDRAW_ALL = "withdraw_all";
    String COMMAND_PUT_BANKNOTE = "put_banknote";
    String COMMAND_CLOSE = "close";

    static Map<String, ATMCommand> getAvailableCommands() {
        return new HashMap<String, ATMCommand>() {{
            put(COMMAND_WITHDRAW, new WithdrawCommand());
            put(COMMAND_WITHDRAW_ALL, new WithdrawAllCommand());
            put(COMMAND_PUT_BANKNOTE, new PutBanknoteCommand());
        }};
    }

    void execute(ATMTerminal terminal, ATM atm) throws IOException;
}
