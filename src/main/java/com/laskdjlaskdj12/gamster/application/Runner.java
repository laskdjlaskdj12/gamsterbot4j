package com.laskdjlaskdj12.gamster.application;

import com.laskdjlaskdj12.gamster.command.HelpCommand;
import com.laskdjlaskdj12.gamster.command.StartTeamMakingCommand;
import com.laskdjlaskdj12.gamster.service.ConfigService;
import de.btobastian.sdcf4j.CommandHandler;
import de.btobastian.sdcf4j.handler.JDA3Handler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.logging.Logger;

public class Runner {
	public final static Logger logger = Logger.getGlobal();

	public void run() {
		logger.info("Config 파일을 불러오고 있습니다...");
		ConfigService.getInstance().loadConfig();

		logger.info("Discord 토큰을 불러오는 중입니다...");
		String token = ConfigService.getInstance().getConfig("token");
		if(token == null) {
			logger.warning("Discord 토큰을 못불러왔습니다. 프로그램을 종료합니다.");
			return;
		}

		JDA jda;
		try {
			logger.info("디스코드봇을 초기화하는중...");
			jda = new JDABuilder(AccountType.BOT).setAudioEnabled(true)
					.setToken(token)
					.buildAsync();
		} catch (LoginException e) {
			logger.info("디스코드 봇을 초기화 하는데 에러가 발생했습니다.");
			e.printStackTrace();
			return;
		}

		logger.info("명령어 핸들러를 초기화 하는중....");
		registerHandler(jda);

		logger.info("디스코드 봇 초기화를 완료했습니다..");
	}

	private void registerHandler(JDA jda) {
		CommandHandler commandHandler = new JDA3Handler(jda);
		commandHandler.registerCommand(new HelpCommand());
		commandHandler.registerCommand(new StartTeamMakingCommand());
	}
}
