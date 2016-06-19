package sun.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 
  * @ClassName: XmlAnalysisUtil 
  * @Description: 数据转换工具类 
  * @author tony 
  * @date 2014-6-19 上午08:00:36 
  *
 */
public class XmlAnalysisUtil {
	/**
	 * 将element填充为map
	 * 获取普通型报文信息   
	 * @param element
	 * @param map
	 * @return
	 */
	 public static HashMap map(Element element,HashMap map){
		 List list = element.elements();
			for(int i = 0;i<list.size();i++){
				Element e = (Element) list.get(i);
				if(e.elements()!=null && e.elements().size()>0){
					map(e,map);
				}
				if(map.get(e.getName())==null){
					String key0=e.getName();
					String key1=e.getName();
				    String key=key0.toLowerCase().substring(0,1)+key1.replace("_", "").substring(1);
					map.put(key, e.getText().trim());
				}
			}
		 return map;
	 }
	 
	 /**
	  * 将element填充为map   
	  * 获取键值对型报文信息  键值为“key”
	  * @param element
	  * @param map
	  * @return
	  */
	 public static HashMap mapForItem(Element element,HashMap map){
		 List list = element.elements();
			for(int i = 0;i<list.size();i++){
				Element e = (Element) list.get(i);
				if(e.elements()!=null && e.elements().size()>0){
					mapForItem(e,map);
				}
				if(map.get(e.getName())==null){
					String item=e.getStringValue();
					String itemname=e.attributeValue("key");
				    String key=itemname.toLowerCase().substring(0,1)+itemname.replaceAll("-", "_").substring(1);
					map.put(key, item);
				}
			}
		 return map;
	 }
	
	 /**
	 * string2Document 将字符串转为Document
	 * @return
	 * @param str  xml格式的字符串
	 */
	public static Document string2Document(String str) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return doc;
	}
	/**
	 * 根据路径解析报文  
	 * @param inXml
	 * @return
	 */
	public static Map getObjectFromString(String inXml,String nodePath) {
		Document doc = string2Document(inXml);
		List responseList = doc.selectNodes(nodePath);
		//报文头
		Iterator it = responseList.iterator();
		HashMap mp = new HashMap();
		while (it.hasNext()) {
			Element errorElement = (Element) it.next();
			mp = map(errorElement, mp);
		}
		
		return mp;
	}
	/**
	 * 根据路径解析报文  
	 * @param inXml
	 * 修改2016年1月18日15:58:22（无忧卡单承保测试）
	 * @return
	 */
	public static List<Map> getObjectListFromString(String inXml,String nodePath) {
		Document doc = string2Document(inXml);
		List responseList = doc.selectNodes(nodePath);
		List<Map> returnList=new ArrayList<Map>();
		//报文头
		Iterator it = responseList.iterator();
		HashMap mp=null;
		while (it.hasNext()) {
			mp = new HashMap();
			Element errorElement = (Element) it.next();
			mp = map(errorElement, mp);
			returnList.add(mp);
		}
		
		return returnList;
	}
	/**
	 * 根据路径解析报文  
	 * @param inXml
	 * @return
	 */
	public static Map getObjectFromStringInItemMap(String inXml,String nodePath) {
		Document doc = string2Document(inXml);
		List responseList = doc.selectNodes(nodePath);
		//报文头
		Iterator it = responseList.iterator();
		HashMap mp = new HashMap();
		while (it.hasNext()) {
			Element errorElement = (Element) it.next();
			mp = mapForItem(errorElement, mp);
		}
		
		return mp;
	}
	

	
}
