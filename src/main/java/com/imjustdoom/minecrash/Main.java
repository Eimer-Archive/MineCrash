package com.imjustdoom.minecrash;

import com.google.gson.Gson;
import com.imjustdoom.minecrash.command.CommandManager;
import com.imjustdoom.minecrash.config.Config;
import com.imjustdoom.minecrash.crash.Crash;
import com.imjustdoom.minecrash.database.DatabaseConnection;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String[] prefix = new String[]{"!", "C!"};

    public static CommandManager commandManager = new CommandManager();
    public static DatabaseConnection db;
    public static JDA jda;
    public static Config config;

    public static List<Crash> crashList = new ArrayList<>();

    public static void main(String[] args) throws LoginException, IOException, InterruptedException, URISyntaxException {

        String data = Files.readString(Path.of("config.json"));
        config = new Gson().fromJson(data, Config.class);
        db = new DatabaseConnection();

        //new File(Main.class.getResource("/crashes/").toURI())
        for(File file : new File(String.valueOf(Path.of("crashes/"))).listFiles()) {
            crashList.add(new Gson().fromJson(Files.readString(file.toPath()), Crash.class));
        }

        JDABuilder builder = JDABuilder.createDefault(config.token)
                .addEventListeners(commandManager, new EventWaiter())
                .enableIntents(GatewayIntent.GUILD_MEMBERS);

        jda = builder.build();

        jda.awaitReady();

        jda.getPresence().setActivity(Activity.playing("Solving crash reports"));
    }
}