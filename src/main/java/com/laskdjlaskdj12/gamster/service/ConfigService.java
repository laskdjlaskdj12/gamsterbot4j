package com.laskdjlaskdj12.gamster.service;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class ConfigService {

	private static ConfigService configService;
	private HashMap config = new HashMap<>();

	private ConfigService() {
	}

	public static ConfigService getInstance() {
		if (configService == null) {
			configService = new ConfigService();
		}

		return configService;
	}

	@Nullable
	public void loadConfig() {
		try {
			String jsonContent = readConfigFile();
			config = new Gson().fromJson(jsonContent, HashMap.class);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Nullable
	private String readConfigFile() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File propertyFile = new File(classLoader.getResource("property").getFile());
		return FileUtils.readFileToString(propertyFile);
	}

	@Nullable
	public String getConfig(String key) {
		return config.get(key).toString();
	}
}
