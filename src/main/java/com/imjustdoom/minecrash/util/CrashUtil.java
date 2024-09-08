package com.imjustdoom.minecrash.util;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CrashUtil {

    public static EmbedBuilder getCrashEmbed(String solution, String crash) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(crash);
        embed.addField("Solution", solution, false);
        embed.setColor(Color.GREEN);
        embed.setFooter("Discord invite: " + "Soon:tm:");
        return embed;
    }
}
