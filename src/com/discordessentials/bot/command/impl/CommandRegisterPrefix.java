package com.discordessentials.bot.command.impl;

import com.discordessentials.bot.command.Command;
import com.discordessentials.bot.command.CommandManager;
import com.discordessentials.bot.database.MySQL;
import com.discordessentials.bot.database.prefix.PrefixRegistry;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CommandRegisterPrefix extends Command {

	private final Permission REQUIRED_PERMISSION = Permission.MANAGE_SERVER;

	private final int CHARACTER_MAX = 4;

	private final String[] bannedTerms = {"fag", "nig", "jew", "ass", "anal", "boob", "cock", "cum", "kkk", "milf", "nazi", "orgy", "porn", "pube", "pussy", "rape", "scat", "sex", "shit", "fuck", "slut", "twat", "tit", "wank"};

	public CommandRegisterPrefix() {
		super(new String[] {"setprefix", "prefix"}, new String[] {"prefix"}, "Set the prefix");
	}

	@Override
	public void onUse(Message query, List<String> arguments, CommandManager commandManager) throws Exception {
		Member queryMember = query.getMember();

		if(queryMember.hasPermission(this.REQUIRED_PERMISSION)) {
			String guildId = query.getGuild().getId();
			String oldPrefix = PrefixRegistry.getInstance().getPrefixForGuild(guildId);
			String prefix = StringUtils.substringBetween(query.getContentRaw(), "\"");

			if(this.validPrefix(prefix)) {
				PrefixRegistry.getInstance().registerCustomPrefix(commandManager.getAccountManager().getMysql(), guildId, prefix + " ");

				String prefixedStart = PrefixRegistry.getInstance().getPrefixedCommand(guildId, "help");

				query.getTextChannel().sendMessage(":white_check_mark: Prefix has been set successfully! \n\nExample usage: " + prefixedStart).queue();
			} else {
				String usage = (oldPrefix + this.getIdentifiers()[0] + " \"yourprefix\"");
				String example = (oldPrefix + this.getIdentifiers()[0] + " \".de \"");
				query.getTextChannel().sendMessage("Either the command was used incorrectly, or the requested prefix exceeds **" + this.CHARACTER_MAX + "** characters (excluding spaces).\n-\n\n**Correct usage:**\n" + usage + "\n\n**Example:**\n" + example).queue();
			}

		} else {
			query.getTextChannel().sendMessage("You require the `" + this.REQUIRED_PERMISSION.getName() + "` permission to use this command!").queue();
		}
	}

	private boolean validPrefix(String prefix) {
		if (prefix != null) {
			if (prefix.length() > 0 && !this.exceedsCharacterMax(prefix) && !this.containsBannedTerm(prefix)) {
				return true;
			}
		}

		return false;
	}

	private boolean exceedsCharacterMax(String prefix) {
		int containedSpaces = StringUtils.countMatches(prefix, " ");

		int prefixLengthWithoutSpaces = prefix.length() - containedSpaces;

		return prefixLengthWithoutSpaces > this.CHARACTER_MAX;
	}

	private boolean containsBannedTerm(String prefix) {
		for(String bannedTerm : this.bannedTerms) {
			if(prefix.toLowerCase().contains(bannedTerm.toLowerCase())) {
				return true;
			}
		}

		return false;
	}

}
