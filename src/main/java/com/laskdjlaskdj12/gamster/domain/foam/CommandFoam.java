package com.laskdjlaskdj12.gamster.domain.foam;

import com.laskdjlaskdj12.gamster.domain.vo.Team;
import com.sun.javafx.scene.control.skin.EmbeddedTextContextMenuContent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandFoam {
    private static CommandFoam commandFoam;

    private CommandFoam() {
    }

    public static CommandFoam getInstance() {
        if (commandFoam == null) {
            commandFoam = new CommandFoam();
        }
        return commandFoam;
    }

    public MessageEmbed allCommandList() {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("명령어 리스트");
        embedBuilder.addField("", makeAllCommandList(), false);
        embedBuilder.setColor(Color.ORANGE);
        return embedBuilder.build();
    }

    private String makeAllCommandList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/help - 모든 명령어들을 보여줍니다. \n");
        stringBuilder.append("/팀최대인원 [인원수] - 내전때 팀당 최대인원을 설정합니다.\n");
        stringBuilder.append("/내전시작 - 대기방에 있는 참가자들을 랜덤으로 팀을 만들고 통화방으로 이동시킵니다.\n");
        stringBuilder.append("/끝 - 내전이 끝났습니다. 팀을 해체하고 참가자들을 대기방으로 옮깁니다.\n");
        stringBuilder.append("/편나누기 - 모든 참가자들을 두편으로 나눕니다.");
        return stringBuilder.toString();
    }

    public MessageEmbed noPrivateMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("경고");
        embedBuilder.setDescription("개인톡으로는 이 명령어가 실행되지 않습니다.");
        embedBuilder.setColor(Color.RED);
        return embedBuilder.build();
    }

    public MessageEmbed noVoiceChannelMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("참가자 대기 채널인 match 음성채팅방을 찾을수가 없습니다.");
        embedBuilder.setColor(Color.RED);
        return embedBuilder.build();
    }

    public MessageEmbed noMembersFound() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("대기중인 참가자들을 찾을수없습니다. 내전을 중단합니다.");
        embedBuilder.setColor(Color.RED);
        return embedBuilder.build();
    }

    public MessageEmbed scanMembersMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("참가자들을 스캔합니다...");
        embedBuilder.setColor(Color.GREEN);
        return embedBuilder.build();
    }

    public MessageEmbed scanedMembersMessage(List<Member> members) {
        String membersList = getMembersList(members);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("인식된 참가자");
        embedBuilder.setDescription(membersList);
        embedBuilder.setColor(Color.BLUE);
        return embedBuilder.build();
    }

    private String getMembersList(List<Member> members) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Member member : members) {
            stringBuilder.append(member.getUser().getName() + "\n");
        }
        return stringBuilder.toString();
    }

    public MessageEmbed makeTeamMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("랜덤으로 팀을 만듭니다..");
        embedBuilder.setColor(Color.BLUE);
        return embedBuilder.build();
    }

    public List<MessageEmbed> showTeamMessage(List<Team> teamList) {
        List<Color> colors = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.ORANGE);

        int index = 0;
        List<MessageEmbed> teamMessageList = new ArrayList<>();
        for (Team team : teamList){

            int colorIndex = (colors.size() + index) % colors.size();
            Color color = colors.get(colorIndex);

            String teamMember = makeTeamMemberString(team.getMembers());
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.addField(team.getTeamName(), teamMember, false);
            embedBuilder.setColor(color);
            teamMessageList.add(embedBuilder.build());
        }

        return teamMessageList;
    }

    private String makeTeamMemberString(List<Member> members){
        StringBuilder stringBuilder = new StringBuilder();

        for(Member member: members) {
            stringBuilder.append(member.getUser().getName() + "\n");
        }
        return stringBuilder.toString();
    }

    public MessageEmbed showTimerMessage(int i) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("팀통화방으로 옮기기 " + i + "초 전....");
        embedBuilder.setColor(Color.GREEN);
        return embedBuilder.build();
    }

    public MessageEmbed moveToTeamMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("각자 팀 통화방으로 이동합니다.");
        embedBuilder.setColor(Color.BLUE);
        return embedBuilder.build();
    }

    public MessageEmbed noTeamMake() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("팀이 만들어지지 않았습니다.");
        embedBuilder.setColor(Color.RED);
        return embedBuilder.build();
    }

    public MessageEmbed noMatchChannelMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("참가자 대기방인 match 채널을 찾을수 없습니다.");
        embedBuilder.setColor(Color.RED);
        return embedBuilder.build();
    }

    public MessageEmbed moveToMatchChannelMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("내전이 끝났습니다. 모든 참가자들을 대기방으로 이동합니다.");
        embedBuilder.setColor(Color.GREEN);
        return embedBuilder.build();
    }

    public MessageEmbed wrongSetMaximumTeamCount() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("잘못된 명령어입니다. : /팀최대인원 [인원수] ");
        embedBuilder.setColor(Color.RED);
        return embedBuilder.build();
    }

    public MessageEmbed setMaximumTeamCount(int count) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("한팀탄 최대 인원을 " + count + "명 으로 설정완료했습니다.");
        embedBuilder.setColor(Color.GREEN);
        return embedBuilder.build();
    }

    public MessageEmbed needMaximumTeamCount() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("한팀당 최대 인원을 설정 안하셨습니다. /팀최대인원 명령어로 한팀당 최대 인원을 설정해주세요.");
        embedBuilder.setColor(Color.RED);
        return embedBuilder.build();
    }

    public MessageEmbed makeSideTeamMessage() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("참가자들을 두편으로 나눕니다.");
        embedBuilder.setColor(Color.BLUE);
        return embedBuilder.build();
    }

    public MessageEmbed noMakeSideMembers() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("참가자가 한명이여서 편을 나눌수가 없습니다.");
        embedBuilder.setColor(Color.RED);
        return embedBuilder.build();
    }
}
