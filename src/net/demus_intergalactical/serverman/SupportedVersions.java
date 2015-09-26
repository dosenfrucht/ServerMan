package net.demus_intergalactical.serverman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by thomas on 26/09/15.
 */
public class SupportedVersions {

	private final String FILE_URL =
		"http://serverman.demus-intergalactical.net/versions";

	private Set<String> versions;

	public SupportedVersions() {
		versions = new TreeSet<>();
	}

	public void load() throws IOException {
		String vs = Utils.download(FILE_URL);
		StringReader vr = new StringReader(vs);
		BufferedReader br = new BufferedReader(vr);
		while (br.ready()) {
			String tmp = br.readLine();
			if (tmp == null) {
				break;
			}
			versions.add(tmp);
		}
		br.close();
		vr.close();
	}

	public Set<String> getVersions() {
		return versions;
	}

}
