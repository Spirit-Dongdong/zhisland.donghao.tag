package com.util;

import org.ansj.lucene.util.AnsjTokenizer;
import org.ansj.lucene4.AnsjAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.*;


public class AnsjUtil {

	public static final Set<String> filter = new HashSet<>();

	public static Map<String, IndexReader> readerMap = new HashMap<String, IndexReader>();
	private static final AnsjAnalysis ANALYZER = new AnsjAnalysis();
//	private static final Analysis ANALYSIS = new AnsjAnalysis();
	static {
//		String indexDir = "es/index";
//		File[] indexFolders = new File(indexDir).listFiles();
//		try {
//			for (File indexFolder : indexFolders) {
//				String domain = indexFolder.getName();
//				IndexReader reader = DirectoryReader.open(FSDirectory.open(indexFolder));
//				readerMap.put(domain, reader);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// stopwords
		String[] lines = TxtUtil.getFileContent("data/stopwords.txt").split("\n");
		for (String line : lines) {
			filter.add(line.trim());
		}
		
	}


	
	public static void test(Directory dir) {
		try {
			
			DirectoryReader reader = DirectoryReader.open(dir);
			System.out.println(reader.numDocs());
			
			Fields fields = MultiFields.getFields(reader);
			System.out.println(fields.size());

			for (String string : fields) {
				System.out.println(string);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static List<String> analyze(String str) {
		List<String> words = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		try {
			AnsjTokenizer tokenizer = new AnsjTokenizer(new ToAnalysis(new StringReader(str)), new StringReader(str), filter, false);
//			TokenStream ts = ANALYZER.tokenStream("", str);
//			ts.clearAttributes();
			
//			AnsjTokenizer tokenizer = new AnsjTokenizer(ANALYZER, new StringReader(str), null, false);
			while (tokenizer.incrementToken()) {
//				System.out.println(ts.getAttribute(CharTermAttribute.class));
//				words.add(ts.getAttribute(CharTermAttribute.class).toString());
				words.add(tokenizer.addAttribute(CharTermAttribute.class).toString());
			}
//			ts.reset();
//			ts.close();
//			ts.clearAttributes();
			return words;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return words;
	}

	public static String segment(String str) {
		StringBuilder sb = new StringBuilder();
		try {
			AnsjTokenizer tokenizer = new AnsjTokenizer(new ToAnalysis(new StringReader(str)), new StringReader(str), filter, false);
//			TokenStream ts = ANALYZER.tokenStream("", str);
//			ts.clearAttributes();

//			AnsjTokenizer tokenizer = new AnsjTokenizer(ANALYZER, new StringReader(str), null, false);
			while (tokenizer.incrementToken()) {
//				System.out.println(ts.getAttribute(CharTermAttribute.class));
//				words.add(ts.getAttribute(CharTermAttribute.class).toString());
//				words.add(tokenizer.addAttribute(CharTermAttribute.class).toString());
				String term = tokenizer.addAttribute(CharTermAttribute.class).toString();
				if (!term.trim().isEmpty()) {
                    if(term.contains("o2o") || term.contains("360"))
					    sb.append(term).append(' ');
                    else if (!term.matches(".*\\d+.*"))
                        sb.append(term).append(' ');
				}
			}
//			ts.reset();
//			ts.close();
//			ts.clearAttributes();
//			return words;
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
//		return words;
		return sb.toString();
	}

	public static void getTFIDF(String page, String v, String domain) {
		IndexReader reader = readerMap.get(domain);
		long numDocs = reader.numDocs();
		try {
			List<String> words = analyze(v);
			for (String word : words) {
				Term term = new Term(page, word);
				long sumDocFreq = reader.getSumDocFreq(word);
				long docFreq = reader.docFreq(term);
				long totalTermFreq = reader.totalTermFreq(term);
//				TermsEnum termsEnum = 
				//sumTotalTermFreq()	returns the total number of tokens for this field
				//sumDocFreq			returns the total number of postings for this field
				//totalTermFreq			Returns the total number of occurrences of term across all documents (the sum of the freq() for each doc that has this term).
				//getSumTotalTermFreq	Returns the sum of TermsEnum.totalTermFreq() for all terms in this field, or -1 if this measure isn't stored by the codec (or if this fields omits term freq and positions).
				//getSumDocFreq			Returns the sum of TermsEnum.docFreq() for all terms in this field, or -1 if this measure isn't stored by the codec.
				//getDocCount			Returns the number of documents that have at least one term for this field, or -1 if this measure isn't stored by the codec.
				System.out.println(word);
				System.out.println("sumDocFreq:" + sumDocFreq);
				System.out.println("docFreq:" + docFreq);
				System.out.println("totalTermFreq:" + totalTermFreq);
				
//				double tfidf = Math.sqrt(totalTermFreq) / docFreq;
				double tfidf = Math.sqrt(totalTermFreq) * Math.log(numDocs / (docFreq + 1));
				System.out.println(tfidf);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double avgTFIDF(String page, String v, String domain) {
		IndexReader reader = readerMap.get(domain);
		long numDocs = reader.numDocs();
		int count = 0;
		double totalTfidf = 0;
		try {
			List<String> words = analyze(v);
			for (String word : words) {
				count++;
				Term term = new Term(page, word);
				long sumDocFreq = reader.getSumDocFreq(word);
				long docFreq = reader.docFreq(term);
				long totalTermFreq = reader.totalTermFreq(term);
//				TermsEnum termsEnum = 
				//sumTotalTermFreq()	returns the total number of tokens for this field
				//sumDocFreq			returns the total number of postings for this field
				//totalTermFreq			Returns the total number of occurrences of term across all documents (the sum of the freq() for each doc that has this term).
				//getSumTotalTermFreq	Returns the sum of TermsEnum.totalTermFreq() for all terms in this field, or -1 if this measure isn't stored by the codec (or if this fields omits term freq and positions).
				//getSumDocFreq			Returns the sum of TermsEnum.docFreq() for all terms in this field, or -1 if this measure isn't stored by the codec.
				//getDocCount			Returns the number of documents that have at least one term for this field, or -1 if this measure isn't stored by the codec.
//				System.out.println(word);
//				System.out.println("sumDocFreq:" + sumDocFreq);
//				System.out.println("docFreq:" + docFreq);
//				System.out.println("totalTermFreq:" + totalTermFreq);
				
				double tfidf = Math.sqrt(totalTermFreq) * Math.log(numDocs / (docFreq + 1));
				totalTfidf += tfidf;
//				System.out.println(tfidf);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (count == 0) {
			return 0;
		} else {
			return totalTfidf/count;
		}
	}
	
	public static Directory buildIndex(String dir, String file, String domain, String page) {
		try {
			FSDirectory indexDir = FSDirectory.open(new File(dir));
			Analyzer analyzer = new AnsjAnalysis();
//			analyzer = new analy
			IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
			config.setOpenMode(OpenMode.CREATE);
			IndexWriter indexWriter = new IndexWriter(indexDir, config);
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				String[] segs = line.split("\t");
				String d = segs[0];
				String p = segs[1];
				String v = segs[2];
				
				
				if (v.isEmpty() || v.equals("\\N")) {
					continue;
				}
				Field field = new Field(p, v, Field.Store.NO, Field.Index.ANALYZED);
				Document doc = new Document();
				doc.add(field);
				indexWriter.addDocument(doc);
			}
			
			indexWriter.commit();
			indexWriter.close();
			return indexDir;
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		
		
		return null;
	}
	


    public static void test_read() {
        ClassLoader loader = AnsjUtil.class.getClassLoader();
        File file = new File(loader.getResource("data/stopwords.txt").getFile());
        System.out.println(file.length());
    }

	
	public static void main(String[] args) throws IOException {
//		String liepinIndex = "es/index/lpt.liepin.com2";
//		Directory dir = buildIndex(liepinIndex, "es/output/lpt.liepin.com", "", "");
//		FSDirectory indexDir = FSDirectory.open(new File("es/index/lpt.liepin.com"));
		
//		test(indexDir);

        test_read();

		System.out.println("classpath路径:" + AnsjUtil.class.getClassLoader().getResource("").getPath());
		System.out.println("当前类加载路径:" + AnsjUtil.class.getResource("").getPath());

//
//		String a = segment("O2O是为了便于区别一个特定商业模式而取的约定名称符号");
//		System.out.println(a);
//		System.out.println(analyze("你真的能确定你爱它吗"));
//		System.out.println(analyze("你真的能确定你爱它吗"));
//		System.out.println(analyze("匆匆那年"));
//		getTFIDF("/resume/showresumedetail", "下载联系方式", "lpt.liepin.com2");
	}
}
