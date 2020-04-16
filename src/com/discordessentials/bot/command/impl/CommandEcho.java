package com.discordessentials.bot.command.impl;

import java.sql.SQLException;
import java.util.List;

import com.discordessentials.bot.command.Command;
import com.discordessentials.bot.command.CommandManager;
import com.discordessentials.bot.database.AccountManager;
import net.dv8tion.jda.api.entities.Message;

public class CommandEcho extends Command {
	
	
	private AccountManager accountManager;

	public CommandEcho(AccountManager accountManager) {
		super(new String[] {"echo"}, new String[] {"message"}, "Makes the bot repeat what you want it to.");
		
		this.accountManager = accountManager;
	}
	

	@Override
	public void onUse(Message query, List<String> arguments, CommandManager commandManager) throws Exception {
		if(query.getAuthor().isBot() == true) {
			System.out.println("Ignored Echo from a Bot");
		}
		else {
			String guildid = query.getGuild().getId();
			String prefix = CommandEcho.getPrefix(this.accountManager, guildid);
			String echo = query.getContentDisplay();
			System.out.println("The prefix is: " + prefix);
			echo = echo.replaceFirst(prefix, "");
			echo = echo.replaceFirst("echo", "");
			query.getChannel().sendMessage(echo).queue();
		}
	}
	
	public static String getPrefix(AccountManager p, String guildid) throws SQLException {
		return p.getPrefix(guildid);
	}

}
