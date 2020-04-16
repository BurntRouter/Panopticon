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
		commandManager.sendBatchedResponse(query.getGuild().getId(), query.getTextChannel().getId(),"To add me to your server just click this link!\nhttps://discordapp.com/oauth2/authorize?client_id=700187991460806696&scope=bot&permissions=1610608119");
	}

}
