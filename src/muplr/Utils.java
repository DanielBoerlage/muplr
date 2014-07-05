package muplr;

public class Utils {

	public static String joinPath(String[] strs){
		String accum = "";
		for(String str : strs)
			accum += str + "/";
		return accum;
	}
}