package net.demus_intergalactical.serverman;

import net.demus_intergalactical.serverman.instance.ServerInstanceSettings;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Globals {

	private static ServerManConfig serverManConfig;
	private static ServerInstanceSettings instanceSettings;
	private static AvailableServers availableServers;
	private static SupportedVersions supportedVersions;

	public synchronized static void init()
			throws IOException, ParseException {
		serverManConfig = new ServerManConfig();
		instanceSettings = new ServerInstanceSettings();
		availableServers = new AvailableServers();
		supportedVersions = new SupportedVersions();
		serverManConfig.load();
		instanceSettings.load();
		availableServers.load();
		supportedVersions.load();
	}

	public synchronized static
	ServerManConfig getServerManConfig() {
		return serverManConfig;
	}

	public synchronized static
	ServerInstanceSettings getInstanceSettings() {
		return instanceSettings;
	}

	public synchronized static AvailableServers getAvailableServers() {
		return availableServers;
	}

	public synchronized static SupportedVersions getSupportedVersions() {
		return supportedVersions;
	}
}
