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
        String url = "https://detail.tmall.com/item.htm?id=575141378393";
        try {
            process = rt.exec("D:\\Users\\Administrator\\Download\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe D:\\Users\\Administrator\\DeskTop\\sync\\src\\main\\resources\\parser.js " +url);
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