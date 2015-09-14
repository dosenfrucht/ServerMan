package net.demus_intergalactical.serverman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thomas on 14/09/15.
 */
public class ServerOutputMatcher {

	private OutputHandler outputHandler;
	private Pattern joinedPattern;
	private Pattern leftPattern;
	private Pattern errPattern;
	private Pattern warnPattern;
	private Pattern infoPattern;
	private Pattern elsePattern;

	public ServerOutputMatcher(String instanceID, OutputHandler out) {
		outputHandler = out;
		String patFilePath = (String) Globals.getServerManConfig()
			.get("instances_home");
		patFilePath = patFilePath + File.separator + instanceID
			+ File.separator + "_patterns.txt";
		try {
			BufferedReader f = new BufferedReader(
				new FileReader(patFilePath)
			);
			String joinedS = f.readLine();
			String leftS   = f.readLine();
			String errS    = f.readLine();
			String warnS   = f.readLine();
			String infoS   = f.readLine();
			String elseS   = f.readLine();

			/*
			System.out.println(joinedS);
			System.out.println(leftS);
			System.out.println(errS);
			System.out.println(warnS);
			System.out.println(infoS);
			System.out.println(elseS);
			*/

			joinedPattern = Pattern.compile(joinedS);
			leftPattern = Pattern.compile(leftS);
			errPattern = Pattern.compile(errS);
			warnPattern = Pattern.compile(warnS);
			infoPattern = Pattern.compile(infoS);
			elsePattern = Pattern.compile(elseS);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	public void process(String line) {
		Matcher left = leftPattern.matcher(line);
		Matcher joined = joinedPattern.matcher(line);
		Matcher err = errPattern.matcher(line);
		Matcher warn = warnPattern.matcher(line);
		Matcher info = infoPattern.matcher(line);
		Matcher else_ = elsePattern.matcher(line);
		if (joined.matches()) {
			outputHandler.onOutputPlayerJoined(
				joined.group(1),
				joined.group(2)
			);
		} else if (left.matches()){
			outputHandler.onOutputPlayerLeft(
				left.group(1),
				left.group(2)
			);
		} else if (err.matches()) {
			outputHandler.onOutputErr(
				err.group(1),
				err.group(2)
			);
		} else if (warn.matches()) {
			outputHandler.onOutputWarn(
				warn.group(1),
				warn.group(2)
			);
		} else if (info.matches()) {
			outputHandler.onOutputInfo(
				info.group(1),
				info.group(2)
			);
		} else if (else_.matches()) {
			outputHandler.onOutput(
				else_.group(1),
				else_.group(2)
			);
		} else {
			outputHandler.onOutputErr(
				null,
				"Could not parse server output: " + line
			);
		}
	}

}
