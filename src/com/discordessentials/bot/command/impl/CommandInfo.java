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
		embed.setColor(Color.GREEN);
		
		embed.setThumbnail("https://i.imgur.com/CLUK1rd.png");
		
		embed.setTitle("Snail Racing Shard #" + shard + "/" + shards + " Information", "https://discord.io/snail");
		
		embed.setFooter("Enjoy your racing! - Zach, Router, & Ieyfo", "https://i.imgur.com/CLUK1rd.png");
		
		embed.addField("\nShard Guilds", "" + servers, false);
		embed.addField("Shard Users", "" + users, false);
		embed.addField("Version Number", "" + Version.VERSION, true);
		embed.addField("Bot Author", "Dr Zachary Smith#9260 & Router#1384", true);

		query.getChannel().sendMessage(embed.build()).queue();
		
		//commandManager.sendBatchedResponse(query.getGuild().getId(), query.getTextChannel().getId(), "I was created by Dr Zachary Smith#9260. This is shard " + shard + "/" + api.getShardInfo().getShardTotal() + ", which is supplying **" + api.getGuilds().size() + "** of the bot's guilds. The servers on this shard have a total of **" + users + "** users." + "\nThis is Snail Racing version " + Version.VERSION + ".");
		//commandManager.sendBatchedResponse(query.getGuild().getId(), query.getTextChannel().getId(),"I was created by Dr Zachary Smith#9260. I am currently entertaining **" + users + "** people in **" + servers + "** servers. :smile:\n_I was created on December 8, 2016, and this is version " + Version.VERSION + "_");
	}
	
}
