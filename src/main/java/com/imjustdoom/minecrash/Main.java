package com.imjustdoom.minecrash;

import com.google.gson.Gson;
import com.imjustdoom.minecrash.command.CommandManager;
import com.imjustdoom.minecrash.config.Config;
import com.imjustdoom.minecrash.crash.Crash;
import com.imjustdoom.minecrash.database.DatabaseConnection;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import lombok.Getter;
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

@Getter
public class Main {

    private static Main INSTANCE;

    public static Main getInstance() {
        return INSTANCE;
    }

    private final String[] prefix = new String[]{"!", "C!"};

    private final CommandManager commandManager = new CommandManager();
    private final DatabaseConnection db;
    private final JDA jda;
    private Config config;

    private final List<Crash> crashList = new ArrayList<>();

    public Main() throws IOException, LoginException, InterruptedException {
        INSTANCE = this;

        try {
            String data = Files.readString(Path.of("config.json"));
            config = new Gson().fromJson(data, Config.class);
        } catch (IOException e) {
            System.out.println("Unable to load config. Shutting down.");
            e.printStackTrace();
            System.exit(0);
        }

        db = new DatabaseConnection();

        loadErrorFiles();

        JDABuilder builder = JDABuilder.createDefault(config.getToken())
                .addEventListeners(commandManager, new EventWaiter())
                .enableIntents(GatewayIntent.GUILD_MEMBERS);

        jda = builder.build();

        jda.awaitReady();

        jda.getPresence().setActivity(Activity.playing("Solving crash reports!"));
    }

    public void loadErrorFiles() throws IOException {
        //new File(Main.class.getResource("/crashes/").toURI())
        for(File file : new File(String.valueOf(Path.of("crashes/"))).listFiles()) {
            crashList.add(new Gson().fromJson(Files.readString(file.toPath()), Crash.class));
        }
    }

    public static void main(String[] args) throws LoginException, IOException, InterruptedException {
        new Main();
    }
}