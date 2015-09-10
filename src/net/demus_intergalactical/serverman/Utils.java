package net.demus_intergalactical.serverman;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Utils {

	public static String readFile(String filename)
			throws IOException {
		FileReader in = new FileReader(filename);
		StringBuilder contents = new StringBuilder();
		char[] buffer = new char[4096];
		int read = 0;
		do {
			contents.append(buffer, 0, read);
			read = in.read(buffer);
		} while (read >= 0);
		return contents.toString();
	}

	public static JSONObject loadJson(String filename)
			throws IOException, ParseException {
		JSONParser jp = new JSONParser();

		return (JSONObject) jp.parse(new FileReader(filename));
	}

}
