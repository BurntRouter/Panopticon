package com.discordessentials.bot.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.discordessentials.bot.database.AccountManager;
import com.discordessentials.bot.database.MySQL;
import com.discordessentials.bot.database.prefix.PrefixRegistry;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {

	private List<Command> commands;
	
	private AccountManager accountManager;

	private JDA api;

	private PrefixRegistry prefixRegistry;
	
	private final String LEGACY_PREFIX = (".de ");

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
	
	if(content.startsWith(prefix) || content.startsWith(this.LEGACY_PREFIX)){
		content = content.replaceAll(Pattern.quote(prefix), "");

		content = content.replaceFirst(this.LEGACY_PREFIX, "");

		List<String> fullQuery = (Arrays.asList(content.split(" ")));
		
		for(Command command : this.getCommands()){
			if(command.canExecuteForChannel(query.getTextChannel(), this.api.getSelfUser())) {
				String queryIdentifier = (fullQuery.get(0));
				if (command.identifierMatches(queryIdentifier)) {
					try {
						command.onUse(query, fullQuery, this);
					} catch (Exception e) {
						try {
							if (!(e instanceof RateLimitedException)) {
								String args = Arrays.toString(command.getArguments());

								query.getTextChannel().sendMessage("Oops! That command was used incorrectly. Command arguments: `" + args + "`\nUsage: `" + prefix + command.getIdentifiers()[0] + " " + args.replace("[", "").replace("]", "").replace(",", "") + "`\n\nIf you are still experiencing issues, DM Router#1384 for help!").queue();
							}
						} catch (Exception e2) {
							System.out.println("Error A (error in reporting error):");
							e2.printStackTrace();
						}

						System.out.println("Error B:");
						e.printStackTrace();
					}
				}
			}
		}
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