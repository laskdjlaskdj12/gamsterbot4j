package com.laskdjlaskdj12.gamster.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class GuildInfo {

	private List<Team> teamList;

	private int maximumTeamCount;
}
