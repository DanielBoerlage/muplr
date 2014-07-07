package muplr;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

	public static String joinPath(String[] strs){
		return joinTrailingPath(strs, 0);
	}

	public static String joinTrailingPath(String[] strs, int index) {
		if(index == strs.length)
			return "/*";
		String accum = "";
		for(int i = index; i < strs.length; i++)
			accum += strs[i] + "/";
		return "/" + accum.substring(0,accum.length()-1);
	}

	public static Path getPathTail(Path path, int numNames) {
		return Paths.get("/").resolve(path.subpath(path.getNameCount() - numNames, path.getNameCount()));
	}

	public static boolean isAbsolute(String path) {
		if(Main.os.startsWith("Windows"))
			return path.substring(0, 1).matches("[/\\\\]") 
					|| path.length() > 2 && path.substring(0, 3).matches(".:[/\\\\]");
		else
			return path.startsWith("/");
	}
}