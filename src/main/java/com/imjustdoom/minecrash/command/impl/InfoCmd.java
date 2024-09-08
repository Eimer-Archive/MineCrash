package com.imjustdoom.minecrash.command.impl;

import com.imjustdoom.minecrash.MineCrash;
import com.imjustdoom.minecrash.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
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
    public String[] getRoles() {
        return new String[]{};
    }

    @Override
    public String[] getUsers() {
        return new String[]{};
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        int guilds = MineCrash.get().getJda().getGuilds().size();

        event.replyEmbeds(new EmbedBuilder()
                .setTitle("MineCrash Info")
                .setDescription("")
                .addField("Servers", String.valueOf(guilds), false)
                .addField("Solved errors", "Soon:tm:", false)
                .setFooter("Discord: " + "Soon:tm:")
                .setColor(Color.CYAN)
                .build()).queue();
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
