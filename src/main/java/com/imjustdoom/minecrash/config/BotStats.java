package com.imjustdoom.minecrash.config;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@Getter
@Setter
public class BotStats {

    private int solvedErrors;

    public void save() {
        try {
            Writer writer = new FileWriter("stats.json");
            new Gson().toJson(this, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
