package otus.terminal.command;

import java.util.Arrays;

public enum CommandsEnum {
    COMMAND_WITHDRAW("withdraw", new WithdrawCommand()),
    COMMAND_WITHDRAW_ALL("withdraw_all", new WithdrawAllCommand()),
    COMMAND_PUT_BANKNOTE("put_banknote", new PutBanknoteCommand());

    private final String commandName;
    private final ATMCommand atmCommand;

    CommandsEnum(String commandName, ATMCommand atmCommand) {
        this.commandName = commandName;
        this.atmCommand = atmCommand;
    }

    public static ATMCommand getCommandByName(String name) {
        return Arrays.stream(CommandsEnum.values()).filter(c -> c.getCommandName().equals(name))
                .map(CommandsEnum::getAtmCommand)
                .findAny()
                .orElse(null);
    }

    public String getCommandName() {
        return commandName;
    }

    public ATMCommand getAtmCommand() {
        return atmCommand;
    }
}
