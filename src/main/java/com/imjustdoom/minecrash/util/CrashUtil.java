package com.imjustdoom.minecrash.util;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CrashUtil {

    public static EmbedBuilder getDefaultEmbed() {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.CYAN);
        embed.setFooter("Discord invite: https://discord.gg/ydGK5jYV6t");
        return embed;
    }

    public static EmbedBuilder getErrorEmbed() {
        return getDefaultEmbed().setTitle("There was an error...").setColor(Color.RED);
    }

    public static EmbedBuilder getCrashEmbed(String solution, String crash) {
        EmbedBuilder embed = getDefaultEmbed();
        embed.setTitle(crash);
        embed.addField("Solution", solution, false);
        embed.setColor(Color.GREEN);
        return embed;
    }
}
