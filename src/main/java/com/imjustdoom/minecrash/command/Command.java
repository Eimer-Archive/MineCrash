package com.imjustdoom.minecrash.command;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public interface Command {
    String[] getName();

    String getDescription();

    String[] getRoles();

    String[] getUsers();

    void execute(User user, String[] args, Message message, TextChannel channel);

    List<Command> getCommands();
}
