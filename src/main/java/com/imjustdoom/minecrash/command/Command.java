package com.imjustdoom.minecrash.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public interface Command {
    String getName();

    String getDescription();

    OptionData[] getOptions();

    InteractionContextType[] getContexts();

    IntegrationType[] getTypes();

    String[] getRoles();

    String[] getUsers();

    void execute(SlashCommandInteractionEvent event);

    List<Command> getCommands();
}
