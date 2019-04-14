package com.laskdjlaskdj12.gamster.Service;

import com.laskdjlaskdj12.gamster.domain.vo.Team;
import com.laskdjlaskdj12.gamster.service.TeamService;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TeamServiceTest {

    private TeamService teamService;

    @Before
    public void before(){
        teamService = new TeamService(null, null);
    }

    /***
     * 반드시 teamService.makeSideTeam 메소드의 messageEmbed를 주석화하고 테스트를 실행해주세요
     */
    @Test
    public void makeSideTeamTest(){
        List<Member> memberListMock = makeMemberMock();
        List<Team> teams = teamService.makeSideTeam(memberListMock);

        boolean is_team_is_two = teams.size() == 2;

        Assert.assertTrue(is_team_is_two);

        for(Team team : teams){
            System.out.println(team.getTeamName());
            for (Member member : team.getMembers()){
                System.out.println(member.getUser().getName());
            }
        }
    }

    private List<Member> makeMemberMock() {
        List<Member> members = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            User user = mock(User.class);
            when(user.getName()).thenReturn("팀원 " + String.valueOf(i));

            Member member = mock(Member.class);
            when(member.getUser()).thenReturn(user);
            members.add(member);
        }

        return members;
    }
}
