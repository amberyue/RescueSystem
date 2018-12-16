package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SentUtils {

public static String sendPostData(String POST_URL, String content) throws Exception {
    HttpURLConnection connection = null;
    DataOutputStream out = null;
    BufferedReader reader = null;
    String line = "";
    String result = "";
    try {
        URL postUrl = new URL(POST_URL);
        connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        // Post 请求不能使用缓存
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();
 
        out = new DataOutputStream(connection.getOutputStream());
        // content = URLEncoder.encode(content, "utf-8");
        // DataOutputStream.writeBytes将字符串中的16位的unicode字符�?8位的字符形式写道流里�?
        out.writeBytes(content);
        out.flush();
        out.close();
        // 获取结果
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码
        while ((line = reader.readLine()) != null) {
            result = result + line;
        }
        return result;
    } catch (Exception e) {
        throw e;
    } finally {
        if (out != null) {
            out.close();
            out = null;
        }
        if (reader != null) {
            reader.close();
            reader = null;
        }
        connection.disconnect();
    }
}




}
