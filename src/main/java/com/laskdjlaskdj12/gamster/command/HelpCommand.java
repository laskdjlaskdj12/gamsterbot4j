package com.laskdjlaskdj12.gamster.command;

import com.laskdjlaskdj12.gamster.domain.foam.CommandFoam;
import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class HelpCommand implements CommandExecutor {
	@Command(aliases = {"/help"}, description = "내전봇의 모든 명령어입니다.")
	public void showHelpMessage(MessageChannel channel){
		MessageEmbed allCommandMessage = CommandFoam.getInstance().allCommandList();
		channel.sendMessage(allCommandMessage).queue();
	}
}
