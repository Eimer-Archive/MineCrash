package com.imjustdoom.minecrash.util;

import com.imjustdoom.minecrash.MineCrash;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class EmbedUtil {

    public static final String FOOTER = "Version: " + MineCrash.get().getShortHash() + " - Discord: discord.gg/ydGK5jYV6t";

    public static EmbedBuilder getDefaultEmbed() {
        return new EmbedBuilder()
                .setColor(Color.CYAN)
                .setFooter(FOOTER);
    }

    public static EmbedBuilder getErrorEmbed() {
        return getDefaultEmbed()
                .setTitle("There was an error...")
                .setColor(Color.RED);
    }

    public static EmbedBuilder getCrashEmbed(String solution, String crash) {
        return getDefaultEmbed()
                .setTitle(crash)
                .addField("Solution", solution, false)
                .setColor(Color.GREEN);
    }

    public static String appendOnFooter(String append) {
        return FOOTER + " - " + append;
    }

    public static String prependOnFooter(String prepend) {
        return prepend + " - " + FOOTER;
    }
}
