package com.discordessentials.bot;

import java.sql.SQLException;

import javax.security.auth.login.LoginException;

import com.discordessentials.bot.command.impl.*;
import net.dv8tion.jda.api.entities.Activity;
import com.discordessentials.bot.command.CommandManager;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.utils.ChunkingFilter;

/**
 * Provides the master functionality of the bot, connecting to
 * and managing the API and command manager
 * @author jordanb84
 *
 */
public class Bot extends Thread {

	private JDA api;
	
	private CommandManager commandManager;

	private final String token;
	private final int shard;

	private int shardTotal;

	public Bot(String token, boolean debug, int shard, int shardTotal) throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException {
		this.token = token;
		this.shard = shard;
		this.shardTotal = shardTotal;

		this.setup();
		this.start();
	}
	
	public void setup() throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException {
		System.out.println("Starting building");
		this.api = new JDABuilder(AccountType.BOT).setToken(token).setChunkingFilter(ChunkingFilter.NONE).setActivity(Activity.of(Activity.ActivityType.DEFAULT, ".de help | By Router#1384")).useSharding(this.shard, this.shardTotal).build().awaitReady();		
		System.out.println("Finished " + this.api.getShardInfo().getShardId() + " with " + this.api.getGuilds().size() + " guilds. " + api.getSelfUser().getName());
	}

	@Override
	public void run(){
		try {
			System.out.println("Running (for shard " + (this.api.getShardInfo().getShardId() + 1) + ")");
			System.out.println("Registering command manager");
			this.commandManager = new CommandManager(this.api);
			System.out.println("Registered command manager");

			System.out.println("Registering commands");
			this.registerCommands();
			System.out.println("Registered commands");
			System.out.println("Done registering commands, connecting...");
			this.connect();
			int shard = (api.getShardInfo().getShardId() + 1);
			System.out.println("CONNECTED SHARD " + shard);

		}catch(ClassNotFoundException | SQLException | IllegalArgumentException e){
			e.printStackTrace();
		}
	}

	public void registerCommands(){
		this.commandManager.registerCommand(new CommandRegisterPrefix());
		this.commandManager.registerCommand(new CommandInvite());
		this.commandManager.registerCommand(new CommandInfo(this.api));
		this.commandManager.registerCommand(new CommandHelp(this.commandManager));
		this.commandManager.registerCommand(new CommandData());
		this.commandManager.registerCommand(new CommandEcho(this.commandManager.getAccountManager()));

	}
	
	private void connect(){
		int shard = (this.api.getShardInfo().getShardId() + 1);
		System.out.println("ADDING EVENT LISTENER FOR SHARD " + shard);
		this.api.addEventListener(this.commandManager);
	}

	
}
