package com.imjustdoom.minecrash.command.impl;

import com.imjustdoom.minecrash.command.Command;
import com.imjustdoom.minecrash.util.CrashUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class AboutCmd implements Command {

    @Override
    public String getName() {
        return "about";
    }

    @Override
    public String getDescription() {
        return "Displays information about the project";
    }

    @Override
    public OptionData[] getOptions() {
        return new OptionData[0];
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
        event.replyEmbeds(CrashUtil.getDefaultEmbed()
                .setTitle("MineCrash")
                .setDescription("For help run /help")
                .addField("About", "This bot aims to take in errors/crash reports and respond with a solution. " +
                        "It will improve overtime and get solutions for more crashes/errors.", false)
                .addField("How does it work?", "The bot works by checking for certain keywords and taking certain " +
                        "information from the error by using regex.", false)
                .addField("Source", "This is also open source and can be found on GitHub [here](https://github.com/JustDoom/MineCrash)", false)
                .build()).queue();
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
