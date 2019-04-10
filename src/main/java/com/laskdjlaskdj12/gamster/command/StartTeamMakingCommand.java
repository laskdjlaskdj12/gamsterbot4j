package com.laskdjlaskdj12.gamster.command;

import com.laskdjlaskdj12.gamster.domain.foam.CommandFoam;
import com.laskdjlaskdj12.gamster.domain.vo.Team;
import com.laskdjlaskdj12.gamster.service.TeamService;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StartTeamMakingCommand implements CommandExecutor {

    @Command(aliases = "/내전시작", description = "대기중인 참여자들을 스캔해서 랜덤으로 팀을 만들고 팀통화방을 만듭니다.")
    public void startTeamMaking(Guild guild, MessageChannel messageChannel){

        // 0. 개인톡으로 봇명령어를 시작했을때
        if(guild == null){
            MessageEmbed wrongMessage = CommandFoam.getInstance().noPrivateMessage();
            messageChannel.sendMessage(wrongMessage).queue();
            return;
        }

        // 1. 음성채널에서 대기중인 참여자들을 스캔하기
        MessageEmbed scanMembersMessage = CommandFoam.getInstance().scanMembersMessage();
        messageChannel.sendMessage(scanMembersMessage).queue();

        VoiceChannel voiceChannel = getMatchVoiceChannel(guild);
        if(voiceChannel == null){
            MessageEmbed noVoiceChannelMessage = CommandFoam.getInstance().noVoiceChannelMessage();
            messageChannel.sendMessage(noVoiceChannelMessage).queue();
            return;
        }

        List<Member> members = voiceChannel.getMembers();
        if(members.isEmpty()){
            MessageEmbed noMembersFound =  CommandFoam.getInstance().noMembersFound();
            messageChannel.sendMessage(noMembersFound).queue();
            return;
        }

        MessageEmbed showScanedMembersMessage = CommandFoam.getInstance().scanedMembersMessage(members);
        messageChannel.sendMessage(showScanedMembersMessage).queue();

        // 2. 스캔된 참여자들을 자동으로 팀을 만들어주기
        MessageEmbed makeTeamMessage = CommandFoam.getInstance().makeTeamMessage();
        messageChannel.sendMessage(makeTeamMessage).queue();

        List<Team> teamList = makeTeam(members);

        List<MessageEmbed> showTeamMessage = CommandFoam.getInstance().showTeamMessage(teamList);
        for(MessageEmbed messageEmbed : showTeamMessage) {
            messageChannel.sendMessage(messageEmbed).queue();
        }

        // 3. 각 팀별 통화방을 만들기
        for(Team team : teamList){
            VoiceChannel teamVoiceChannel = (VoiceChannel) guild.getController().createVoiceChannel(team.getTeamName()).complete();
            team.setVoiceChannel(teamVoiceChannel);
        }

        // 3.5 옮기기전까지 타이머 재기
        Message message = null;
        for(int i = 10; i > 0; i--){
            MessageEmbed timerMessage = CommandFoam.getInstance().showTimerMessage(i);

            if(i == 10){
                message = messageChannel.sendMessage(timerMessage).complete();
            }else{
                messageChannel.editMessageById(message.getId(), timerMessage).complete();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 4. 팀별 통화방에 참여자들을 옮기기
        MessageEmbed moveToTeamMessage = CommandFoam.getInstance().moveToTeamMessage();
        messageChannel.editMessageById(message.getId(), moveToTeamMessage).complete();

        for(Team team : teamList){
            for (Member member : team.getMembers()){
                guild.getController().moveVoiceMember(member, team.getVoiceChannel()).complete();
            }
        }

        //5. 팀을 저장하기
        TeamService.getInstance().register(teamList, guild.getId());
    }

    private List<Team> makeTeam(List<Member> members) {
        // 참여자들을 섞어주기
        long seed = System.nanoTime();
        Collections.shuffle(members, new Random(seed));

        // 배그 스쿼드를 기준으로 팀을 나누기
        int less = members.size() % 4;
        if(less > 0){
            less = 1;
        }

        int teamCount = (members.size() / 4) + less;
        List<Team> teamList = new ArrayList<>();

        for (int i = 0; i < teamCount; i++){
            teamList.add(new Team());
        }

        for (int i = 0; i < members.size(); i++){
            Member member = members.get(i);
            int teamIndex = (teamCount + i) % teamCount;
            Team team = teamList.get(teamIndex);
            team.getMembers().add(member);
        }

        // 팀이름을 제일 첫번째 인사람인 이름팀으로 설정
        for(Team team: teamList){
            Member firstMember = team.getMembers().get(0);
            team.setTeamName(firstMember.getUser().getName() + " 팀");
        }

        return teamList;
    }

    @Nullable
    private VoiceChannel getMatchVoiceChannel(Guild guild){
        List<VoiceChannel> voiceChannels = guild.getVoiceChannelsByName("match", false);

        // 만약 match채널이 없을경우
        if(voiceChannels.isEmpty()){
            return null;
        }
        return voiceChannels.get(0);
    }
}
