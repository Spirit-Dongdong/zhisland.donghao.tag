package tag;

import com.util.TxtUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Spirit on 16/6/28.
 */
public class Test {

    public static void washDict() {
        String output = "resources/new.dic";
        Set<String> ws = new HashSet<>();

        String[] words = TxtUtil.getFileContent("resources/main2012.dic").split("\n");
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            if (w.length() > 1 && !ws.contains(w)) {
                TxtUtil.writeToFile(w, output, true);
            }
            ws.add(w);
        }
    }

    public static void testJsoup(String html) {
        Document doc = Jsoup.parse(html);
        System.out.println(doc);
        String text = doc.text();
        System.err.println(text);
    }





    public static void main(String[] args) {
//        washDict();
//        File f = new File("my.dic");
//        System.out.println(f.getAbsolutePath());
        String a = TxtUtil.getFileContent("resources/my.dic");
        System.out.println(a.length());
    }
}
