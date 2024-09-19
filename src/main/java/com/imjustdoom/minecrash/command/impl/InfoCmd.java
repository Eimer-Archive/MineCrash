package com.imjustdoom.minecrash.command.impl;

import com.imjustdoom.minecrash.MineCrash;
import com.imjustdoom.minecrash.command.Command;
import com.imjustdoom.minecrash.util.CrashUtil;
import com.imjustdoom.minecrash.util.NetworkUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.util.List;

public class InfoCmd implements Command {

    @Override
    public String getName() {
        return "statistics";
    }

    @Override
    public String getDescription() {
        return "Displays statistics for the bot/project";
    }

    @Override
    public OptionData[] getOptions() {
        return new OptionData[0];
    }

    @Override
    public InteractionContextType[] getContexts() {
        return new InteractionContextType[]{InteractionContextType.GUILD, InteractionContextType.PRIVATE_CHANNEL, InteractionContextType.BOT_DM};
    }

    @Override
    public IntegrationType[] getTypes() {
        return new IntegrationType[]{IntegrationType.GUILD_INSTALL, IntegrationType.USER_INSTALL};
    }

    @Override
    public String[] getRoles() {
        return new String[0];
    }

    @Override
    public String[] getUsers() {
        return new String[0];
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        event.deferReply().queue();

        long guilds = MineCrash.get().getJda().getGuildCache().size();

        try {
            int value = NetworkUtil.getStatistics();
            event.getHook().sendMessageEmbeds(CrashUtil.getDefaultEmbed()
                    .setTitle("MineCrash Info")
                    .addField("Servers", String.valueOf(guilds), false)
                    .addField("Solved errors", String.valueOf(value), false)
                    .build()).queue();
        } catch (IOException exception) {
            event.getHook().sendMessageEmbeds(CrashUtil.getErrorEmbed().setDescription(exception.getMessage()).build()).queue();
        }
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
