package muplr;

public class Utils {

	public static String joinPath(String[] strs){
		String accum = "";
		for(String str : strs)
			accum += str + "/";
		return accum;
	}

	public static boolean isAbsolute(String path) {
		if(Main.os.startsWith("Windows"))
			return path.substring(0, 1).matches("[/\\\\]") 
					|| path.length() > 2 && path.substring(0, 3).matches(".:[/\\\\]");
		else
			return path.startsWith("/");
	}
}