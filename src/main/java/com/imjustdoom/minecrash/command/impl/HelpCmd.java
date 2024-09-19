package com.imjustdoom.minecrash.command.impl;

import com.imjustdoom.minecrash.command.Command;
import com.imjustdoom.minecrash.util.CrashUtil;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class HelpCmd implements Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Displays help regarding the bot";
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
        return new String[]{};
    }

    @Override
    public String[] getUsers() {
        return new String[0];
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        event.replyEmbeds(CrashUtil.getDefaultEmbed()
                .setTitle("Help")
//                .setDescription("Command prefixes are ! or c! (Currently replaced by slash commands)")
                .addField("/crash or /error", "To get a crash/error solved by the bot run the command /error or " +
                        "/crash and either paste in the error or upload it in a txt file. If you upload a file add \"file\" after !error/!crash.", false)
                .addField("/statistics", "Get some stats about the discord bot!", false)
                .addField("/about", "Displays information about the project", false)
                .build()).queue();
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
