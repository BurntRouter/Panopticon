package com.discordessentials.bot.command.impl;

import java.util.List;

import com.discordessentials.bot.command.Command;
import com.discordessentials.bot.command.CommandManager;

import net.dv8tion.jda.api.entities.Message;

public class CommandInvite extends Command {

	public CommandInvite() {
		super(new String[] {"invite"}, new String[] {}, "Invite the bot");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUse(Message query, List<String> arguments, CommandManager commandManager) throws Exception {
		commandManager.sendBatchedResponse(query.getGuild().getId(), query.getTextChannel().getId(),"You can invite me to my own server by using this link. If you'd like an admin to add it to their server, send them this link too!\nhttps://discordapp.com/oauth2/authorize?client_id=256556410031046657&scope=bot&permissions=281600");
	}

}
