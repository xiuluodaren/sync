
/*
 * 作者:张世成
 * 时间:2018/11/7 10:42
 * 描述:
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetSKUInfo {

    public static String path = "/Users/xiuluo/Desktop/数据同步/";
//    public static String path = "../";

    public JScrollPane jScrollPane;

    public void skuInfoGet(JTextArea textArea)
    {

        Thread thread = new Thread(() -> {
            //获取商品详情
            try {

                String[] numbers = getNumbers();

                int i = 0;
                for(String number : numbers)
                {
                    System.out.println("开始获取第" + i + "个商品:" + number + "的SKU信息");
                    if (textArea != null)
                    {
                        textArea.append("开始获取第" + i + "个商品:" + number + "的SKU信息\r\n");
                        textArea.paintImmediately(textArea.getBounds());
                        jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
                    }
                    String url = "https://detail.tmall.com/item.htm?id=" + number;
    //            String url = "https://mdskip.taobao.com/core/initItemDetail.htm?itemId=44163831176";

                    URL httpUrl = new URL(url);

                    HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:59.0) Gecko/20100101 Firefox/59.0");
                    connection.setRequestProperty("Referer", "https://detail.tmall.com/item.htm?id=" + number);

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
                        //                System.out.println(html);

                        String json = analysisHTML(html);

                        if (json == null) {
                            System.out.println("获取" + number + "的SKU信息失败");
                            if (textArea != null) {
                                textArea.append("获取" + number + "的SKU信息失败\r\n");
                                textArea.paintImmediately(textArea.getBounds());
                                jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
                            }
                            i++;
                            continue;
                        }

                        System.out.println("获取" + number + "的SKU信息成功");
                        if (textArea != null) {
                            textArea.append("获取" + number + "的SKU信息成功\r\n");
                            textArea.paintImmediately(textArea.getBounds());
                            jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
                        }
                        File file = new File(path + "/sku/" + number + ".json");

                        File tempFile = new File(path + "/sku/");
                        if (!tempFile.isDirectory()) {
                            tempFile.mkdirs();
                        }

                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        PrintWriter printWriter = new PrintWriter(fileOutputStream);
                        printWriter.write(json);
                        printWriter.flush();
                        printWriter.close();

                    }

                    i++;

                }

                System.out.println("全部获取完毕,请到" + path + "/sku/目录查看");
                if (textArea != null)
                {
                    textArea.append("全部获取完毕,请到" + path + "/sku/目录查看\r\n");
                    textArea.paintImmediately(textArea.getBounds());
                    jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        });

        thread.start();

    }

    private String[] getNumbers()
    {
        try{
            File file = new File(path + "/numbers.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] b = new byte[1024];

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            while (-1 != (len = fileInputStream.read(b))) {
                baos.write(b, 0, len);
                baos.flush();
            }

            String numberStr = baos.toString();
            return numberStr.split(",");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String analysisHTML(String html)
    {
        String result = "";

        int start = html.indexOf("TShop.Setup(") + 12;

        if (start == 11)
        {
            //没找到，返回
            return null;
        }

        html = html.substring(start);
        int end = html.indexOf(");");
        result = html.substring(0,end);

        JSONObject jsonObject = JSON.parseObject(result);
        String json = JSON.toJSONString(jsonObject);

        return json;

        /*
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
        */


    }


}
