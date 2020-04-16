package com.discordessentials.bot.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.discordessentials.bot.database.AccountManager;
import com.discordessentials.bot.database.MySQL;
import com.discordessentials.bot.database.prefix.PrefixRegistry;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {

	private List<Command> commands;
	
	private AccountManager accountManager;

	private JDA api;

	private PrefixRegistry prefixRegistry;

	public CommandManager(JDA api) throws ClassNotFoundException, SQLException {
		this.commands = new ArrayList<>();
		
		List<String> volume = new Authenticator().getCredentials();

		MySQL database = new MySQL("com.mysql.jdbc.Driver", "jdbc:mysql://" + volume.get(1) + "/" + volume.get(2) + "?autoReconnect=true&user=" + volume.get(3) + "&password=" + volume.get(4));

		this.setAccountManager(new AccountManager(database));

		this.api = api;

		this.prefixRegistry = PrefixRegistry.getInstance();
		this.prefixRegistry.reloadRegistry(database);
	}

	public void sendBatchedResponse(String serverId, String channelId, String messageContent){
		this.api.getGuildById(serverId).getTextChannelById(channelId).sendMessage(messageContent).queue();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		try {
			if (!event.getMessage().getAuthor().isBot()) {
				this.executeQuery(this.api, event.getMessage());
			}
		} catch(Exception uncaughtMessageException) {
			System.err.println("Exception from message event");
			uncaughtMessageException.printStackTrace();
		}
	}
	
	public void executeQuery(JDA api, Message query) {
		String content = (query.getContentRaw());

		String prefix = PrefixRegistry.getInstance().getPrefixForGuild(query.getGuild().getId()).toLowerCase();
		
		if(content.startsWith("!admin ")) {
			if (query.getAuthor().getId().equals("135723242978672640")) {
				content = content.replaceFirst(prefix, "");

				List<String> fullQuery = (Arrays.asList(content.split(" ")));

				if (fullQuery.get(1).equalsIgnoreCase("gc") || fullQuery.get(1).equalsIgnoreCase("garbagecollect")) {
					System.gc();
					query.getTextChannel().sendMessage("Garbage collected.").queue();
				}

				if (fullQuery.get(1).equalsIgnoreCase("quit")) {
					this.api.shutdown();
					System.exit(0);
				}

			}
		}

		content = (content.toLowerCase());

		String selfMention = query.getGuild().getSelfMember().getAsMention();

		if(query.getContentRaw().startsWith(selfMention)) {
			prefix = (selfMention) + " ";
		}
	}
	
	public void registerCommand(Command command){
		this.getCommands().add(command);
	}
	
	public List<Command> getCommands(){
		return this.commands;
	}

	public AccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}