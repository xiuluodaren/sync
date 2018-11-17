import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.LoadURLParams;
import com.teamdev.jxbrowser.chromium.events.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainFrame {

    public Browser browser = new Browser();

    public final JTextArea rTextArea = new JTextArea();

    final JScrollPane js;

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

        JButton promotionBtn = new JButton();
        promotionBtn.setBounds(220,20,180,40);
        promotionBtn.setText("获取商品促销信息");
        promotionBtn.setVisible(true);
        ActionListener actionListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("获取商品促销信息");
                promotionGet();
            }
        };
        promotionBtn.addActionListener(actionListener1);
        jPanel.add(promotionBtn);

        //生成信息
        JButton genBtn = new JButton();
        genBtn.setBounds(420,20,80,40);
        genBtn.setText("生成信息");
        genBtn.setVisible(true);
        ActionListener genBtnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("生成信息");
                GenerateData generateData = new GenerateData();
                generateData.jScrollPane = js;
                generateData.generate(rTextArea);
            }
        };
        genBtn.addActionListener(genBtnListener);
        jPanel.add(genBtn);

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

        js = new JScrollPane(rTextArea);
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

    public void promotionGet()
    {
        Thread thread = new Thread(() -> {
            //获取商品详情
            try {

                String[] numbers = getNumbers();

                int i = 0;
                for(String number : numbers)
                {
                    System.out.println("开始获取第" + i + "个商品:" + number + "的促销信息");
                    if (rTextArea != null)
                    {
                        rTextArea.append("开始获取第" + i + "个商品:" + number + "的促销信息\r\n");
                        rTextArea.paintImmediately(rTextArea.getBounds());
                        js.getVerticalScrollBar().setValue(js.getVerticalScrollBar().getMaximum());
                    }
                    String url = "https://mdskip.taobao.com/core/initItemDetail.htm?itemId=" + number;

                    browser.loadURL(new LoadURLParams(url,"","" +
                            "Referer:https://detail.tmall.com/item.htm?id=" + number));

                    LoadListener loadListener = new LoadListener() {
                        @Override
                        public void onStartLoadingFrame(StartLoadingEvent startLoadingEvent) {

                        }

                        @Override
                        public void onProvisionalLoadingFrame(ProvisionalLoadingEvent provisionalLoadingEvent) {

                        }

                        @Override
                        public void onFinishLoadingFrame(FinishLoadingEvent finishLoadingEvent) {
                            String html = browser.getHTML();

                            int start = html.indexOf("defaultModel") - 2;
                            int end = html.indexOf("</pre>");

                            if (start < 0)
                            {
                                browser.loadURL("https://login.tmall.com/");
                                return;
                            }

                            String json = html.substring(start, end);

                            if (json == null) {
                                System.out.println("获取" + number + "的促销信息失败");
                                if (rTextArea != null) {
                                    rTextArea.append("获取" + number + "的促销信息失败\r\n");
                                    rTextArea.paintImmediately(rTextArea.getBounds());
                                    js.getVerticalScrollBar().setValue(js.getVerticalScrollBar().getMaximum());
                                }
                            }

                            System.out.println("获取" + number + "的促销信息成功");
                            if (rTextArea != null) {
                                rTextArea.append("获取" + number + "的促销信息成功\r\n");
                                rTextArea.paintImmediately(rTextArea.getBounds());
                                js.getVerticalScrollBar().setValue(js.getVerticalScrollBar().getMaximum());
                            }

                            if (number == numbers[numbers.length - 1])
                            {
                                System.out.println("全部获取完毕,请到" + GetSKUInfo.path + "/promotions/目录查看");
                                if (rTextArea != null)
                                {
                                    rTextArea.append("全部获取完毕,请到" + GetSKUInfo.path + "/promotions/目录查看\r\n");
                                    rTextArea.paintImmediately(rTextArea.getBounds());
                                    js.getVerticalScrollBar().setValue(js.getVerticalScrollBar().getMaximum());
                                }
                            }

                            File file = new File(GetSKUInfo.path + "/promotions/" + number + ".json");

                            File tempFile = new File(GetSKUInfo.path + "/promotions/");
                            if (!tempFile.isDirectory()) {
                                tempFile.mkdirs();
                            }

                            FileOutputStream fileOutputStream = null;
                            try {
                                fileOutputStream = new FileOutputStream(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            PrintWriter printWriter = new PrintWriter(fileOutputStream);
                            printWriter.write(json);
                            printWriter.flush();
                            printWriter.close();

                        }

                        @Override
                        public void onFailLoadingFrame(FailLoadingEvent failLoadingEvent) {

                        }

                        @Override
                        public void onDocumentLoadedInFrame(FrameLoadEvent frameLoadEvent) {

                        }

                        @Override
                        public void onDocumentLoadedInMainFrame(LoadEvent loadEvent) {

                        }
                    };
                    browser.addLoadListener(loadListener);

                    i++;

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
            File file = new File(GetSKUInfo.path + "/numbers.txt");
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

}
