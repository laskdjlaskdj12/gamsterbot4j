package com.laskdjlaskdj12.gamster.command;

import com.laskdjlaskdj12.gamster.domain.foam.CommandFoam;
import com.laskdjlaskdj12.gamster.domain.vo.Team;
import com.laskdjlaskdj12.gamster.service.TeamService;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import javax.annotation.Nullable;
import java.util.List;

public class EndTeamMakingCommand implements CommandExecutor {

    @Command(aliases = "/끝")
    public void EndCivilWar(Guild guild, MessageChannel messageChannel){
        TeamService teamService = TeamService.getInstance();
        List<Team> teamList = teamService.getTeamList(guild.getId());

        if(teamList == null){
            MessageEmbed messageEmbed = CommandFoam.getInstance().noTeamMake();
            messageChannel.sendMessage(messageEmbed).queue();
            return;
        }

        VoiceChannel matchChannel = findMatchChannel(guild);
        if(matchChannel == null){
            MessageEmbed messageEmbed = CommandFoam.getInstance().noMatchChannelMessage();
            messageChannel.sendMessage(messageEmbed).queue();
            return;
        }

        // 모든 참가자들을 데리고 오기
        MessageEmbed messageEmbed = CommandFoam.getInstance().moveToMatchChannelMessage();
        messageChannel.sendMessage(messageEmbed).queue();
        for (Team team: teamList){
            List<Member> members = team.getVoiceChannel().getMembers();

            for(Member member : members){
                guild.getController().moveVoiceMember(member, matchChannel).complete();
            }

            // 음성채널 없애기
            team.getVoiceChannel().delete().complete();
        }

        // 팀을 없애기
        teamService.removeTeam(guild.getId());
    }

    @Nullable
    private VoiceChannel findMatchChannel(Guild guild) {
        return guild.getVoiceChannelsByName("match", true).get(0);
    }
}
