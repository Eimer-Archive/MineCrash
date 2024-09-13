package com.imjustdoom.minecrash.command;

import com.imjustdoom.minecrash.command.impl.AboutCmd;
import com.imjustdoom.minecrash.command.impl.CrashCmd;
import com.imjustdoom.minecrash.command.impl.HelpCmd;
import com.imjustdoom.minecrash.command.impl.InfoCmd;
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

        for (Command command : commands) {
            if (command.getName().equalsIgnoreCase(event.getName())) {

                System.out.println("Command '" + event.getName() + "' was run by " + event.getUser().getName());
                command.execute(event);
                return;
            }
        }
    }

    public List<Command> getCommands() {
        return commands;
    }
}
