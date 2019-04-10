package com.laskdjlaskdj12.gamster.service;

import com.laskdjlaskdj12.gamster.domain.vo.Team;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamService {
    private Map<String, List<Team>> teamInfo = new HashMap();
    private static TeamService teamService;
    private TeamService(){}

    public static TeamService getInstance(){
        if (teamService == null){
            teamService = new TeamService();
        }

        return teamService;
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
