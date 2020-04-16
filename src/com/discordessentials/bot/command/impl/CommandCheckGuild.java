package com.discordessentials.bot.command.impl;

import com.discordessentials.bot.command.Command;
import com.discordessentials.bot.command.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.utils.WidgetUtil;
import net.dv8tion.jda.api.utils.WidgetUtil.Widget;


import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommandCheckGuild extends Command {

    private final String STAFF_CHANNEL_ID = ("291056552528314389");

    private JDA api;

    public CommandCheckGuild(JDA api) {
        super(new String[] {"guild"}, new String[] {}, "");
        this.api = api;
        this.setUnlisted(true);
    }

    @Override
    public void onUse(Message query, List<String> arguments, CommandManager commandManager) throws Exception {

        if(this.queryAllowed(query)) {
            String guildIdentifier = (query.getContentRaw().split(" ")[2]);
            Guild resultGuild = (null);

            System.out.println("Checking invite " + guildIdentifier);

            Invite queryInvite = Invite.resolve(this.api, guildIdentifier).complete();

            System.out.println("Result: " + queryInvite.getCode());

            if(queryInvite.getGuild() != null) {
                resultGuild = this.api.getGuildById(queryInvite.getGuild().getId());
            } else {
                resultGuild = this.api.getGuildById(guildIdentifier);
            }

            if(resultGuild != null) {
                query.getTextChannel().sendMessage(this.generateEmbedForGuild(resultGuild)).queue();
            } else {
                query.getTextChannel().sendMessage("Guild not found. Perhaps it's on a different shard?").queue();
            }
        }
    }

    private boolean queryAllowed(Message query) {
        //return query.getTextChannel().getId().equals(this.STAFF_CHANNEL_ID);
        return true;
    }

    private MessageEmbed generateEmbedForGuild(Guild guild) {
        EmbedBuilder guildEmbed = new EmbedBuilder();

        guildEmbed.setImage(guild.getIconUrl());

        guildEmbed.setTitle(guild.getName());

        guildEmbed.addField("Total Members", "" + guild.getMembers().size(), false);

        guildEmbed.addField("Bot Members", "" + this.getGuildBotCount(guild), false);

        guildEmbed.addField("Channels", "" + guild.getChannels().size(), false);

        String guildCreationTime = guild.getTimeCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        guildEmbed.addField("Creation", guildCreationTime, false);

        return guildEmbed.build();
    }

    private int getGuildBotCount(Guild guild) {
        int botMembers = 0;

        for(Member member : guild.getMembers()) {
            if(member.getUser().isBot()) {
                botMembers++;
            }
        }

        return botMembers;
    }

}
