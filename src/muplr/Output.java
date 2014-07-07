package muplr;

import org.fusesource.jansi.AnsiConsole;

public class Output {

	private final static String ESC = "\033[";

	public final static String CLEAR = ESC + "2J";

	public static void print(String str, int... textAttr) {
		boolean attrsPresent = textAttr.length > 0;
		String attrStr = attrsPresent ? ESC : "";
		for(int n : textAttr)
			attrStr += n + ";";
		if(attrsPresent)
			attrStr += "m";
		AnsiConsole.out.println(attrStr + str + (attrsPresent ? ESC + "0m" : ""));
	}

	public static void printErr(String str) {
		print(str, 31, 1);  // red-bold
	}
}