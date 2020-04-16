package com.discordessentials.bot.command.impl;

import com.discordessentials.bot.command.Command;
import com.discordessentials.bot.command.CommandManager;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;

/**
 * Created by oprsec on 2/18/17.
 */
public class CommandPing extends Command {

    public CommandPing(){
        super(new String[] {"ping"}, new String[] {}, "Just another basic ping command");
    }

    @Override
    public void onUse(Message query, List<String> arguments, CommandManager commandManager) throws Exception {
        query.getChannel().sendMessage("Pong!").queue();
    }
}
