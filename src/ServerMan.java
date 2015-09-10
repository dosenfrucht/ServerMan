import net.demus_intergalactical.serverman.Globals;
import net.demus_intergalactical.serverman.instance.ServerInstance;

import java.io.*;

public class ServerMan {


	public static void main(String[] args) {
		Globals.init();
		try {
			Globals.getServerManConfig().load();
			Globals.getInstanceSettings().load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ServerInstance i = new ServerInstance("vanilla_1.8");
		i.loadInstance();
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
