package com.imjustdoom.minecrash.command.impl;

import com.imjustdoom.minecrash.MineCrash;
import com.imjustdoom.minecrash.command.Command;
import com.imjustdoom.minecrash.util.CrashUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

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

        event.replyEmbeds(CrashUtil.getDefaultEmbed()
                .setTitle("MineCrash Info")
                .setDescription("")
                .addField("Servers", String.valueOf(guilds), false)
                .addField("Solved errors", "Soon:tm:", false)
                .build()).queue();
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
