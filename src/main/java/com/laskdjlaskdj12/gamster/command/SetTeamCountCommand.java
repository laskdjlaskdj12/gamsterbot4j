package com.laskdjlaskdj12.gamster.command;

import com.laskdjlaskdj12.gamster.domain.foam.CommandFoam;
import com.laskdjlaskdj12.gamster.domain.vo.GuildInfo;
import com.laskdjlaskdj12.gamster.service.GuildInfoService;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class SetTeamCountCommand implements CommandExecutor {

	@Command(aliases = "/팀최대인원", description = "한팀당 최대 인원을 설정합니다.")
	public void setMaximumTeamCount(String[] args, Guild guild, MessageChannel messageChannel){
		int count;
		try {
			count = Integer.valueOf(args[0]);
		}catch(NumberFormatException e){
			MessageEmbed messageEmbed = CommandFoam.getInstance().wrongSetMaximumTeamCount();
			messageChannel.sendMessage(messageEmbed).queue();
			return;
		}

		//Todo: 최대 인원수를 Guild 정보 안에 저장함
		GuildInfo guildInfo = GuildInfoService.getInstance().getGuildInfo(guild.getId());
		if(guildInfo == null){
			//새로운 guildInfo를 만듬
			guildInfo = new GuildInfo();
		}

		guildInfo.setMaximumTeamCount(count);
		GuildInfoService.getInstance().setGuildInfo(guild.getId(), guildInfo);

		MessageEmbed setMaximumTeamCountMessage = CommandFoam.getInstance().setMaximumTeamCount(count);
		messageChannel.sendMessage(setMaximumTeamCountMessage).queue();
	}
}
