package com.discordessentials.bot;

import net.dv8tion.jda.api.exceptions.RateLimitedException;
import javax.security.auth.login.LoginException;

import com.discordessentials.bot.command.Authenticator;

public class Launcher {
	
	private static final boolean DEBUG = true;

	private static final int TOTAL_SHARDS = 4;

	private static final int CURRENT_SHARD = 0;

	public static void main(String[] args) {
		try {
			Authenticator authenticator = new Authenticator();

			Bot bot = new Bot(authenticator.getToken(), DEBUG, CURRENT_SHARD, TOTAL_SHARDS);
		} catch(LoginException | InterruptedException | RateLimitedException initiationException) {
			initiationException.printStackTrace();
		}
	}

}
