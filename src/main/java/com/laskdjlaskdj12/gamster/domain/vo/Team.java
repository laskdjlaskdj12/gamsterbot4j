package com.laskdjlaskdj12.gamster.domain.vo;

import lombok.Data;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.List;

@Data
public class Team {
    private String teamName;
    private List<Member> members = new ArrayList<>();
    private VoiceChannel voiceChannel;
}
