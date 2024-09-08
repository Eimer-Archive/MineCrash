package com.imjustdoom.minecrash.command.impl;

import com.imjustdoom.minecrash.command.Command;
import com.imjustdoom.minecrash.util.CrashUtil;
import com.imjustdoom.minecrash.util.NetworkUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CrashCmd implements Command {

    @Override
    public String getName() {
        return "error";
    }

    @Override
    public String getDescription() {
        return "Tries to solve the error/crash";
    }

    @Override
    public OptionData[] getOptions() {
        return new OptionData[]{new OptionData(OptionType.ATTACHMENT, "error", "The file containing the error", false), new OptionData(OptionType.STRING, "errortext", "Text containing a short error", false)};
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

        OptionMapping fileOption = event.getOption("error");
        OptionMapping textOption = event.getOption("errortext");

        event.deferReply().queue();

        if (fileOption != null) {
            Message.Attachment errorFile = fileOption.getAsAttachment();
            // Convert to MiB
            if ((errorFile.getSize() / 1024f / 1024f) > 24f) {
                event.reply("File is too large, currently only 24MiB and below are supported").queue();
                return;
            }

            if (errorFile.getContentType() == null || !errorFile.getContentType().contains("text/plain")) {
                event.reply("Failed to read file. Please upload a .txt file containing your error").queue();
                return;
            }

            if (errorFile.getSize() > 1000f * 1000f) {
                System.out.println("File size is " + (errorFile.getSize() / 1000f / 1000f) + "mb");
            } else if (errorFile.getSize() > 1000f) {
                System.out.println("File size is " + (errorFile.getSize() / 1000f) + "kb");
            } else {
                System.out.println("File size is " + errorFile.getSize() + " bytes");
            }

            CompletableFuture<InputStream> futureFile = errorFile.getProxy().download();
            futureFile.whenComplete((inputStream, e) -> {
                String text = "";
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }
                    inputStream.close();
                    text = out.toString();
                } catch (IOException exception) {
                    event.getHook().sendMessage("Error").queue();
                    return;
                }

                checkError(event, text);
            });

            return;
        }

        if (textOption != null) {
            String error = textOption.getAsString();

            if (textOption.getAsString().isBlank()) {
                event.reply("Error is blank, please try again with an error specified.").queue();
                return;
            }

            checkError(event, error);

            return;
        }

        event.getHook().sendMessageEmbeds(CrashUtil.getDefaultEmbed().setTitle("Please specify either an error file or an error through text using the \"errortext\" option.").build()).queue();
    }

    private void checkError(SlashCommandInteractionEvent event, String error) {
        try {
            long start = System.currentTimeMillis();
            String[] response = NetworkUtil.sendErrorForCheck(error);

            if (response.length == 1) {
                event.getHook().sendMessageEmbeds(CrashUtil.getDefaultEmbed()
                        .setTitle("Unknown Crash/Error")
                        .setDescription(response[0])
                        .setFooter("Took " + (System.currentTimeMillis() - start) + "ms")
                        .setColor(Color.ORANGE)
                        .build()).queue();
            } else {
                event.getHook().sendMessageEmbeds(CrashUtil.getDefaultEmbed()
                        .setTitle(response[0])
                        .setDescription(response[1])
                        .setFooter("Took " + (System.currentTimeMillis() - start) + "ms")
                        .setColor(Color.GREEN)
                        .build()).queue();
            }
        } catch (IOException ex) {
            event.getHook().sendMessageEmbeds(CrashUtil.getErrorEmbed().build()).queue();
            ex.printStackTrace();
        }
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
