package com.laskdjlaskdj12.gamster.service;

import com.laskdjlaskdj12.gamster.domain.vo.Team;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamInfoService {
    private Map<String, List<Team>> teamInfo = new HashMap();
    private static TeamInfoService teamInfoService;
    private TeamInfoService(){}

    public static TeamInfoService getInstance(){
        if (teamInfoService == null){
            teamInfoService = new TeamInfoService();
        }

        return teamInfoService;
    }

    public void register(List<Team> teamList, String guildID){
        teamInfo.put(guildID, teamList);
    }

    @Nullable
    public List<Team> getTeamList(String guildID){
        return teamInfo.get(guildID);
    }

    public void removeTeam(String id) {
        teamInfo.remove(id);
    }
}
