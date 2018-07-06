package otus.terminal.command;

import otus.atm.ATMType1;
import otus.terminal.ATMTerminal;

import java.io.IOException;

@FunctionalInterface
public interface ATMCommand {
    void execute(ATMTerminal terminal, ATMType1 atm) throws IOException;
}
