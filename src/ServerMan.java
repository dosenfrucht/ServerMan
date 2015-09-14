import net.demus_intergalactical.serverman.Globals;
import net.demus_intergalactical.serverman.OutputHandler;
import net.demus_intergalactical.serverman.instance.ServerInstance;
import net.demus_intergalactical.serverman.instance.ServerInstanceProcess;

import java.io.*;

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
				System.out.println("err: " + text);
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

	public static void example() throws IOException, InterruptedException {

		ProcessBuilder pb = new ProcessBuilder();

		File ed = new File("server");
		pb.directory(ed);
		pb.command("java", "-jar",
			"forge-1.7.10-10.13.2.1291-universal.jar", "nogui");
		pb.redirectErrorStream(false);



		Process p = pb.start();
		OutputStream outS = p.getOutputStream();
		InputStream  inS  = p.getInputStream();

		InputStreamReader  inB  = new InputStreamReader(inS);
		OutputStreamWriter outB = new OutputStreamWriter(outS);

		BufferedReader in  = new BufferedReader(inB);
		BufferedWriter out = new BufferedWriter(outB);


		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		String line;

		while (p.isAlive()) {
			if (stdin.ready()) {
				line = stdin.readLine();
				out.write(line);
				out.newLine();
				out.flush();
			}
			if (in.ready()) {
				System.out.print("out: ");
				System.out.println(in.readLine());
				System.out.flush();
			}
		}

		in.close();
		out.close();
		inB.close();
		outB.close();

		p.waitFor();
	}

}
