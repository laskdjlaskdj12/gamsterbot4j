package com.laskdjlaskdj12.gamster.domain.foam;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class CommandFoam {
    private static CommandFoam commandFoam;

    private CommandFoam(){}

    public static CommandFoam getInstance(){
        if (commandFoam == null){
            commandFoam = new CommandFoam();
        }
        return commandFoam;
    }

    public MessageEmbed allCommandList(){
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("명령어 리스트");
        embedBuilder.addField("", makeAllCommandList(), false);
        return embedBuilder.build();
    }
    private String makeAllCommandList(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/help - 모든 명령어들을 보여줍니다. \n");
        stringBuilder.append("/내전시작 - 대기방에 있는 참가자들을 랜덤으로 팀을 만들고 통화방으로 이동시킵니다.\n");
        stringBuilder.append("/끝 - 내전이 끝났습니다. 팀을 해체하고 참가자들을 대기방으로 옮깁니다.\n");
        return stringBuilder.toString();
    }
}
