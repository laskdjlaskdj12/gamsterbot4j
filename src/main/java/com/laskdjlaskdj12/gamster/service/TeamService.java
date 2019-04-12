package com.laskdjlaskdj12.gamster.service;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;

public class TeamService {

    private Guild guild;
    private MessageChannel messageChannel;

    public TeamService(Guild guild, MessageChannel messageChannel){
        this.guild = guild;
        this.messageChannel = messageChannel;
    }


}
