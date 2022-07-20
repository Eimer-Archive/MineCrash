package com.imjustdoom.minecrash.command.impl;

import com.imjustdoom.minecrash.Main;
import com.imjustdoom.minecrash.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.List;

public class InfoCmd implements Command {

    @Override
    public String[] getName() {
        return new String[]{"information", "i", "info", "stats", "statistics"};
    }

    @Override
    public String getDescription() {
        return null;
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
    public void execute(User user, String[] args, Message message, TextChannel channel) {

        int guilds = Main.getInstance().getJda().getGuilds().size();
        int solvedErrors = Main.getInstance().getBotStats().getSolvedErrors();

        message.replyEmbeds(new EmbedBuilder()
                .setTitle("MineCrash Info")
                .setDescription("")
                .addField("Servers", String.valueOf(guilds), false)
                .addField("Solved errors", String.valueOf(solvedErrors), false)
                .setFooter("Discord: " + Main.getInstance().getConfig().getServer())
                .setColor(Color.CYAN)
                .build()).queue();
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
