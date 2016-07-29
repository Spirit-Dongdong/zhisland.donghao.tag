package tag;

import com.util.ConfigLoader;

/**
 * Created by Spirit on 16/7/25.
 */
public class Main {

    public static void go() {
        int cnt = 0;

        String sql = "select tb_news.news_id, tb_news_content.content_read\n" +
                "from tb_news\n" +
                "left join tb_news_content on tb_news.news_id = tb_news_content.news_id";

    }

    public static void main (String[] args) {
//        if (args.length < 1) {
//            System.out.println("Usage:");
//            System.exit(1);
//        }
//        System.out.println(args[0]);
//        if (args[0].equals("")) {
//
//        } else if (args[0].equals("")) {
//
//        }
        ConfigLoader loader = new ConfigLoader("config");
        String ldaPath = loader.getString("lda_path");
        System.out.println(ldaPath);
    }
}
