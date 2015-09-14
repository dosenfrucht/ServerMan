import net.demus_intergalactical.serverman.Globals;
import net.demus_intergalactical.serverman.OutputHandler;
import net.demus_intergalactical.serverman.instance.ServerInstance;
import net.demus_intergalactical.serverman.instance.ServerInstanceProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ServerMan {


	public static void main(String[] args) throws IOException, InterruptedException {
		Globals.init();
		try {
			Globals.getServerManConfig().load();
			Globals.getInstanceSettings().load();
		} catch (IOException e) {
			e.printStackTrace();
		}



		ServerInstance i = new ServerInstance("vanilla_1.8");
		i.loadInstance();
		i.run(new OutputHandler() {
			@Override
			public void onOutputPlayerJoined(String time, String player) {
				System.out.println(player + " joined");
			}

			@Override
			public void onOutputPlayerLeft(String time, String player) {
				System.out.println(player + " left");
			}

			@Override
			public void onOutputInfo(String time, String text) {
				System.out.println("info: " + text);
			}

			@Override
			public void onOutputWarn(String time, String text) {
				System.out.println("warn: " + text);
			}

			@Override
			public void onOutputErr(String time, String text) {
				System.out.println("error: " + text);
			}

			@Override
			public void onOutput(String time, String text) {
				System.out.println(text);
			}
		});


		BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in)
		);

		ServerInstanceProcess p = i.getProcess();

		boolean running = p.isRunning();
		while (running) {
			if (in.ready()) {
				p.send(in.readLine());
			}
			Thread.sleep(1);
			running = p.isRunning();
		}
	}

}
