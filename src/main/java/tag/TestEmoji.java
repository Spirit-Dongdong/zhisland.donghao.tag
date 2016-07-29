package tag;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by Spirit on 16/7/26.
 */
public class TestEmoji {
    public static void main (String[] args) throws Exception {
        String cmsUrl="jdbc:mysql://192.168.2.101:4007/zh_bms_cms?user=zhisland_app&password=akQq5csFsmbx5U&useUnicode=true";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(cmsUrl);
        String url = "http://mp.weixin.qq.com/s?__biz=MjM5NDI2OTk5Mg==&mid=2651526051&idx=1&sn=f73ebd74b32bd3611013a3db2d61d2d6";


        conn.setAutoCommit(true);
//        conn.prepareStatement("set names utf8mb4").executeQuery();

        Document doc = Jsoup.connect(url).get();
        String title = doc.title();
        String content = doc.body().getElementById("js_content").html();
        System.out.println(title);
        System.out.println(content);



        content = " ";

        String sql = "insert into tb_news_resource (src_url, title, content, audit_status, publish_time, create_time, author_name) values (?,?,?,?,?,?,?)";
        PreparedStatement sta = conn.prepareStatement(sql);
        sta.setString(1, url);
        sta.setString(2, title);
        sta.setString(3, content);
        sta.setInt(4, 0);
        sta.setString(5, "2016-07-26");System.out.println(sql);
        sta.setString(6, "2016-07-26");
        sta.setString(7, "aaaaa");
        System.out.println(sta);

        sta.executeUpdate();

        sta.close();
        conn.close();



    }
}
