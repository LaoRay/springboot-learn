package com.zhengtoon.framework.pay.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author Leiqiyun
 * @date 2018/9/28 11:40
 */
@Slf4j
public class XmlUtil {

    /**
     * xml转对象
     *
     * @param xml
     * @param objClass
     * @return
     */
    public static Object toObject(String xml, Class objClass) {
        Serializer serializer = new Persister();
        try {
            return serializer.read(objClass, xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * xml 转 字符
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        Serializer serializer = new Persister();
        StringWriter output = new StringWriter();
        try {
            serializer.write(obj, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     */
    public static String mapToXmlString(Map<String, String> data) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.newDocument();
            org.w3c.dom.Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key : data.keySet()) {
                String value = data.get(key);
                if (value == null) {
                    value = "";
                }
                value = value.trim();
                org.w3c.dom.Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.getBuffer().toString();
            try {
                writer.close();
            } catch (Exception ex) {
                log.error("writer close error", ex);
            }
            return output;
        } catch (Exception ex) {
            log.error("map to xml error", ex);
        }
        return null;
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     */
    public static Map<String, String> xmlToMap(String strXML) {
        try {
            Map<String, String> data = Maps.newHashMap();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                log.error("stream close error", ex);
            }
            return data;
        } catch (Exception ex) {
            log.error("xml to map error", ex);
        }
        return Maps.newHashMap();
    }

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     */
    public static String parseXmlToString(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();
            br = request.getReader();
            for (String line; (line = br.readLine()) != null; ) {
                if (result.length() > 0) {
                    result.append("\n");
                }
                result.append(line);
            }

            return result.toString();
        } catch (IOException e) {
            log.error("parse Xml to String error", e);
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解析微信发来的请求（XML）
     *
     * @param request
     * @return
     */
    public static Map<String, String> parseXmltoMap(HttpServletRequest request) {
        try {
            // 将解析结果存储在HashMap中
            Map<String, String> map = Maps.newHashMap();

            // 从request中取得输入流
            InputStream inputStream = request.getInputStream();
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
            }
            try {
                inputStream.close();
            } catch (Exception ex) {
                log.error("inputStream close error", ex);
            }
            return map;
        } catch (Exception ex) {
            log.error("parse request to map error", ex);
        }
        return Maps.newHashMap();
    }

    /***
     * 告诉微信服务器，收到消息
     *
     * @param returnCode
     * @param returnMsg
     * @return
     */
    public static String setXML(String returnCode, String returnMsg) {
        return "<xml><return_code><![CDATA[" + returnCode
                + "]]></return_code><return_msg><![CDATA[" + returnMsg
                + "]]></return_msg></xml>";
    }
}
