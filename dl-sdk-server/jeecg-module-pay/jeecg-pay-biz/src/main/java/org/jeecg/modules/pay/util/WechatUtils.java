package org.jeecg.modules.pay.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import java.util.Map.Entry;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.NodeList;

/**
 * @author xmh
 * @version V1.0
 * @description: 微信工具类
 * @date: 2023/1/3 14:43
 **/
public class WechatUtils {

    /**
     * @param mchId:               商户号
     * @param mchSerialNo:         商户API证书序列号
     * @param mchPrivateKeyString: 商户私钥
     * @param apiV3Key:            API V3密钥
     * @return CloseableHttpClient
     * @author xmh
     * @description 构建 wechat http client
     * @date 2023/1/5 9:57
     */
    public static CloseableHttpClient buildHttpClient(String mchId, String mchSerialNo,
        String mchPrivateKeyString, String apiV3Key) {
        try {
            PrivateKey mchPrivateKey = loadMchPrivateKey(mchPrivateKeyString);
            // 获取 verifier
            Verifier verifier = getVerifier(mchId, mchSerialNo, apiV3Key, mchPrivateKey);

            WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, mchPrivateKey)
                .withValidator(new WechatPay2Validator(verifier));
            // 通过 WechatPayHttpClientBuilder 构造的 httpClient  会自动的处理签名和验签，并进行证书自动更新
            return builder.build();
        } catch (Exception e) {
            throw new IdeaRunTimeException(ErrorCode.BUILD_HTTP_CLIENT_ERROR);
        }
    }

    /**
     * @param mchPrivateKeyString: 私钥字符串
     * @return PrivateKey
     * @author xmh
     * @description 加载商户私钥
     * @date 2023/1/5 14:04
     */
    public static PrivateKey loadMchPrivateKey(String mchPrivateKeyString) {
        return PemUtil.loadPrivateKey(
            new ByteArrayInputStream(mchPrivateKeyString.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * @param mchId:         商户号
     * @param mchSerialNo:   商户API证书序列号
     * @param apiV3Key:      API V3密钥
     * @param mchPrivateKey: 商户私钥
     * @return Verifier
     * @author xmh
     * @description 获取 verifier
     * @date 2023/1/3 18:20
     */
    public static Verifier getVerifier(String mchId, String mchSerialNo, String apiV3Key,
        PrivateKey mchPrivateKey) {
        // 获取证书管理器实例
        CertificatesManager certificatesManager = CertificatesManager.getInstance();
        // 私钥签名对象
        PrivateKeySigner privateKeySigner = new PrivateKeySigner(mchSerialNo, mchPrivateKey);
        // 用来实现省份认证
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(mchId,
            privateKeySigner);
        // 向证书管理器增加需要自动更新平台证书的商户信息
        try {
            certificatesManager.putMerchant(mchId, wechatPay2Credentials, apiV3Key.getBytes(
                StandardCharsets.UTF_8));
            // 从证书管理器中获取verifier
            return certificatesManager.getVerifier(
                mchId);
        } catch (IOException | GeneralSecurityException | HttpCodeException | NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json转xml<br>
     * 方 法 名：jsonToXml <br>
     * @param json
     * @return String
     */
    public static String jsonToXml(String json){
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append("<xml>");
            JSONObject jObj = JSON.parseObject(json);
            jsonToXmlstr(jObj,buffer);
            buffer.append("</xml>");
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * json转str<br>
     * 方 法 名：jsonToXmlstr <br>
     * @param jObj
     * @param buffer
     * @return String
     */
    public static String jsonToXmlstr(JSONObject jObj,StringBuffer buffer ){
        Set<Entry<String, Object>> se = jObj.entrySet();
        for( Iterator<Entry<String, Object>> it = se.iterator();  it.hasNext(); )
        {
            Entry<String, Object> en = it.next();
            if(en.getValue() != null && en.getValue().getClass().getName().equals("com.alibaba.fastjson.JSONObject")){
                buffer.append("<"+en.getKey()+">");
                JSONObject jo = jObj.getJSONObject(en.getKey());
                jsonToXmlstr(jo,buffer);
                buffer.append("</"+en.getKey()+">");
            }else if(en.getValue() != null && en.getValue().getClass().getName().equals("com.alibaba.fastjson.JSONArray")){
                if (en.getKey().equals("extproperties")) {
                    JSONArray ja = jObj.getJSONArray(en.getKey());
                    Iterator<Object> it1 = ja.iterator();
                    List<String> list=new ArrayList<String>();
                    while (it1.hasNext()) {
                        String ob = (String) it1.next();
                        System.out.println(ob);
                    }
                }else {
                    JSONArray jarray = jObj.getJSONArray(en.getKey());
                    for (int i = 0; i < jarray.size(); i++) {
                        buffer.append("<"+en.getKey()+">");
                        JSONObject jsonobject =  jarray.getJSONObject(i);
                        jsonToXmlstr(jsonobject,buffer);
                        buffer.append("</"+en.getKey()+">");
                    }
                }

            }else if(en.getValue() != null && (en.getValue().getClass().getName().equals("java.lang.String") || en.getValue().getClass().getName().equals("java.lang.Integer"))){
                buffer.append("<"+en.getKey()+">"+en.getValue());
                buffer.append("</"+en.getKey()+">");
            }else{
                buffer.append("<"+en.getKey()+">"+"");
                buffer.append("</"+en.getKey()+">");
            }

        }
        return buffer.toString();
    }

    /**
     * @param obj
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @Author lili
     * @Description Java对象转Map<String,String>
     * @Date 16:16 2023/4/13
     **/
    public static Map<String, String> convertObjectToMap(Object obj){
        return Arrays.stream(BeanUtils.getPropertyDescriptors(obj.getClass()))
            .filter(pd -> !"class".equals(pd.getName()))
            .collect(HashMap::new,
                (map, pd) -> {
                    if(ReflectionUtils.invokeMethod(pd.getReadMethod(), obj) != null) {
                        map.put(pd.getName(), String.valueOf(ReflectionUtils.invokeMethod(pd.getReadMethod(), obj)));
                    }
                },
                HashMap::putAll);
    }

    /**
     * @param paramMap
     * @return 将Map转成XML
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> paramMap) throws Exception {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("utf-8");
        //创建根节点
        Element xmlRoot = document.addElement("xml");
        //转化成treeMap
        Set<Map.Entry<String, String>> paramSet = paramMap.entrySet();
        Iterator<Map.Entry<String, String>> paramMapIter = paramSet.iterator();

        Map<String, String> treeMap = new TreeMap<>();

        while (paramMapIter.hasNext()) {
            Map.Entry<String, String> param = paramMapIter.next();
            if (StringUtils.isEmpty(param.getKey())) {
                throw new RuntimeException("微信支付，转换xml参数为空");
            }

            treeMap.put(param.getKey(), param.getValue());
        }

        Set<Map.Entry<String, String>> treeMapSet = treeMap.entrySet();
        Iterator<Map.Entry<String, String>> treeMapSetIterator = treeMapSet.iterator();
        while (treeMapSetIterator.hasNext()) {
            Map.Entry<String, String> param = treeMapSetIterator.next();
            // 如有空值就不参加校验
            if (StringUtils.isEmpty(param.getKey()) || StringUtils.isEmpty(param.getValue())) {
                continue;
            }

            if (!StringUtils.isEmpty(param.getKey())) {
                Element element = xmlRoot.addElement(param.getKey());

                if (!StringUtils.isEmpty(param.getValue())) {
                    element.addText("<![CDATA[" + param.getValue() + "]]>");
                }
            }
        }

        String xmlString = formatXml(document, "utf-8", false);

        return xmlString;
    }

    /**
     * 格式化XML文档
     *
     * @param document xml文档
     * @param charset  字符串的编码
     * @param istrans  是否对属性和元素值进行转移
     * @return 格式化后XML字符串
     */
    private static String formatXml(Document document, String charset, boolean istrans) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        StringWriter sw = new StringWriter();
        XMLWriter xw = new XMLWriter(sw, format);
        xw.setEscapeText(istrans);
        try {
            xw.write(document);
            xw.flush();
            xw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * StringUtils工具类方法
     * 获取一定长度的随机字符串，范围0-9，a-z
     *
     * @param length：指定字符串长度
     * @return 一定长度的随机字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 对单词列表进行冒泡排序
     * 直接操作对象地址 无需返回
     *
     * @param words ["name","age"]
     */
    private static void wordSort(ArrayList<String> words) {
        for (int i = words.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (words.get(j).compareToIgnoreCase(words.get(j + 1)) > 0) {
                    String temp = words.get(j);
                    words.set(j, words.get(j + 1));
                    words.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * 对单层json排序
     *
     * @param json
     */
    public static JSONObject getAloneKeys(JSONObject json) {
        ArrayList<String> aloneKeys = new ArrayList<>();
        for (String key : json.keySet()) {
            aloneKeys.add(key);
        }
        // 排序
        wordSort(aloneKeys);
        // 整理排序后的json
        JSONObject newJson = new JSONObject(new LinkedHashMap<>());
        for (String key : aloneKeys) {
            newJson.put(key, json.get(key));
        }
        return newJson;
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> doXMLParse(String result) {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        try {
            InputStream in = new ByteArrayInputStream(result.getBytes());
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();
            for (Element element : elementList) {
                map.put(element.getName(), element.getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return map;
    }



}
