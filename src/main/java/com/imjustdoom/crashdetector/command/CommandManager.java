package com.imjustdoom.crashdetector.command;

import com.imjustdoom.crashdetector.Main;
import com.imjustdoom.crashdetector.command.impl.CrashCmd;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        commands.add(new CrashCmd());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");
        User user = event.getAuthor();

        if (event.getMessage().getContentRaw().contains("<@990855990897418270>")) {
            event.getMessage().replyEmbeds(new EmbedBuilder()
                    .setTitle("Crash Detector")
                    .setDescription("""
                            Hello, I am the crash detector bot.
                            I can detect crashes in your code and send you a solution.
                            Use `!help` to see all my commands (Soon).""").build()).queue();
            return;
        }

        String strCommand = "";
        for (String prefix : Main.prefix) {
            if (!args[0].toLowerCase().startsWith(prefix.toLowerCase())) continue;
            strCommand = args[0].substring(prefix.length());
        }

        COMMAND:
        for (Command command : commands) {
            for (String name : command.getName()) {
                if (name.equalsIgnoreCase(strCommand)) {
                    if (command.getRoles().length > 0) for (String role : command.getRoles()) {
                        if (!event.getMember().getRoles().contains(event.getGuild().getRoleById(role))) {
                            event.getMessage().reply("You don't have permission to use this command.").queue();
                            continue COMMAND;
                        }
                    }
                    if (command.getUsers().length > 0) for (String user1 : command.getUsers()) {
                        if (!event.getAuthor().getId().equals(user1)) {
                            event.getMessage().reply("You don't have permission to use this command.").queue();
                            continue COMMAND;
                        }
                    }

                    command.execute(user, args, event.getMessage(), event.getTextChannel());
                    return;
                }
            }
        }
    }

    public List<Command> getCommands() {
        return commands;
    }
}
