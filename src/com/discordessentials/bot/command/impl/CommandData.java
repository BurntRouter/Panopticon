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
		commandManager.sendBatchedResponse(query.getGuild().getId(), query.getTextChannel().getId(),"The Snail Racing bot may store minimal data to help track stats and help us ensure the bot is being fair to all users. The data that is stored is purely statistical, meaning we only store rupees earned, items, levels, total races done, total races won, total races lost, total bets placed, total bets won, total bets lost, user ids, and guild ids. The Snail Racing bot does not store messages under any circumstance and no messages or commands used are available for any team member including developers to see. If you would like to request a copy of all data stored or to have all your data deleted please contact Router#1384 and be warned that this WILL delete ALL data including progress, items, XP, and premium status. After you contact Router please allow 24-48 hours for him to delete the data. \n\nFor more info or any questions feel free to ask in the official Snail Racing Discord Guild at <https://discord.io/snail>");
	}

}