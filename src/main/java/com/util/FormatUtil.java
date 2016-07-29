package com.util;

public class FormatUtil {

	public static String delHTMLTag(String content) {
//		content = content.replaceAll("<p .*?>", "");
//		content = content.replaceAll("<br\\s*/?>", "");
		content = content.replaceAll("\\<.*?>", "");
		content = content.replaceAll("&nbsp;", "");
		content = content.replaceAll(" +", "");
		content = content.replaceAll("&ldquo;", "");
		content = content.replaceAll("&rdquo;", "");
		return content;
	}
}
