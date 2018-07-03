package otus.terminal;

import otus.atm.ATMType1;
import otus.terminal.command.ATMCommand;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class ATMTerminal implements AutoCloseable{

    private final Map<String, ATMCommand> availableCommands;
    private final ATMType1 atm;

    private final Scanner scanner;
    private final Writer writer;


    public ATMTerminal(ATMType1 atm, OutputStream out, InputStream in) {
        scanner = new Scanner(in);
        writer = new OutputStreamWriter(out);
        availableCommands = ATMCommand.getAvailableCommands();
        this.atm = atm;
    }

    public void run() throws IOException {
        printHelloMessage();
        while (true) {
            String inputString = scanner.nextLine();
            if (availableCommands.containsKey(inputString)) {
                availableCommands.get(inputString).execute(this, atm);
            } else if (inputString.equals(ATMCommand.COMMAND_CLOSE)) {
                break;
            }
        }
    }

    private void printHelloMessage() throws IOException {
        writer.append("Введите одну из следующих доступных комманд:\n   ")
                .append(ATMCommand.COMMAND_PUT_BANKNOTE).append(" - Чтобы положить наличные на счет\n   ")
                .append(ATMCommand.COMMAND_WITHDRAW).append(" - Для снятия наличных\n   ")
                .append(ATMCommand.COMMAND_WITHDRAW_ALL).append(" - Для снятия всех наличных\n   ")
                .append(ATMCommand.COMMAND_CLOSE).append(" - Для закрытия терминала\n\n")
                .append(" > ")
                .flush();
    }

    public void printlnToTerminal(String s) throws IOException {
        writer.append(s).append("\n\n").append(" > ").flush();
    }

    public String readLineFromTerminal() {
        return scanner.nextLine();
    }

    @Override
    public void close() throws Exception {
        scanner.close();
        writer.close();
    }
}
