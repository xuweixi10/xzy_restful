package xzydemo.demo.service.impl;
import org.springframework.stereotype.Service;
import xzydemo.demo.service.CodeToOpenID;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CodeToOpenIDImpl implements CodeToOpenID {
    private Object ArrayList;

    @Override
    public String GetOpenID(String code) {
        try {
            String result="";
            URL url = new URL("https://api.weixin.qq.com/sns/jscode2session?appid=wx487fa995a1ca7f1c&secret=cb20083066fcc40bb3f72d99c0a41857&js_code="+code+"&grant_type=authorization_code&connect_redirect=1");
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            //请求方式
//          conn.setRequestMethod("POST");
//           //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。
            conn.setRequestMethod("GET");//GET和POST必须全大写
            /**GET方法请求*****start*/
            /**
             * 如果只是发送GET方式请求，使用connet方法建立和远程资源之间的实际连接即可；
             * 如果发送POST方式的请求，需要获取URLConnection实例对应的输出流来发送请求参数。
             * */
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line;
            String openid = null;
            while ((line = in.readLine()) != null) {
                result += line;
                System.out.println(line);
                openid=line.split(",")[1].split(":")[1].replace("\"","").replace("}","");
            }
            return openid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
