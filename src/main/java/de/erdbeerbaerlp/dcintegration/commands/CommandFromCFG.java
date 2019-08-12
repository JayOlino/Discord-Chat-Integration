package de.erdbeerbaerlp.dcintegration.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class CommandFromCFG extends DiscordCommand {
    private final String cmd, desc, mcCmd, argText;
    private final boolean admin;
    private final String[] aliases;
    private final boolean useArgs;

    public CommandFromCFG(String cmd, String description, String mcCommand, boolean adminOnly, String[] aliases, boolean useArgs, String argText) {
        this.desc = description;
        this.cmd = cmd;
        this.admin = adminOnly;
        this.mcCmd = mcCommand;
        this.aliases = aliases;
        this.useArgs = useArgs;
        this.argText = argText;
    }

    /**
     * Sets the name of the command
     */
    @Override
    public String getName() {
        return cmd;
    }

    @Override
    public boolean adminOnly() {
        return admin;
    }

    /**
     * Sets the aliases of the command
     */
    @Override
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Sets the description for the help command
     */
    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public String getCommandUsage() {
        if (useArgs)
            return super.getCommandUsage() + " " + argText;
        else
            return super.getCommandUsage();
    }

    /**
     * Method called when executing this command
     *
     * @param args   arguments passed by the player
     * @param cmdMsg the {@link MessageReceivedEvent} of the message
     */
    @Override
    public void execute(String[] args, MessageReceivedEvent cmdMsg) {
        String cmd = mcCmd;
        int argsCount = useArgs ? args.length : 0;
        if (argsCount > 0) {
            for (int i = 0; i < argsCount; i++) {
                cmd = cmd + " " + args[i];
            }
        }

        ServerLifecycleHooks.getCurrentServer().getCommandManager().handleCommand(
                new DCCommandSender(cmdMsg.getAuthor(), this).getCommandSource(),
                cmd.trim()
        );
    }

}