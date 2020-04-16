package com.discordessentials.bot.command.impl;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import com.discordessentials.bot.command.Command;
import com.discordessentials.bot.command.CommandManager;

import com.discordessentials.bot.database.prefix.PrefixRegistry;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;

public class CommandHelp extends Command {

	private CommandManager commandManager;
	
	public CommandHelp(CommandManager commandManager) {
		super(new String[] {"help"}, new String[] {}, "Displays help information");
		
		this.commandManager = commandManager;
		this.setUnlisted(true);
		this.setRequiresWritePermission(false);
	}

	@Override
	public void onUse(Message query, List<String> arguments, CommandManager commandManager) throws Exception {
		EmbedBuilder embed = new EmbedBuilder();
		//embed.setTitle("Snail Racing Help");
		embed.setColor(Color.green);
		
		String commands = ("");
		String descriptions = ("");
		String argumentsDisplay = ("");
		
		for(Command command : this.commandManager.getCommands()){
			if(!command.isUnlisted()){
				commands += (this.getPrefixedCommand(query, command.getIdentifiers()[0]) + "\n\n");
				
				descriptions += (command.getDescription() + "\n\n");
				
				if(command.getArguments().length > 0){
					argumentsDisplay += (Arrays.toString(command.getArguments()) + "\n\n");
				}else{
					argumentsDisplay += ("None\n\n");
				}
			}
		}

		if(PrefixRegistry.getInstance().customPrefixExistsForGuild(query.getGuild().getId())) {
			String customPrefix = PrefixRegistry.getInstance().getPrefixForGuild(query.getGuild().getId());

			embed.setTitle("Using custom prefix \"" + customPrefix + "\" from guild \"" + query.getGuild().getName() + "\"");
		}

		embed.addField("Commands", commands, true);
		embed.addField("Description", descriptions, true);
		embed.addField("Arguments", argumentsDisplay, true);

		PrivateChannel channel = query.getAuthor().openPrivateChannel().complete();
		
		channel.sendMessage(embed.build()).queue();
		channel.sendMessage("Bug or suggestion? Let us know on our Github! https://github.com/BurntRouter/DiscordEssentials").queue();
		channel.sendMessage("Questions or Comments? DM Router#1384 for help!").queue();
		commandManager.sendBatchedResponse(query.getGuild().getId(), query.getTextChannel().getId(),"DMed :thumbsup: :sparkles:");
	}

}
