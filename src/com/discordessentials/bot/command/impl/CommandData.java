package com.discordessentials.bot.command.impl;

import java.util.List;

import com.discordessentials.bot.command.Command;
import com.discordessentials.bot.command.CommandManager;

import net.dv8tion.jda.api.entities.Message;

public class CommandData extends Command {

	public CommandData() {
		super(new String[] {"data"}, new String[] {}, "Info about how we use your data");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUse(Message query, List<String> arguments, CommandManager commandManager) throws Exception {
		commandManager.sendBatchedResponse(query.getGuild().getId(), query.getTextChannel().getId(),"Discord Essentials only stores info related to temporary bans and mutes. Discord Essentials does not store messages under any circumstance and no messages or commands used are available for any team member including developers to see. For more info or any questions feel free to DM Router#1384.");
	}

}