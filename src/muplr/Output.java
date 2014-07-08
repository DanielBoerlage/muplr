package muplr;

import org.fusesource.jansi.AnsiConsole;

public class Output {

	private final static String ESC = "\033[";
	private final static String TEXT_RESET = ESC + "0m";

	//private final static Dimension dimension = new Dimension(80, 20);

	public static void print(String str) {
		AnsiConsole.out.print(str);
	}

	public static void clear() {
		print(ESC + "2J" + ESC + "f");
	}

	public static void puts(String str) {
		print(str + "\n");
	}

	public static void printErr(String str) {
		puts(textAttr(str, 31, 1));  // red, bold
	}

	public static void printHeader(String firstText, String secondText, int width) {
		puts(textAttr(firstText + Utils.nChars(' ', width - firstText.length() - secondText.length()) + secondText, 30, 47));  // white background, black foreground
	}

	private static String textAttr(String str, int... codes) {
		String attr = ESC;
		for(int code : codes)
			attr += code + ";";
		return attr + "m" + str + TEXT_RESET;
	}


}