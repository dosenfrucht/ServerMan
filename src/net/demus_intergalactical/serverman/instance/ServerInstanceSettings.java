package net.demus_intergalactical.serverman.instance;

import net.demus_intergalactical.serverman.Globals;
import net.demus_intergalactical.serverman.Utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ServerInstanceSettings {

	private HashMap<String, Object> conf;
	private String path;

	public ServerInstanceSettings() {
		path = Globals.getServerManConfig()
			.get("instances_home") + File.separator
			+ "instances.json";
		conf = new HashMap<>();
	}

	public ServerInstanceSettings load() throws IOException {
		File f = new File(path);
		if (!f.exists()) {
			f.getParentFile().mkdirs();
			f.createNewFile();
			conf = defaultConfig();
			saveConfig(path);
			return this;
		}
		JSONObject confJson = null;
		try {
			confJson = Utils.loadJson(path);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		final JSONObject finalConfJson = confJson;
		confJson.keySet().stream()
			.filter(key -> key instanceof String)
			.forEach(key -> {
				conf.put((String)key, finalConfJson.get(key));
			});
		return this;
	}

	public ServerInstanceSettings saveConfig(String path) {
		JSONObject obj = new JSONObject();
		obj.putAll(conf);
		try {
			FileWriter fw = new FileWriter(path);
			obj.writeJSONString(fw);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	private HashMap<String, Object> defaultConfig() {
		return new HashMap<>();
	}

	public synchronized Object get(String key) {
		return conf.get(key);
	}

}
