package com.discordessentials.bot.command;

import java.util.List;

import com.discordessentials.bot.database.prefix.PrefixRegistry;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public abstract class Command {

	private String[] identifiers;
	
	private String[] arguments;
	
	private String description;

	private boolean unlisted;

	private boolean requiresWritePermission = true;
	
	public Command(String[] identifiers, String[] arguments, String description){
		this.identifiers = identifiers;
		this.arguments = arguments;
		this.description = description;
	}

	/**
	 * Called when the command is used
	 * @param query
	 * @param arguments All arguments, including the command identifier at index 0
	 * @throws For all uncaught exceptions that should be dealt with by a generic error message
	 */
	public abstract void onUse(Message query, List<String> arguments, CommandManager commandManager) throws Exception;
	
	public String[] getIdentifiers() {
		return identifiers;
	}

	public String[] getArguments() {
		return arguments;
	}
	
	/**
	 * @return Whether the provided identifier is an identifier of the command
	 */
	public boolean identifierMatches(String identifier){
		for(String potentialMatchIdentifier : this.getIdentifiers()){
			////System.out.println("Checking if my ID " + potentialMatchIdentifier + " matches request " + identifier);
			if(potentialMatchIdentifier.equalsIgnoreCase(identifier)){
				return true;
			}
		}
		
		return false;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isUnlisted() {
		return unlisted;
	}

	public void setUnlisted(boolean unlisted) {
		this.unlisted = unlisted;
	}

	public String getPrefixedCommand(Message originMessage, String commandIdentifier) {
		return PrefixRegistry.getInstance().getPrefixedCommand(originMessage.getGuild().getId(), commandIdentifier);
	}

	public void setRequiresWritePermission(boolean requiresWritePermission) {
		this.requiresWritePermission = requiresWritePermission;
	}

	public boolean requiresWritePermission() {
		return this.requiresWritePermission;
	}

	public boolean canExecuteForChannel(TextChannel textChannel, User selfUser) {
		Member selfMember = textChannel.getGuild().getMemberById(selfUser.getId());

		if(this.requiresWritePermission()) {
			return selfMember.hasPermission(textChannel, Permission.MESSAGE_WRITE);
		} else {
			return true;
		}
	}

}
