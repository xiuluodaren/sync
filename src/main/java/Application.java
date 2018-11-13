import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;

import com.teamdev.jxbrowser.chromium.bb;

public class Application {

    static {
        try {
            Field e = bb.class.getDeclaredField("e");
            e.setAccessible(true);
            Field f = bb.class.getDeclaredField("f");
            f.setAccessible(true);
            Field modifersField = Field.class.getDeclaredField("modifiers");
            modifersField.setAccessible(true);
            modifersField.setInt(e, e.getModifiers() & ~Modifier.FINAL);
            modifersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
            e.set(null, new BigInteger("1"));
            f.set(null, new BigInteger("1"));
            modifersField.setAccessible(false);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String url = "http://www.baidu.com/";
        final String title = "百度";
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);

        JFrame frame = new JFrame();
        //禁用close功能
//        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //不显示标题栏,最大化,最小化,退出按钮
//        frame.setUndecorated(true);
        frame.add(view, BorderLayout.CENTER);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        browser.loadURL(url);
    }

}
