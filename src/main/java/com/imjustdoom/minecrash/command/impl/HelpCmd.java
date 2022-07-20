package com.imjustdoom.minecrash.command.impl;

import com.imjustdoom.minecrash.Main;
import com.imjustdoom.minecrash.command.Command;
import com.imjustdoom.minecrash.config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.List;

public class HelpCmd implements Command {

    @Override
    public String[] getName() {
        return new String[]{"help", "h"};
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

        message.replyEmbeds(new EmbedBuilder()
                .setTitle("Help")
                .setDescription("Command prefixes are ! or c!")
                .addField("!error/!crash", "To get a crash/error solved by the bot run the command !error or " +
                        "!crash and either paste in the error or upload it in a txt file.", false)
                .setFooter("Discord: " + Main.getInstance().getConfig().getServer())
                .setColor(Color.CYAN)
                .build()).queue();
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
