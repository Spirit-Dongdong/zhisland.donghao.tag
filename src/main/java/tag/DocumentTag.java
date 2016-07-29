package tag;

import com.lda.LDAPredictor;
import com.lda.Model;
import com.util.AnalyzerUtil;
import com.util.ConfigLoader;
import com.util.TxtUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

/**
 * Created by Spirit on 16/6/29.
 */
public class DocumentTag {
    public static Map<Integer, String> idx2topic = new HashMap<Integer, String>();
    private static LDAPredictor predictor;

    private static Connection conn;

    public static void init(String modelPath, String modelName) throws Exception {
        String[] topicNames = TxtUtil.getFileContent(modelPath + "/topic_name.txt").split("\n");
        for (String line : topicNames) {
            int idx = Integer.parseInt(line.split(":")[0]);
            String name = line.split(":")[1];
            idx2topic.put(idx, name);
        }

        predictor = new LDAPredictor(modelPath, modelName);

        String cmsUrl="jdbc:mysql://192.168.2.101:4006/zh_bms_cms?user=fangdonghao&password=fAKi_UlkHRO.HTHH&useUnicode=true&characterEncoding=UTF-8";
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(cmsUrl);



    }

    public static void tag(String path, int maxWords, double threshold) {
        File[] files = new File(path).listFiles();
        for (File f : files) {
            String content = TxtUtil.getFileContent(f);
            String segment_content = AnalyzerUtil.getAnalyzeResult(content, " ");
            List<String> words = new ArrayList<String>();
            int wordCnt = 0;
            StringBuffer output = new StringBuffer(f.getName().split("\\.")[0] + " ");

            String id = f.getName().split("\\.")[0];
            StringBuffer wordsBuffer = new StringBuffer();

            Model model = predictor.inference(segment_content);
            double[] dist = model.theta[0];

            TreeMap<Double, Integer> topicWeightTreeMap = new TreeMap<Double, Integer>();
            HashMap<Integer, Double> topicWeight = new HashMap<>();
            for (int i = 0; i < dist.length; i++) {
                topicWeightTreeMap.put(dist[i], i);
                topicWeight.put(i, dist[i]);
            }

            ArrayList<Integer> indices = new ArrayList<>(topicWeightTreeMap.values());
            for (int i = indices.size() - 1; i>=0; i--) {
                if (wordCnt >= maxWords)
                    break;
                int idx = indices.get(i);
                String word = idx2topic.get(idx);
                double weight = topicWeight.get(idx);

                if (!word.equals("无意义") && weight >= threshold) {
                    output.append(word).append(',');
                    wordsBuffer.append(word).append(',');
                    wordCnt++;
                }


            }
            output.deleteCharAt(output.length() - 1);
            System.out.println(output);

            try {
                wordsBuffer.deleteCharAt(wordsBuffer.length() - 1);
                Statement stmt = conn.createStatement();
                String sql = "update tb_news_resource set tags='" + wordsBuffer.toString() + "' where id = " + id;
                int result = stmt.executeUpdate(sql);
                System.out.println(id);

            } catch (Exception e) {
                System.out.println(e);
                continue;
            }
        }
        try {
            conn.close();
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws Exception {
//        if (args.length < 2) {
//            System.out.println("Usage: java -jar xxx.jar configPath documentPath");
//            System.exit(1);
//        }
//        String configPath = args[0];
//        String configPath = "config";
//        String documentPath = "/Users/Spirit/PycharmProjects/python-crawler/tag/news";
//        ConfigLoader loader = new ConfigLoader(configPath);
//        String modelPath = loader.getString("lda_path");
//        String modelName = loader.getString("lda_modelName");
//        System.out.println(modelPath + "   " + modelName);
//        init(modelPath, modelName);
//
//        int maxWords = loader.getInt("lda_max_words");
//        double threshold = loader.getDouble("lda_threshold");
//
//        tag(documentPath, maxWords, threshold);



    }

}
