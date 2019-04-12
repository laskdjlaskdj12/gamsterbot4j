package com.laskdjlaskdj12.gamster.command;

import com.laskdjlaskdj12.gamster.domain.foam.CommandFoam;
import com.laskdjlaskdj12.gamster.domain.vo.GuildInfo;
import com.laskdjlaskdj12.gamster.domain.vo.Team;
import com.laskdjlaskdj12.gamster.service.GuildInfoService;
import com.laskdjlaskdj12.gamster.service.TeamService;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import java.util.List;

public class StartSideTeamCommand implements CommandExecutor {

    @Command(aliases = "/편나누기", description = "모든 참여자들을 양팀의 편으로 나눕니다.")
    public void makeSideTeam(Guild guild, MessageChannel messageChannel){
        ChannelService channelService = new ChannelService(guild, messageChannel);
        TeamService teamService = new TeamService(guild, messageChannel);

        VoiceChannel readyChannel = channelService.getReadyChannel();

        List<Member> inMember = readyChannel.getMembers();
        if(inMember.isEmpty()){
            MessageEmbed messageEmbed = CommandFoam.getInstance().noMembersFound();
            messageChannel.sendMessage(messageEmbed).queue();
            return;
        }

        List<Team> sideTeam = teamService.makeSideTeam(inMember);
        registerTeamToGuildInfo(guild, sideTeam);

        List<MessageEmbed> teamInfoMessage = CommandFoam.getInstance().showTeamMessage(sideTeam);
        for(MessageEmbed messageEmbed: teamInfoMessage) {
            messageChannel.sendMessage(messageEmbed).complete();
        }

        channelService.createTeamChannel(sideTeam);

        TeamService.countDownAndMove(sideTeam, 10);
    }

    private void registerTeamToGuildInfo(Guild guild, List<Team> sideTeam) {
        GuildInfo guildInfo = GuildInfoService.getInstance().getGuildInfo(guild.getId());

        if(guildInfo == null){
            guildInfo = new GuildInfo();
        }

        guildInfo.setMaximumTeamCount(2);
        guildInfo.setTeamList(sideTeam);

        GuildInfoService.getInstance().setGuildInfo(guild.getId(), guildInfo);
    }
}
