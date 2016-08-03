package tag;

import com.lda.LDAPredictor;
import com.lda.Model;
import com.util.AnalyzerUtil;
import com.util.ConfigLoader;
import com.util.TxtUtil;

import java.sql.*;
import java.util.*;

/**
 * Created by Spirit on 16/6/29.
 */
public class DocumentTag {
    public static Map<Integer, String> idx2topic = new HashMap<Integer, String>();
    private static LDAPredictor predictor;

    private static Connection conn;

    private static int maxWords;
    private static double threshold;



    public static void init() throws Exception {
        ConfigLoader loader = new ConfigLoader("config");
        String modelPath = loader.getString("lda_path");
        String modelName = loader.getString("lda_modelName");
        String topicPath = modelPath + "/topic_name.txt";
        String[] topicNames = TxtUtil.getFileContent(topicPath).split("\n");
        for (String line : topicNames) {
            int idx = Integer.parseInt(line.split(":")[0]);
            String name = line.split(":")[1];
            idx2topic.put(idx, name);
        }

        predictor = new LDAPredictor(modelPath, modelName);

        String cmsUrl=loader.getString("mysql_url");
        Class.forName("com.mysql.jdbc.Driver");
        String user = loader.getString("mysql_user");
        String passwd = loader.getString("mysql_passwd");
        conn = DriverManager.getConnection(cmsUrl, user, passwd);

        maxWords = loader.getInt("lda_max_words");
        threshold = loader.getDouble("lda_threshold");
    }

    public static void go() throws Exception {
        init();

        Statement stmt = conn.createStatement();
        String sql = "select news_id, content_read from tb_news_content where tags is null";
        ResultSet rs = stmt.executeQuery(sql);

        PreparedStatement sta = null;

        while (rs.next()) {
            int news_id = rs.getInt("news_id");
            String content_read = rs.getString("content_read");
            String tags = tag(content_read);

            sql = "update tb_news_content set tags=? where news_id=?";
            sta = conn.prepareCall(sql);
            sta.setString(1, tags);
            sta.setInt(2, news_id);
            sta.executeUpdate();

        }


        stmt.close();
        if (sta != null) {
            sta.close();
        }
        conn.close();


    }

    public static String tag(String content_read) {
        long start = System.currentTimeMillis();

        String content = PrepareTag.preprocess(content_read);
        String segment_content = AnalyzerUtil.getAnalyzeResult(content, " ");

        if(segment_content.split(" ").length < 15) {
//            System.out.println("origin content is too short");
            return " ";
        }

        int wordCnt = 0;
        StringBuffer tags = new StringBuffer();

        Model model = predictor.inference(segment_content);
        double[] dist = model.theta[0];

        TreeMap<Double, Integer> topicWeightTreeMap = new TreeMap<Double, Integer>();
        HashMap<Integer, Double> topicWeight = new HashMap<>();
        for (int i = 0; i < dist.length; i++) {
            topicWeightTreeMap.put(dist[i], i);
            topicWeight.put(i, dist[i]);
        }

        ArrayList<Integer> indices = new ArrayList<>(topicWeightTreeMap.values());
        for (int i = indices.size() - 1; i >= 0; i--) {
            if (wordCnt >= maxWords)
                break;
            int idx = indices.get(i);
            String word = idx2topic.get(idx);
            double weight = topicWeight.get(idx);

            if (!word.equals("无意义") && weight >= threshold) {
                tags.append(word).append(',');
                wordCnt++;
            }

        }
        if (tags.length() > 0) {
            tags.deleteCharAt(tags.length() - 1);
        }
        long end = System.currentTimeMillis();
        System.out.println((end-start) + "ms");
        return tags.toString();

    }

    public static void main(String[] args) throws Exception {
        go();

    }

}
