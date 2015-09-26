import net.demus_intergalactical.serverman.Globals;
import net.demus_intergalactical.serverman.PlayerHandler;
import net.demus_intergalactical.serverman.instance.ServerInstance;
import net.demus_intergalactical.serverman.instance.ServerInstanceProcess;
import org.json.simple.parser.ParseException;

import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ServerMan {


	public static void main(String[] args) throws IOException,
		InterruptedException, NoSuchMethodException,
		ScriptException, ParseException {
		Globals.init();



		ServerInstance i = new ServerInstance();
		i.setServerInstanceID("vanilla_1.8");
		i.setOut((type, time, thread, infoLvl, arg) -> {
			System.out.println(arg);
		});
		i.setPlayerHandler(new PlayerHandler() {
			@Override
			public void onPlayerJoined(String name) {
				System.out.println("Player " + name
					+ " joined");
				System.out.println(i.getProcess().getPlayers()
					.size());
			}

			@Override
			public void onPlayerLeft(String name) {
				System.out.println("Player " + name + " left");
				System.out.println(i.getProcess().getPlayers()
					.size());
			}
		});

		i.load();
		i.run();


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
