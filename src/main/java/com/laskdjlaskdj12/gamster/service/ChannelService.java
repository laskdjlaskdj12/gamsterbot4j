package com.laskdjlaskdj12.gamster.service;

import com.laskdjlaskdj12.gamster.domain.vo.Team;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

import javax.annotation.Nullable;
import java.util.List;

public class ChannelService {
    private Guild guild;
    private MessageChannel messageChannel;

    public ChannelService(Guild guild, MessageChannel messagechannel){
        this.guild = guild;
        this.messageChannel = messagechannel;
    }

    @Nullable
    public VoiceChannel getReadyChannel() {
        List<VoiceChannel> voiceChannelList = guild.getVoiceChannelsByName("match", true);

        if(voiceChannelList.isEmpty()){
            return null;
        }

        return voiceChannelList.get(0);
    }

    public void createTeamChannel(List<Team> sideTeamList) {
        for (Team team: sideTeamList) {
            String teamName = team.getTeamName();
            VoiceChannel voiceChannel = (VoiceChannel) guild.getController().createVoiceChannel(teamName).complete();
            team.setVoiceChannel(voiceChannel);
        }
    }
}
