import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Demo {

    public static void main(String[] args)
    {
        Runtime rt = Runtime.getRuntime();
        Process process = null;
        String url = "https://detail.tmall.com/item.htm?id=44163831176";

        String parser = Demo.class.getResource("parser.js").getFile();
//        String phantomjs = "/Users/xiuluo/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs";
        String phantomjs = "D:\\Users\\Administrator\\Download\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";

        try {
            process = rt.exec(phantomjs + " "
                    + parser + " " +url);
            InputStream in = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            BufferedReader br = new BufferedReader(reader);
            StringBuffer sbf = new StringBuffer();
            String tmp = "";
            while ((tmp = br.readLine()) != null) {
                sbf.append(tmp);
            }
            System.out.println(sbf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}