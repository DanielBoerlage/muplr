package muplr;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public final static Path WORKING_DIRECTORY = Paths.get(System.getProperty("user.dir"));
	public final static String OS = System.getProperty("os.name");

	public static void main(String[] args) {
		Input.parseArgs(args);
	}

	public static void exitSuccess() {
		System.exit(0);
	}

	public static void fatalError(String errMsg) {
		error(errMsg);
		System.exit(-1);
	}

	public static void error(String errMsg) {
		Output.printErr(errMsg);
	}
}