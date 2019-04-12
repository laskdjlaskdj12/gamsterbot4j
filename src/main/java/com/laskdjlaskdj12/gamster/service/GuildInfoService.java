package com.laskdjlaskdj12.gamster.service;

import com.laskdjlaskdj12.gamster.domain.vo.GuildInfo;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class GuildInfoService {

	private static GuildInfoService guildInfoService;

	private GuildInfoService(){}

	private Map<String, GuildInfo> guildInfoMap = new HashMap<>();

	public static GuildInfoService getInstance(){
		if(guildInfoService == null){
			guildInfoService = new GuildInfoService();
		}

		return guildInfoService;
	}

	@Nullable
	public GuildInfo getGuildInfo(String guildID){
		return guildInfoMap.get(guildID);
	}

	public void setGuildInfo(String guildID, GuildInfo guildInfo){
		guildInfoMap.put(guildID, guildInfo);
	}
}
