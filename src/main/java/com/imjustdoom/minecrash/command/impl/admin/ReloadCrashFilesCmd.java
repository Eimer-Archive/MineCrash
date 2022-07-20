package com.imjustdoom.minecrash.command.impl.admin;

import com.imjustdoom.minecrash.Main;
import com.imjustdoom.minecrash.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ReloadCrashFilesCmd implements Command {

    @Override
    public String[] getName() {
        return new String[]{"rcf", "reload", "reloadfiles", "reloadcrashfiles"};
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
        return new String[]{Main.getInstance().getConfig().getOwner()};
    }

    @Override
    public void execute(User user, String[] args, Message message, TextChannel channel) {
        try {
            Main.getInstance().loadErrorFiles();
        } catch (IOException e) {
            message.replyEmbeds(new EmbedBuilder()
                    .setTitle("Error while loading crash files")
                    .setDescription("There was an error while loading the error/crash files.")
                    .setColor(Color.RED)
                    .setFooter("Discord: discord.gg/wVCSqV7ptB")
                    .build()).queue();

            e.printStackTrace();
            return;
        }

        message.replyEmbeds(new EmbedBuilder()
                .setTitle("Files loaded")
                .setDescription("The files were successfully loaded/reloaded")
                .setColor(Color.GREEN)
                .setFooter("Discord: discord.gg/wVCSqV7ptB")
                .build()).queue();
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
