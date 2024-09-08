package com.imjustdoom.minecrash;

import com.imjustdoom.minecrash.command.Command;
import com.imjustdoom.minecrash.command.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MineCrash {

    private final String[] prefix = new String[]{"!", "D!", "C!"};

    private final JDA jda;

    public MineCrash(String token) throws IOException, LoginException, InterruptedException {
        INSTANCE = this;

        CommandManager commandManager = new CommandManager();

        JDABuilder builder = JDABuilder.createDefault(token)
                .addEventListeners(commandManager);

        this.jda = builder.build();
        this.jda.awaitReady();
        this.jda.getPresence().setActivity(Activity.customStatus("Might be under a recode " + Emoji.fromUnicode("\uD83D\uDC40").getFormatted()));

        List<CommandData> commandDataList = new ArrayList<>();
        for (Command command : commandManager.getCommands()) {
            commandDataList.add(Commands.slash(command.getName(), command.getDescription()).addOptions(command.getOptions()));
        }
        this.jda.updateCommands().addCommands(commandDataList).queue();
    }

    public String[] getPrefix() {
        return this.prefix;
    }

    public JDA getJda() {
        return this.jda;
    }

    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
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