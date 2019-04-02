package br.com.pagga.chamado.util;

public class StringUtil {
	
	public static boolean isNullOrEmpty(String s) {
		
		if(s != null && s.replaceAll(" ", "").length() > 0) {
			return false;
		}
		
		return true;
	}
	
}
