package com.imjustdoom.minecrash.command.impl;

import com.imjustdoom.minecrash.Main;
import com.imjustdoom.minecrash.command.Command;
import com.imjustdoom.minecrash.crash.Crash;
import com.imjustdoom.minecrash.util.CrashUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrashCmd implements Command {

    @Override
    public String[] getName() {
        return new String[]{"crash", "error"};
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

        if (args.length == 1) {
            message.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle("Missing error")
                    .setDescription("Please specify an error to check.")
                    .build()).queue();
            return;
        }

        String text = message.getContentRaw().replace(args[0] + " ", "");

        if (message.getAttachments().size() > 0) {
            try {
                URL url = new URL(message.getAttachments().get(0).getUrl());
                BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String i;
                while ((i = read.readLine()) != null) {
                    stringBuilder.append(i).append(System.lineSeparator());
                }
                read.close();

                text = stringBuilder.toString();
            } catch (Exception e) {
                message.replyEmbeds(new EmbedBuilder()
                        .setTitle("Invalid File")
                        .setDescription("Please attach a valid file.")
                        .setColor(Color.RED)
                        .build()).queue();
                return;
            }
        }

        CRASH:
        for (Crash crash : Main.crashList) {

            // Check if there is a match in the crash list
            for (String match : crash.matches) {
                if (!text.contains(match)) {
                    continue CRASH;
                }
            }

            String solution = crash.solution;
            for (String arg : crash.arguments.keySet()) {
                Pattern pattern = Pattern.compile(crash.arguments.get(arg));
                Matcher matcher = pattern.matcher(text);

                if (matcher.find()) {
                    solution = solution.replaceAll("%" + arg, matcher.group(1));
                }
            }

            // Send the crash solution
            message.replyEmbeds(CrashUtil.getCrashEmbed(solution, crash.error).build()).queue();
            return;
        }

        // Add the error to the database if it's not in the database
        String id = Main.db.addErrorForReview(user.getId(), text);

        message.replyEmbeds(new EmbedBuilder()
                .setTitle("This error has not been solved yet :(")
                .setDescription("This crash is not in the database. It will be submitted to the database to be solved. " +
                        "If you have a solution please go [here](https://github.com/JustDoom/MineCrash/issues) and submit an issue with this " + id + " in the title.")
                .setColor(Color.RED)
                .build())
                .queue();

        // Send the error to the log channel for the solution to be added
        MessageChannel logChannel = Main.jda.getTextChannelById(Main.config.channelId);
        logChannel.sendMessageEmbeds(
                new EmbedBuilder()
                        .setTitle("Unknown Error...")
                        .setDescription("Error from " + user.getName() + "#" + user.getDiscriminator() + " (" + user.getId() + ")")
                        .setFooter("Error ID: " + id)
                        .setColor(Color.ORANGE)
                        .build())
                .addFile(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), id + "-error.txt")
                .queue();
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
