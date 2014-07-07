package muplr;

import org.fusesource.jansi.AnsiConsole;

public class Output {

	private final static String ESC = "\033[";
	private final static String CLEAR = ESC + "2J";
	private final static String TEXT_RESET = ESC + "0m";

	private static void print(String str) {
		AnsiConsole.out.print(str);
	}

	public static void puts(String str) {
		print(str + "\n");
	}

	public static void printErr(String str) {
		puts(textAttr(31, 1) + str + TEXT_RESET);  // red, bold
	}

	private static String textAttr(int... codes) {
		String out = ESC;
		for(int code : codes)
			out += code + ";";
		return out + "m";
	}
}