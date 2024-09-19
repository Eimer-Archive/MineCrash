package com.imjustdoom.minecrash;

import com.imjustdoom.minecrash.command.Command;
import com.imjustdoom.minecrash.command.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class MineCrash {

    private final String[] prefix = new String[]{"!", "D!", "C!"};

    private final JDA jda;

    public MineCrash(String token) throws InterruptedException {
        INSTANCE = this;

        CommandManager commandManager = new CommandManager();

        this.jda = JDABuilder.createLight(token).addEventListeners(commandManager).build();
        this.jda.awaitReady();
        this.jda.getPresence().setActivity(Activity.customStatus("MineCrash is back!"));

        List<CommandData> commandDataList = new ArrayList<>();
        for (Command command : commandManager.getCommands()) {
            commandDataList.add(
                    Commands.slash(command.getName(), command.getDescription())
                            .addOptions(command.getOptions())
                            .setContexts(command.getContexts())
                            .setIntegrationTypes(command.getTypes()));
        }
        this.jda.updateCommands().addCommands(commandDataList).queue();
    }

    public String[] getPrefix() {
        return this.prefix;
    }

    public JDA getJda() {
        return this.jda;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.err.println("The token for the bot needs to be specified as the first argument");
            return;
        }
        new MineCrash(args[0]);
    }

    private static MineCrash INSTANCE;
    public static MineCrash get() {
        return INSTANCE;
    }
}