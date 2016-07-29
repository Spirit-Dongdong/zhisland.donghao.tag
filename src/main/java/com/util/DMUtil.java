package com.util;

public class DMUtil {

	public static String[] getAllNGrams(String str, int n) {
		if (str.length() < n) {//error
			return null;
		}
		String[] result;
		if (str.length() == n) {
			result = new String[1];
			result[0] = str;
		} else {
			result = new String[str.length() - n + 1];
			for (int i = 0; i < str.length() - n + 1; i++) {
				String ngram = str.substring(i, i+n);
				result[i] = ngram;
//				System.out.println(ngram);
			}
		}
		return result;

		
		
		
	}
	
	
	public static void main(String[] args) {
		getAllNGrams("abcdefg", 2);
	}
}
