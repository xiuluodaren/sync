import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class GenerateData {

    public JScrollPane jScrollPane;

    public void generate(JTextArea textArea)
    {
        Thread thread = new Thread(() -> {
            try {
                File file = new File(GetSKUInfo.path + "/sku/");
                if (file.isDirectory()) {
                    File[] files = file.listFiles();

                    int index = 0;
                    for (File f : files) {
                        String fileName = f.getName();
                        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

                        if (!suffix.equals("json")) {
                            continue;
                        }

                        FileInputStream fileInputStream = new FileInputStream(f);

                        byte[] b = new byte[1024];

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int len = 0;
                        while (-1 != (len = fileInputStream.read(b))) {
                            baos.write(b, 0, len);
                            baos.flush();
                        }

                        String skuJSONStr = baos.toString();

                        if (skuJSONStr != null) {
                            JSONObject skuJSON = JSON.parseObject(skuJSONStr);

                            if (!new File(GetSKUInfo.path + "/promotions/").isDirectory()) {
                                System.out.println("promotions目录不存在");
                                if (textArea != null)
                                {
                                    textArea.append("promotions目录不存在\r\n");
                                    textArea.paintImmediately(textArea.getBounds());
                                    jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
                                }
                                return;
                            }

                            File pFile = new File(GetSKUInfo.path + "/promotions/" + f.getName());

                            FileInputStream pFileInputStream = new FileInputStream(pFile);

                            byte[] bytes = new byte[1024];

                            ByteArrayOutputStream pbaos = new ByteArrayOutputStream();
                            int l = 0;
                            while (-1 != (l = pFileInputStream.read(bytes))) {
                                pbaos.write(bytes, 0, l);
                                pbaos.flush();
                            }

                            String promotionJSONStr = pbaos.toString();

                            if (promotionJSONStr != null) {
                                JSONObject promotionJSON = JSON.parseObject(promotionJSONStr);
                                JSONObject defaultModel = (JSONObject) promotionJSON.get("defaultModel");
                                JSONObject itemPriceResultDO = (JSONObject) (defaultModel.get("itemPriceResultDO"));
                                JSONObject priceInfo = (JSONObject) (itemPriceResultDO.get("priceInfo"));

                                JSONObject valItemInfo = (JSONObject) skuJSON.get("valItemInfo");
                                List<JSONObject> skuList = (List<JSONObject>) valItemInfo.get("skuList");

                                //获取库存
                                for (int i = 0; i < skuList.size(); i++) {
                                    JSONObject sku = skuList.get(i);
                                    String pvs = (String) sku.get("pvs");
                                    pvs = ";" + pvs + ";";

                                    JSONObject skuMap = (JSONObject) valItemInfo.get("skuMap");
                                    Integer stock = (Integer) ((JSONObject) skuMap.get(pvs)).get("stock");

                                    sku.put("stock", stock);

                                    JSONObject propertyPics = (JSONObject) skuJSON.get("propertyPics");
                                    JSONArray pics = (JSONArray) propertyPics.get("default");
                                    sku.put("pics", pics);

                                    String skuId = (String) sku.get("skuId");
                                    JSONObject priceInfoWithSkuId = (JSONObject) priceInfo.get(skuId);

                                    if (priceInfoWithSkuId != null)
                                    {
                                        JSONArray promotionList = (JSONArray) priceInfoWithSkuId.get("promotionList");

                                        if (promotionList != null && promotionList.size() > 0) {
                                            for (int j = 0; j < promotionList.size(); j++) {
                                                JSONObject promotion = (JSONObject) promotionList.get(j);
                                                String price = (String) promotion.get("price");
                                                sku.put("price", price);
                                            }
                                        }
                                    }

                                }

                                //                            System.out.println(JSON.toJSONString(skuList));

                                File rFile = new File(GetSKUInfo.path + "/result/" + f.getName());

                                File tempFile = new File(GetSKUInfo.path + "/result/");
                                if (!tempFile.isDirectory()) {
                                    tempFile.mkdirs();
                                }

                                FileOutputStream fileOutputStream = new FileOutputStream(rFile);
                                PrintWriter printWriter = new PrintWriter(fileOutputStream);
                                printWriter.write(JSON.toJSONString(skuList));
                                printWriter.flush();
                                printWriter.close();

                                System.out.println("生成第" + index + "条数据成功");
                                if (textArea != null)
                                {
                                    textArea.append("生成第" + index + "条数据成功\r\n");
                                    textArea.paintImmediately(textArea.getBounds());
                                    jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
                                }
                                index++;

                            }


                        }

                    }

                    System.out.println("全部生成完毕");
                    if (textArea != null)
                    {
                        textArea.append("全部生成完毕r\n");
                        textArea.paintImmediately(textArea.getBounds());
                        jScrollPane.getVerticalScrollBar().setValue(jScrollPane.getVerticalScrollBar().getMaximum());
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

}
