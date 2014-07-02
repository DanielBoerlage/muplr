package muplr;

public class Main {

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