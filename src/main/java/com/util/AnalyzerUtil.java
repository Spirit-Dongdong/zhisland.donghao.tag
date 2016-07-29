package com.util;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;


public class AnalyzerUtil {
	private static final IKAnalyzer ANALYZER = new IKAnalyzer(true);

	private static Configuration cfg;
	private static Dictionary dictionary;

	public static IKAnalyzer getAnalyzer() {

		return ANALYZER;
	}

	public static String getAnalyzeResult(String content, String sep) {
		StringBuilder sb = new StringBuilder();
		try {
			TokenStream ts = ANALYZER.tokenStream("", new StringReader(content));
			ts.reset();
			while (ts.incrementToken()) {
                CharTermAttribute charTermAttribute = ts
		                    .getAttribute(CharTermAttribute.class);

                String term = charTermAttribute.toString().trim();
                if (term.contains("o2o") || term.contains("0后") || term.contains("双11") || !term.matches(".*\\d+.*"))
                    sb.append(term).append(sep);
			}
            ts.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sb.toString();
	}
	
	public static void main(String[] args) {
		IKAnalyzer analyzer = getAnalyzer();
		String content = "IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。";
		String result = getAnalyzeResult(content, " ");
		System.out.println(result);

        content = "加载扩展停止词典";
        result = getAnalyzeResult(content, " ");
        System.out.println(result);

    }
	

}
