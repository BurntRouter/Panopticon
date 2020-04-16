package com.discordessentials.bot.command.impl;

import java.awt.Color;
import java.util.List;

import com.discordessentials.bot.Version;
import com.discordessentials.bot.command.Command;
import com.discordessentials.bot.command.CommandManager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

public class CommandInfo extends Command {

	private JDA api;
	
	public CommandInfo(JDA api) {
		super(new String[] {"info"}, new String[] {}, "Displays bot info");
		this.api = api;
	}

	@Override
	public void onUse(Message query, List<String> arguments, CommandManager commandManager) throws Exception {
		int users = 0;
		int servers = 0;
		
		for(Guild server : this.api.getGuilds()){
			users += (server.getMembers().size());
		}
		
		servers = (this.api.getGuilds().size());

		int shard = (api.getShardInfo().getShardId() + 1);
		
		int shards = (api.getShardInfo().getShardTotal());
		
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.BLUE);
				
		embed.setTitle("Discord Essentials #" + shard + "/" + shards + " Information", "https://github.com/BurntRouter/DiscordEssentials");
		
		embed.setFooter("Designed and ran by Router#1384");
		
		embed.addField("\nShard Guilds", "" + servers, false);
		embed.addField("Shard Users", "" + users, false);
		embed.addField("Version Number", "" + Version.VERSION, true);
		embed.addField("Bot Author", "Router#1384", true);

		query.getChannel().sendMessage(embed.build()).queue();
	}
	
}
