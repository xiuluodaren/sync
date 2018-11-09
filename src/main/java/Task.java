
/*
 * 作者:张世成
 * 时间:2018/11/7 10:42
 * 描述:
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Task {

    public void runTask()
    {
        //获取商品详情
        try {
//            String url = "https://detail.tmall.com/item.htm?id=44163831176";
            String url = "https://mdskip.taobao.com/core/initItemDetail.htm?itemId=44163831176";

            URL httpUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Referer", "https://detail.tmall.com/item.htm?id=44163831176");

            if (200 == connection.getResponseCode()) {
                //得到输入流
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while (-1 != (len = is.read(buffer))) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }
                String html = baos.toString("GBK");

                //得到的页面
                System.out.println(html);

                analysisHTML(html);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void  analysisHTML(String html)
    {
        Document doc = Jsoup.parse(html);

        //找到规格div
        Elements rows = doc.select("div[class=tb-sku]");

        Element e = rows.get(0);

        //获取text
        String text = rows.get(0).text();

        int sizeStart = text.indexOf("尺码") + 3;
        int sizeEnd = text.indexOf("主要颜色") - 1;

        String sizeString = text.substring(sizeStart, sizeEnd);

        //尺码
        String[] sizes = sizeString.split(" ");

        int colorEnd = text.indexOf("数量") - 1;

        //颜色
        String[] colors = text.substring(sizeEnd + 6, colorEnd).split(" ");



    }


}
