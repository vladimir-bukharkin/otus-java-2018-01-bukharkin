package otus.terminal;

import otus.atm.ATMType1;
import otus.terminal.command.ATMCommand;
import otus.terminal.command.CommandsEnum;

import java.io.*;
import java.util.Scanner;

public class ATMTerminal implements AutoCloseable{
    private final static String COMMAND_CLOSE = "close";

    private final ATMType1 atm;

    private final Scanner scanner;
    private final Writer writer;

    public ATMTerminal(ATMType1 atm, OutputStream out, InputStream in) {
        scanner = new Scanner(in);
        writer = new OutputStreamWriter(out);
        this.atm = atm;
    }

    public void run() throws IOException {
        printHelloMessage();
        while (true) {
            String inputString = scanner.nextLine();
            if (inputString.equals(COMMAND_CLOSE)) {
                break;
            } else {
                ATMCommand command = CommandsEnum.getCommandByName(inputString);
                if (command == null) {
                    printlnToTerminal("Invalid command");
                } else {
                    command.execute(this, atm);
                }
            }
        }
    }

    private void printHelloMessage() throws IOException {
        writer.append("Введите одну из следующих доступных комманд:\n   ")
                .append(CommandsEnum.COMMAND_PUT_BANKNOTE.getCommandName()).append(" - Чтобы положить наличные на счет\n   ")
                .append(CommandsEnum.COMMAND_WITHDRAW.getCommandName()).append(" - Для снятия наличных\n   ")
                .append(CommandsEnum.COMMAND_WITHDRAW_ALL.getCommandName()).append(" - Для снятия всех наличных\n   ")
                .append(COMMAND_CLOSE).append(" - Для закрытия терминала\n\n")
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
