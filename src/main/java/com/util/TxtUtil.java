package com.util;

import java.io.*;

/**
 * Created by fangdonghao on 15/8/11.
 */
public class TxtUtil {
    public static String getFileContent(String path) {
        try {
            StringBuffer sb = new StringBuffer();
            String line;
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getFileContent(File f) {
        try {
            StringBuffer sb = new StringBuffer();
            String line;
            BufferedReader br = new BufferedReader(new FileReader(f));
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static void writeToFile(String content, String path, boolean append) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, append));
            bw.write(content + "\n");
            bw.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}
