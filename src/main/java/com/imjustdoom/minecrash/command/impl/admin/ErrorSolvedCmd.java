package com.imjustdoom.minecrash.command.impl.admin;

import com.imjustdoom.minecrash.Main;
import com.imjustdoom.minecrash.command.Command;
import com.imjustdoom.minecrash.config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ErrorSolvedCmd implements Command {

    public static Config config;

    @Override
    public String[] getName() {
        return new String[]{"errorsolved", "solved", "solve", "errsolved"};
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
        return new String[]{config.owner};
    }

    @Override
    public void execute(User user, String[] args, Message message, TextChannel channel) {
        int id = Integer.parseInt(args[1]);

        if (!Main.db.containsErrorForReview(String.valueOf(id))) {
            message.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle("Error not found")
                    .setDescription("Error not found in the database")
                    .build()).queue();
            return;
        }

        String text = Main.db.getErrorFromReview(id);
        String userId = Main.db.getUserIdFromReview(id);

        Main.db.removeErrorForReview(String.valueOf(id));

        message.replyEmbeds(new EmbedBuilder()
                .setTitle("Error has been solved!")
                .setDescription("The error has been solved and a message has been sent to the user")
                .addField("ID", String.valueOf(id), false)
                .setColor(Color.GREEN)
                .setFooter("Discord: discord.gg/wVCSqV7ptB")
                .build()).queue();

        Main.jda.retrieveUserById(userId)
                .map(User::openPrivateChannel)
                .queue(name -> {
                    name.queue((channel1) ->
                            channel1.sendMessageEmbeds(new EmbedBuilder()
                                            .setTitle("Error solved")
                                            .setDescription("The error has been solved!")
                                            .addField("ID", String.valueOf(id), false)
                                            .setColor(Color.GREEN)
                                            .setFooter("Discord: " + config.server)
                                            .build())
                                    .addFile(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)), "your-error.txt")
                                    .queue());
                });
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
