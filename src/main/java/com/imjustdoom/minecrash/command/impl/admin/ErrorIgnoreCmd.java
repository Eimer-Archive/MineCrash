package com.imjustdoom.minecrash.command.impl.admin;

import com.imjustdoom.minecrash.Main;
import com.imjustdoom.minecrash.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;
import java.util.List;

public class ErrorIgnoreCmd implements Command {

    @Override
    public String[] getName() {
        return new String[]{"errorignore", "ignore", "ignored", "errignore"};
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
        int id = Integer.parseInt(args[1]);

        if (!Main.getInstance().getDb().containsErrorForReview(String.valueOf(id))) {
            message.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle("Error not found")
                    .setDescription("Error not found in the database")
                    .build()).queue();
            return;
        }

        if (Main.getInstance().getDb().errorForReview(String.valueOf(id))) {
            message.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.CYAN)
                    .setTitle("Error already solved")
                    .setDescription("The error has already been solved")
                    .build()).queue();
            return;
        }

        Main.getInstance().getDb().solveErrorForReview(String.valueOf(id));

        message.replyEmbeds(new EmbedBuilder()
                .setTitle("Error has been removed")
                .setDescription("The error has been removed from the database without being marked as solved.")
                .addField("ID", String.valueOf(id), false)
                .setColor(Color.GREEN)
                .setFooter("Discord: discord.gg/wVCSqV7ptB")
                .build()).queue();
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
