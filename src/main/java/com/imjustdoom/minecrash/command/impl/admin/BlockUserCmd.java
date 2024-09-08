/*
package com.imjustdoom.minecrash.command.impl.admin;

import com.imjustdoom.minecrash.MineCrash;
import com.imjustdoom.minecrash.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;
import java.util.List;

public class BlockUserCmd implements Command {

    @Override
    public String[] getName() {
        return new String[]{"blockuser", "block", "bu", "b"};
    }

    @Override
    public String getDescription() {
        return "Add/remove a user from the block list (Block list stops submitting errors to be solved by a certain user)";
    }

    @Override
    public String[] getRoles() {
        return new String[]{};
    }

    @Override
    public String[] getUsers() {
        return new String[]{MineCrash.get().getConfig().getOwner()};
    }

    @Override
    public void execute(User user, String[] args, Message message, TextChannel channel) {

        if (args.length < 3) {
            message.reply("Please specify a user and a command (block/unblock)").queue();
            return;
        }

        String block = args[1].toLowerCase();
        String blockUser = args[2];

        switch (block) {
            case "block":
                if (MineCrash.get().getDb().isUserBlocked(blockUser)) {
                    message.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.CYAN)
                            .setTitle("User is already blocked")
                            .setDescription("This user is already in the block list")
                            .build()).queue();
                    return;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                MineCrash.get().getDb().addUserToBlockedUsers(blockUser, args.length > 3 ? sb.toString() : "");

                message.replyEmbeds(new EmbedBuilder()
                        .setColor(Color.CYAN)
                        .setTitle("Blocked user")
                        .setDescription("This user has been blocked.")
                        .build()).queue();
                break;
            case "unblock":
                if (!MineCrash.get().getDb().isUserBlocked(blockUser)) {
                    message.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.CYAN)
                            .setTitle("User is not blocked")
                            .setDescription("This user is not in the block list")
                            .build()).queue();
                    return;
                }

                MineCrash.get().getDb().removeUserFromBlockedUsers(blockUser);

                message.replyEmbeds(new EmbedBuilder()
                        .setColor(Color.CYAN)
                        .setTitle("Unblocked user")
                        .setDescription("This user has been removed from the block list.")
                        .build()).queue();
                break;
            case "info":
            case "reason":
                if (!MineCrash.get().getDb().isUserBlocked(blockUser)) {
                    message.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.CYAN)
                            .setTitle("User is not blocked")
                            .setDescription("This user is not in the block list")
                            .build()).queue();
                    return;
                }

                String reason = MineCrash.get().getDb().getBlockedUserReason(blockUser);
                message.replyEmbeds(new EmbedBuilder()
                        .setColor(Color.CYAN)
                        .setTitle("Blocked user info")
                        .setDescription("This user is blocked.\nReason: " + (reason == null || reason.equals("") ? "There is no set ban reason for this user" : reason))
                        .build()).queue();
                break;
        }
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
*/
