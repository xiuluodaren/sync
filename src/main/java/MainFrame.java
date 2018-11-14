import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class MainFrame {

    public Browser browser = new Browser();

    public final JTextArea rTextArea = new JTextArea();

    final JScrollPane js = new JScrollPane(rTextArea);

    public MainFrame()
    {
        final String url = "https://detail.tmall.com/item.htm?id=44163831176";
        BrowserView browserView = new BrowserView(browser);
        JFrame frame = new JFrame();
        //禁用close功能
//        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //不显示标题栏,最大化,最小化,退出按钮
//        frame.setUndecorated(true);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        browser.loadURL(url);

        //底部按钮
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        Dimension dimension = new Dimension();
        dimension.setSize(frame.getWidth(),80);
        jPanel.setPreferredSize(dimension);
        jPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jPanel.setVisible(true);
        frame.add(jPanel,BorderLayout.SOUTH);

        JButton jButton = new JButton();
        jButton.setBounds(20,20,180,40);
        jButton.setText("开始获取商品SKU信息");
        jButton.setVisible(true);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("点击");
//                browser.executeJavaScript("alert('java調用了js')");

                GetSKUInfo getSKUInfo = new GetSKUInfo();
                getSKUInfo.jScrollPane = js;
                getSKUInfo.skuInfoGet(rTextArea);

            }
        };
        jButton.addActionListener(actionListener);
        jPanel.add(jButton);

        //右侧信息输出区域
        JPanel rPanel = new JPanel();
        rPanel.setLayout(null);
        Dimension rDimension = new Dimension();
        rDimension.setSize(400,frame.getHeight());
        rPanel.setPreferredSize(rDimension);
        rPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rPanel.setVisible(true);
        frame.add(rPanel,BorderLayout.EAST);

        rTextArea.setBounds(0,0,100,100);
        rTextArea.setVisible(true);
        rTextArea.setBackground(new Color(200,200,200));
        rTextArea.setSelectedTextColor(Color.RED);
        rTextArea.setLineWrap(true);        //激活自动换行功能
        rTextArea.setWrapStyleWord(true);
        rPanel.add(rTextArea);

        js.setVisible(true);
        js.setBounds(0,0,200,200);
        rPanel.add(js,BorderLayout.CENTER);

        //添加监听，监听尺寸变化
        ComponentListener componentListener = new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                rTextArea.setBounds(0,0,(int)e.getComponent().getPreferredSize().getWidth(),e.getComponent().getHeight());
                js.setBounds(0,0,(int)e.getComponent().getPreferredSize().getWidth(),e.getComponent().getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        };
        rPanel.addComponentListener(componentListener);

    }


}
