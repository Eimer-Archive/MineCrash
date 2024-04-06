package com.imjustdoom.minecrash.command.impl;

import com.imjustdoom.minecrash.Main;
import com.imjustdoom.minecrash.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;
import java.util.List;

public class AboutCmd implements Command {

    @Override
    public String[] getName() {
        return new String[]{"about", "ab"};
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
                .setTitle("MineCrash")
                .setDescription("For help run c!help")
                .addField("About", "This bot aims to take in errors/crash reports and respond with a solution. " +
                        "It will improve overtime and get solutions for more crashes/errors.", false)
                .addField("How does it work?", "The bot works by checking for certain keywords and taking certain " +
                        "information from the error by using regex.", false)
                .addField("Source", "This is also open source and can be found on GitHub [here](https://github.com/JustDoom/MineCrash)", false)
                .setFooter("Discord: " + Main.getInstance().getConfig().getServer())
                .setColor(Color.CYAN)
                .build()).queue();
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
