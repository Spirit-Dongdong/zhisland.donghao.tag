package com.lda;

import com.util.AnalyzerUtil;
import com.util.TxtUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LDAPredictor {

	private Inferencer inferencer;

	//////输入模型文件地址初始化
	public LDAPredictor(String dir, String modelName) {
		LDAOption option = new LDAOption();
		
		option.dir = dir;
		option.modelName = modelName;
		option.inf = true;
		inferencer = new Inferencer();
		inferencer.init(option);
	}
	
	/////////推断新数据
	public Model inference(String data){
		String [] docs = new String[1];
		docs[0] = data;
		return inferencer.inference(docs);
	}


    public static void tagTbNews() {
        String file = "/Users/Spirit/PycharmProjects/python-crawler/tag/tb_news/18808.txt";
        String content = TxtUtil.getFileContent(file);
        String segment_content = AnalyzerUtil.getAnalyzeResult(content, " ");


        Map<Integer, String> idx2topic = new HashMap<Integer, String>();
        String[] topicNames = TxtUtil.getFileContent("lda-model/topic_name.txt").split("\n");
        for (String line : topicNames) {
            int idx = Integer.parseInt(line.split(":")[0]);
            String name = line.split(":")[1];
            idx2topic.put(idx, name);
        }

        LDAPredictor predictor = new LDAPredictor("lda-model", "model-final");
        Model model = predictor.inference(segment_content);
        double [] dist = model.theta[0];


        TreeMap<Double, Integer> topicWeight = new TreeMap<Double, Integer>();
        for (int i = 0; i < dist.length; i++) {
            topicWeight.put(dist[i], i);
        }

        Map.Entry<Double, Integer> temp = topicWeight.higherEntry(0.0005);

        System.out.println(topicWeight.tailMap(0.001));



        ArrayList<Integer> indices = new ArrayList<Integer>(topicWeight.values());
        System.out.println(indices);
        for (int i = indices.size()-1; i >= 0; i--) {
            int idx = indices.get(i);
            System.out.println(idx2topic.get(idx) + "\t");
        }



    }



	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
//		LDAPredictor predictor = new LDAPredictor("lda-model", "model");
//
//		String input = "金牌 佳能 单反 广角 变焦 红圈 镜头";
//		Model model = predictor.inference(input);
//
//		double [] dist = model.theta[0];
//		for (double d : dist) {
//			System.out.print(d + " ");
//		}

        File f = new File("/Users/Spirit/Documents/workspace/work/lda.txt");
        System.out.println(f.getName().split("\\.")[0]);
//        tagTbNews();

//		
//		LDAPredictor predictor2 = new LDAPredictor("D:/arec/ldaInferencer.model");
//		System.out.println("Inference:");
//		Model model = predictor2.inference("金牌 佳能 单反 广角 变焦 红圈 镜头");
//		
//		double [] dist = model.theta[0];
//		Arrays.sort(dist);
//		for (double d : dist) {
//			System.out.println(d + " ");
//		}

	}
	
	
	
	
	
	
	
	
	
}
