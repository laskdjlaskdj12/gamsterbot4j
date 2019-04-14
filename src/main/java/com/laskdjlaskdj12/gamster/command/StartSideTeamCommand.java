package com.laskdjlaskdj12.gamster.command;

import com.laskdjlaskdj12.gamster.domain.foam.CommandFoam;
import com.laskdjlaskdj12.gamster.domain.vo.GuildInfo;
import com.laskdjlaskdj12.gamster.domain.vo.Team;
import com.laskdjlaskdj12.gamster.service.ChannelService;
import com.laskdjlaskdj12.gamster.service.GuildInfoService;
import com.laskdjlaskdj12.gamster.service.TeamService;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import java.util.List;

public class StartSideTeamCommand implements CommandExecutor {

    @Command(aliases = "/편나누기", description = "모든 참여자들을 두ㅏ편으로 나눕니다.")
    public void makeSideTeam(Guild guild, MessageChannel messageChannel) {
        ChannelService channelService = new ChannelService(guild, messageChannel);
        TeamService teamService = new TeamService(guild, messageChannel);

        VoiceChannel readyChannel = channelService.getReadyChannel();
        if (readyChannel == null) {
            MessageEmbed messageEmbed = CommandFoam.getInstance().noVoiceChannelMessage();
            messageChannel.sendMessage(messageEmbed).complete();
            return;
        }

        List<Member> inMember = readyChannel.getMembers();
        if (inMember.isEmpty() || inMember.size() == 1) {

            MessageEmbed messageEmbed;
            if (inMember.size() == 1) {
                messageEmbed = CommandFoam.getInstance().noMakeSideMembers();
            } else {
                messageEmbed = CommandFoam.getInstance().noMembersFound();
            }

            messageChannel.sendMessage(messageEmbed).complete();
            return;
        }

        List<Team> sideTeamList = teamService.makeSideTeam(inMember);
        registerTeamToGuildInfo(guild, sideTeamList);

        List<MessageEmbed> teamInfoMessage = CommandFoam.getInstance().showTeamMessage(sideTeamList);
        for (MessageEmbed messageEmbed : teamInfoMessage) {
            messageChannel.sendMessage(messageEmbed).complete();
        }

        channelService.createTeamChannel(sideTeamList);

        teamService.countDownAndMove(sideTeamList, 10);

        GuildInfo guildInfo = GuildInfoService.getInstance().getGuildInfo(guild.getId());
        if (guildInfo == null) {
            guildInfo = new GuildInfo();
        }
        guildInfo.setTeamList(sideTeamList);
    }

    private void registerTeamToGuildInfo(Guild guild, List<Team> sideTeam) {
        GuildInfo guildInfo = GuildInfoService.getInstance().getGuildInfo(guild.getId());

        if (guildInfo == null) {
            guildInfo = new GuildInfo();
        }

        guildInfo.setMaximumTeamCount(2);
        guildInfo.setTeamList(sideTeam);

        GuildInfoService.getInstance().setGuildInfo(guild.getId(), guildInfo);
    }
}
