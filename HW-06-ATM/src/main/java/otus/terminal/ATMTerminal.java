package otus.terminal;

import otus.atm.ATM;
import otus.terminal.command.ATMCommand;
import otus.terminal.command.PutBanknoteCommand;
import otus.terminal.command.WithdrawAllCommand;
import otus.terminal.command.WithdrawCommand;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATMTerminal implements AutoCloseable {

    private final Scanner scanner;
    private final Map<String, ATMCommand> availableCommands;
    private final ATM atm;

    public ATMTerminal(InputStream inputStream, OutputStream outputStream, ATM atm) {
        scanner = new Scanner(inputStream);
        this.atm = atm;
        availableCommands = ATMCommand.getAvailableCommands();
    }

    public void run(ATMTerminal atmTerminal) {

        Scanner scanIn = new Scanner(System.in);

        while (true) {
            String inputString = scanIn.nextLine();
            if (availableCommands.containsKey(inputString)) {
                availableCommands.get(inputString).execute(this, atm);
            }
        }
    }

    @Override
    public void close() {
        scanner.close();

    }
}
