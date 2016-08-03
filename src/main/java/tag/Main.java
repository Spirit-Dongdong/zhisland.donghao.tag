package tag;
/**
 * Created by Spirit on 16/7/25.
 */
public class Main {
    public static void main (String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java -jar xxx.jar [generate|tag] ");
            System.exit(1);
        }
        if (args[0].equals("generate")) {
            PrepareTag.go();
        } else if (args[0].equals("tag")) {
            DocumentTag.go();
        }
    }
}
