package com.discordessentials.bot.command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Authenticator {
	
	public List<String> getCredentials() {
		List<String> lines;
		try {
			lines = Files.readAllLines(new File("./volume.txt").toPath());
			
			return lines;
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		return null;
	}

	public String getToken() {
		return this.getCredentials().get(0);
	}
	
}
