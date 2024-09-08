package com.imjustdoom.minecrash.command;

import com.imjustdoom.minecrash.command.impl.AboutCmd;
import com.imjustdoom.minecrash.command.impl.CrashCmd;
import com.imjustdoom.minecrash.command.impl.HelpCmd;
import com.imjustdoom.minecrash.command.impl.InfoCmd;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        this.commands.add(new AboutCmd());
        this.commands.add(new CrashCmd());
        this.commands.add(new HelpCmd());
        this.commands.add(new InfoCmd());
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        User user = event.getUser();

        // Respond with about command when panged
//        if (event.getMessage().getContentRaw().contains("<@" + event.getJDA().getSelfUser().getIdLong() + ">")) {
//            new AboutCmd().execute(user, args, event.getMessage(), event.getChannel().asTextChannel());
//            return;
//        }

        // Run prefix check
//        String strCommand = "";
//        for (String prefix : MineCrash.get().getPrefix()) {
//            if (!args[0].toLowerCase().startsWith(prefix.toLowerCase())) continue;
//            strCommand = args[0].substring(prefix.length());
//        }

        COMMAND:
        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(event.getName())) {
//                if (command.getRoles().length > 0) {
//                    for (String role : command.getRoles()) {
//                        if (!event.getMember().getRoles().contains(event.getGuild().getRoleById(role))) {
//                            event.reply("You don't have permission to use this command.").queue();
//                            continue COMMAND;
//                        }
//                    }
//                }
//                if (command.getUsers().length > 0) {
//                    for (String user1 : command.getUsers()) {
//                        if (!user.getId().equals(user1)) {
//                            event.reply("You don't have permission to use this command.").queue();
//                            continue COMMAND;
//                        }
//                    }
//                }

                command.execute(event);
                return;
            }
        }
    }

    public List<Command> getCommands() {
        return commands;
    }
}
