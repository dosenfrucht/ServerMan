package net.demus_intergalactical.serverman;

import net.demus_intergalactical.serverman.instance.ServerInstanceSettings;

import java.io.IOException;

public class Globals {

	private static ServerManConfig serverManConfig;
	private static ServerInstanceSettings instanceSettings;

	public synchronized static void init() {
		serverManConfig = new ServerManConfig();
		try {
			serverManConfig.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		instanceSettings = new ServerInstanceSettings();
		try {
			instanceSettings.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public synchronized static
	ServerManConfig getServerManConfig() {
		return serverManConfig;
	}

	public synchronized static
	ServerInstanceSettings getInstanceSettings() {
		return instanceSettings;
	}

	public synchronized static
	void setInstanceSettings(ServerInstanceSettings instanceSettings) {
		Globals.instanceSettings = instanceSettings;
	}

	public synchronized static
	void setServerManConfig(ServerManConfig serverManConfig) {
		Globals.serverManConfig = serverManConfig;
	}
}
