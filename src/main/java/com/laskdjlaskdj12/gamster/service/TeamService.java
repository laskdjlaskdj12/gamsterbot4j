package com.laskdjlaskdj12.gamster.service;

import com.laskdjlaskdj12.gamster.domain.foam.CommandFoam;
import com.laskdjlaskdj12.gamster.domain.vo.Team;
import net.dv8tion.jda.core.entities.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamService {

    private Guild guild;
    private MessageChannel messageChannel;

    public TeamService(Guild guild, MessageChannel messageChannel){
        this.guild = guild;
        this.messageChannel = messageChannel;
    }

    public void countDownAndMove(List<Team> sideTeamList, int countDownTime) {
        Message message = null;
        for(int i = countDownTime; i > 0; i--){
            MessageEmbed timerMessage = CommandFoam.getInstance().showTimerMessage(i);

            if(i == countDownTime){
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

        MessageEmbed moveToTeamMessage = CommandFoam.getInstance().moveToTeamMessage();
        messageChannel.editMessageById(message.getId(), moveToTeamMessage).complete();

        for(Team team : sideTeamList){
            for (Member member : team.getMembers()){
                guild.getController().moveVoiceMember(member, team.getVoiceChannel()).complete();
            }
        }
    }

    public List<Team> makeSideTeam(List<Member> inMembers) {
        MessageEmbed messageEmbed = CommandFoam.getInstance().makeSideTeamMessage();
        messageChannel.sendMessage(messageEmbed).queue();

        ArrayList<Member> members = new ArrayList<>(inMembers);

        Collections.shuffle(members);

        //멤버들을 팀리스트에 배분을 하기
        List<Team> teamList = divideMembers(members);

        //팀이름을 정하기
        for(Team team : teamList){
            String teamName = makeTeamName(team);
            team.setTeamName(teamName);
        }
        return teamList;
    }

    private String makeTeamName(Team team) {
        return team.getMembers().get(0).getUser().getName() + "팀";
    }

    private List<Team> divideMembers(List<Member> inMembers) {
        List<Team> teamList = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            teamList.add(new Team());
        }

        //모든 팀원들을 두편으로 배분 시키기
        for(int i = 0; i < inMembers.size(); i++){
            Member member = inMembers.get(i);

            int teamIndex = (teamList.size() + i) % 2;
            Team team = teamList.get(teamIndex);
            team.getMembers().add(member);
        }

        return teamList;
    }
}
