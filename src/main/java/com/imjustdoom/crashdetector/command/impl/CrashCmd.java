package com.imjustdoom.crashdetector.command.impl;

import com.imjustdoom.crashdetector.Main;
import com.imjustdoom.crashdetector.command.Command;
import com.imjustdoom.crashdetector.crash.Crash;
import com.imjustdoom.crashdetector.util.CrashUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
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

        String text = message.getContentRaw();

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
                message.reply("Please attach a valid file.").queue();
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
        }
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
