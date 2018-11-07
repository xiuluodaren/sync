
/*
 * 作者:张世成
 * 时间:2018/7/14 10:10
 * 描述:批量下载全民k歌
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class Download {

    public static void main(String[] args)
    {
        String url = "http://node.kg.qq.com/cgi/fcgi-bin/kg_ugc_get_homepage?jsonpCallback=callback_0&g_tk=5381&outCharset=utf-8&format=jsonp&type=get_ugc&num=8&touin=&share_uid=609d98802128318c37&g_tk_openkey=5381&_=1531492976298&start=";

        for (int i = 1;i <= 30;i++)
        {
            try {
                String newUrl = url + i;

                URL httpUrl = new URL(newUrl);

                HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
                connection.setRequestMethod("GET");

                if(200 == connection.getResponseCode()){
                    //得到输入流
                    InputStream is =connection.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while(-1 != (len = is.read(buffer))){
                        baos.write(buffer,0,len);
                        baos.flush();
                    }
                    String json = baos.toString("utf-8");
                    String substring = json.substring(11, json.length() - 1);
                    JSONObject jsonObject = JSON.parseObject(substring);
                    JSONObject data = (JSONObject)jsonObject.get("data");
                    JSONArray temp = (JSONArray) data.get("ugclist");

                    for (int j = 0; j < temp.size(); j++)
                    {
                        JSONObject o = (JSONObject)temp.get(j);

                        URL detailUrl = new URL("http://node.kg.qq.com/play?g_f=personal&s=" + (String)o.get("shareid"));

                        Long time = Long.valueOf(o.get("time") + "000");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String path = "D:\\Users\\修罗大人\\Desktop\\新建文件夹\\music\\" + o.get("title") + format.format(time) + ".m4a";
                        File file = new File(path);

                        if (file != null && file.exists())
                        {
                            continue;
                        }else{
                            System.out.println(o.get("title"));
                        }

                        HttpURLConnection detailConnection = (HttpURLConnection) detailUrl.openConnection();
                        detailConnection.setRequestMethod("GET");

                        if (200 == detailConnection.getResponseCode()) {
                            //得到输入流
                            InputStream detailInputSteam = detailConnection.getInputStream();
                            ByteArrayOutputStream detailBaos = new ByteArrayOutputStream();
                            byte[] detailBuffer = new byte[1024];
                            int detailLen = 0;
                            while (-1 != (detailLen = detailInputSteam.read(detailBuffer))) {
                                detailBaos.write(detailBuffer, 0, detailLen);
                                detailBaos.flush();
                            }
                            String detailHtml = detailBaos.toString("utf-8");

                            int playurl = detailHtml.indexOf("playurl");
                            int playurl_video = detailHtml.indexOf("playurl_video");

                            String fileUrl = detailHtml.substring(playurl + 10,playurl_video - 3);

                            System.out.println(fileUrl);

                            HttpURLConnection getConnection = (HttpURLConnection) (new URL(fileUrl)).openConnection();
                            getConnection.setRequestMethod("GET");

                            if (200 == getConnection.getResponseCode()) {
                                //得到输入流
                                BufferedInputStream bin = new BufferedInputStream(getConnection.getInputStream());

                                if (!file.getParentFile().exists()) {
                                    file.getParentFile().mkdirs();
                                }
                                OutputStream out = new FileOutputStream(file);
                                int size = 0;
                                int flen = 0;
                                byte[] buf = new byte[1024];
                                while ((size = bin.read(buf)) != -1) {
                                    flen += size;
                                    out.write(buf, 0, size);
                                }
                                bin.close();
                                out.close();
                            }
                        }

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
