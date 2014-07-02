package muplr;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public final static Path workingDirectory = Paths.get(System.getProperty("user.dir"));

	public static void main(String[] args) {
		Input.parseArgs(args);
	}

	public static void exit(int code, String message) {
		if(!message.isEmpty()) {
			if(code == 0)
				Output.print(message);
			else
				Output.printErr(message);
		}
		System.exit(code);
	}
}