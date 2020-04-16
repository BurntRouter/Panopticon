package com.discordessentials.bot.command;

/**
 * Created by oprsec on 1/29/17.
 */
public class BatchedResponse {

    private final String serverId;
    private final String channelId;
    private final String messageContent;

    public BatchedResponse(String serverId, String channelId, String messageContent){
        this.serverId = serverId;
        this.channelId = channelId;
        this.messageContent = messageContent;
    }

    public String getServerId(){
        return this.serverId;
    }

    public String getChannelId(){
        return this.channelId;
    }

    public String getMessageContent(){
        return this.messageContent;
    }

}
