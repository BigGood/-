package sun.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




























import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.log4j.Logger;













import com.google.gson.Gson;

import sun.model.PmMode;
import sun.model.SFCard;
import sun.model.WeatherAlaMode;
import sun.model.WeatherAlaMode.Weather.Alarms;
import sun.model.WeatherAlaMode.Weather.Daily_forecast;
import sun.model.WeatherAlaMode.Weather.Daily_forecast.Cond;
import sun.model.WeatherAlaMode.Weather.Now;
import sun.model.WeatherMode;
import sun.util.HttpUtil;
import sun.util.Sha1;
import sun.util.XmlAnalysisUtil;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class test extends HttpServlet {
	private static Logger logger = Logger.getLogger(test.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		logger.info("进入");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		
		 StringBuffer sb = new StringBuffer();  
	        InputStream is = request.getInputStream();  
	        InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
	        BufferedReader br = new BufferedReader(isr);  
	        String s = "";  
	        while ((s = br.readLine()) != null) {  
	            sb.append(s);  
	        }  
	        String xml = sb.toString(); //次即为接收到微信端发送过来的xml数据  
	  

		logger.info("请求报文==="+xml);
		
		StringBuffer stringBuffer = new StringBuffer();
//		
//		stringBuffer.append("<xml>");
//		stringBuffer.append("<ToUserName><![CDATA[oI1yys69srh07f6EAWvWQc-N7Mg4]]></ToUserName>");
//		stringBuffer.append("<FromUserName><![CDATA[gh_60942cb0315f]]></FromUserName>");
//		stringBuffer.append("<CreateTime>"+System.currentTimeMillis()+"</CreateTime>");
//		stringBuffer.append("<MsgType><![CDATA[text]]></MsgType>");
//		stringBuffer.append("<Content><![CDATA[正在开发中。。]]></Content>");
//		stringBuffer.append("</xml>");
		try{
		XmlAnalysisUtil  xmlUtil = new XmlAnalysisUtil();
		Map<String,Object> map= null;
		
			logger.info("解析xml");
		map= xmlUtil.getObjectFromString(xml, "/xml");
		
		String message="";
		if(map.get("content")!=null&&map.get("content").toString().substring(0, 3).equals("查天气")){
			message=this.getWeather(map.get("content").toString().substring(4));
		}else if(map.get("content")!=null&&map.get("content").toString().substring(0, 4).equals("查身份证")){
			message=this.getIcCard(map.get("content").toString().substring(5));
		}else{
			message="格式错误";
		}
		
		logger.info("解析成功");
		stringBuffer.append("<xml>");
		stringBuffer.append("<ToUserName><![CDATA["+(map.get("fromUserName")==null?"":map.get("fromUserName"))+"]]></ToUserName>");
		stringBuffer.append("<FromUserName><![CDATA["+(map.get("toUserName")==null?"":map.get("toUserName"))+"]]></FromUserName>");
		stringBuffer.append("<CreateTime>"+System.currentTimeMillis()+"</CreateTime>");
		stringBuffer.append("<MsgType><![CDATA[text]]></MsgType>");
		stringBuffer.append("<Content><![CDATA["+message+"]]></Content>");
		stringBuffer.append("</xml>");
		logger.info("返回报文==="+stringBuffer);
		}catch(Exception e){
			logger.info(e.getStackTrace());
		}
		response.setCharacterEncoding("UTF-8");
			response.getWriter().write(stringBuffer.toString());
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request,response);
	}
	public static String getIcCard(String id){
		logger.info("身份证:"+id);
		Map<String,String> params = new HashMap<String,String>();
		params.put("id", id);
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("accept", "application/json");
		properties.put("content-type", "application/json");
		properties.put("apix-key", "c793a669df2547f572d51830da29aadb");
		try {
			String json =  unicodeToUtf8(HttpUtil.doGet("http://a.apix.cn/tongyu/idcardinfo/id", params, properties).toString()).replace("pm2.5", "pm2_5").replace("class", "cls");
			logger.info("查询到信息:"+json);
			Gson gson = new Gson();
			String res = null;
			if(json!=null){
				SFCard sf = gson.fromJson(json, SFCard.class);
				if(sf.getError_code()==0){
					 res = "性别："+(sf.getData().getGender().equals("M")?"男":"女");
					 res+="\n生日："+sf.getData().getBirthday();
					 res+="\n籍贯："+sf.getData().getAddress();
				}
			}
			return res;
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		return null;
	}
	
	public static String getWeather(String py){
		Map<String,String> params = new HashMap<String,String>();
		params.put("city", converterToSpell(py));
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("accept", "application/json");
		properties.put("content-type", "application/json");
		properties.put("apix-key", "13d10226d76048ab727f66ef061f0d32");
		try {
			String json =   unicodeToUtf8(HttpUtil.doGet("http://a.apix.cn/heweather/x3/free/weather", params, properties).toString()).replace("HeWeather data service 3.0", "weather");
			Gson gson = new Gson();
			String res = "";
			String pm = getPM(py);
			if(json.contains("alarms")){
				WeatherAlaMode mode = gson.fromJson(json, WeatherAlaMode.class);
				if(mode.getWeather().length==1){
					if(mode.getWeather()[0].getStatus().equals("ok")){
						Alarms[] ala = mode.getWeather()[0].getAlarms();
						if(ala.length>0){
							for(Alarms alarm:ala){
								res = alarm.getTitle();
								res +="\n级别："+alarm.getLevel();
								res+="\n状态："+alarm.getStat();
								res+="\n类型："+alarm.getType();
								res+="\n"+alarm.getTxt()+"\n\n";
							}
						}
					Now now = mode.getWeather()[0].getNow();
					res+=mode.getWeather()[0].getBasic().getCity()+"当前天气："+now.getTmp() +"℃  " +now.getCond().getTxt();
					res+="\n体感温度："+now.getFl()+"℃ ";
					res+="\n风力：" +now.getWind().getSpd()+" Kmph "+now.getWind().getDir();
					res+="\n湿度："+now.getHum()+"%";
					res+="\n气压："+now.getPres()+" Pa";
					res+="\n降雨量："+(now.getPcpn().equals("?")?"无":now.getPcpn()+" mm");
					res+="\n能见度："+now.getVis()+" km\n";
					if(pm!=null){
						res+=pm+"\n";
					}
					Daily_forecast[] daily_forecast = mode.getWeather()[0].getDaily_forecast();
					 if(daily_forecast.length>0){
						 for(Daily_forecast d:daily_forecast){
								res +="\n\n" +d.getDate() +" : "+ d.getTmp().getMin()+" ~ "+d.getTmp().getMax()+"℃  ";
								Cond cond = d.getCond();
								res+="\n白天："+cond.getTxt_d()+"\n夜间："+cond.getTxt_n();
								res+="\n日出："+d.getAstro().getSr()+"\n日落："+d.getAstro().getSs();
								res+="\n"+d.getWind().getSc()+" "+d.getWind().getDir();
								res+="\n风速："+d.getWind().getSpd()+" Kmph"+"\n风向："+d.getWind().getDeg()+"°";
								res+="\n降水量："+(d.getPcpn().equals("?")?"无":d.getPcpn()+" mm");
								res+="\n降水概率："+d.getPop()+"%";
								res+="\n湿度："+d.getHum()+"%";
								res+="\n气压："+d.getPres()+" Pa";
								res+="\n能见度："+d.getVis()+" km";
							}
					 }
					 res+="\n\n数据来源：中国中央气象台";
					 res+="\n更新时间："+mode.getWeather()[0].getBasic().getUpdate().getLoc();
					}
				}
			}else{
				WeatherMode mode = gson.fromJson(json, WeatherMode.class);
				if(mode.getWeather().length==1){
					if(mode.getWeather()[0].getStatus().equals("ok")){
						WeatherMode.Weather.Now now = mode.getWeather()[0].getNow();
						res+=mode.getWeather()[0].getBasic().getCity()+"当前天气："+now.getTmp() +"℃  " +now.getCond().getTxt();
						res+="\n体感温度："+now.getFl()+"℃ ";
						res+="\n风力：" +now.getWind().getSpd()+" Kmph "+now.getWind().getDir();
						res+="\n湿度："+now.getHum()+"%";
						res+="\n气压："+now.getPres()+" Pa";
						res+="\n降雨量："+(now.getPcpn().equals("?")?"无":now.getPcpn()+" mm");
						res+="\n能见度："+now.getVis()+" km\n";
						if(pm!=null){
							res+=pm+"\n";
						}
						WeatherMode.Weather.Daily_forecast[] daily_forecast = mode.getWeather()[0].getDaily_forecast();
						 if(daily_forecast.length>0){
							 for(WeatherMode.Weather.Daily_forecast d:daily_forecast){
									res +="\n\n" +d.getDate() +"："+ d.getTmp().getMin()+" ~ "+d.getTmp().getMax()+"℃  ";
									WeatherMode.Weather.Daily_forecast.Cond cond = d.getCond();
									res+="\n白天："+cond.getTxt_d()+"\n夜间："+cond.getTxt_n();
									res+="\n日出："+d.getAstro().getSr()+"\n日落："+d.getAstro().getSs();
									res+="\n"+d.getWind().getSc()+" "+d.getWind().getDir();
									res+="\n风速："+d.getWind().getSpd()+" Kmph"+"\n风向："+d.getWind().getDeg()+"°";
									res+="\n降水量："+(d.getPcpn().equals("?")?"无":d.getPcpn()+" mm");
									res+="\n降水概率："+d.getPop()+"%";
									res+="\n湿度："+d.getHum()+"%";
									res+="\n气压："+d.getPres()+" Pa";
									res+="\n能见度："+d.getVis()+" km";
								}
						 }
						 res+="\n\n数据来源：中国中央气象台";
						 res+="\n更新时间："+mode.getWeather()[0].getBasic().getUpdate().getLoc();
					}
				}
			}
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPM(String place){
		Map<String,String> params = new HashMap<String,String>();
		params.put("cityname", place);
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("accept", "application/json");
		properties.put("content-type", "application/json");
		properties.put("apix-key", "102c391ba82e4e3d7571a558b21720cd");
		try {
			String json =  unicodeToUtf8(HttpUtil.doGet("http://a.apix.cn/apixlife/pm25/PM2.5", params, properties).toString()).replace("pm2.5", "pm2_5").replace("class", "cls");
			Gson gson = new Gson();
			String res = null;
			if(json!=null){
				PmMode pm = gson.fromJson(json, PmMode.class);
				if(pm.getError_code()==0){
					res = "PM2.5："+pm.getData().getPm2_5()
							+"\n空气质量："+pm.getData().getCls();
					String bz = pm.getData().getPrimary();
					if(bz!=null&&!bz.equals("")){
						res+="  "+ bz;
					}
				}
			}
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String unicodeToUtf8(String theString) {
	       char aChar;
	       int len = theString.length();
	       StringBuffer outBuffer = new StringBuffer(len);
	       for (int x = 0; x < len;) {
	           aChar = theString.charAt(x++);
	           if (aChar == '\\') {
	               aChar = theString.charAt(x++);
	               if (aChar == 'u') {
	                   int value = 0;
	                   for (int i = 0; i < 4; i++) {
	                       aChar = theString.charAt(x++);
	                       switch (aChar) {
	                       case '0':
	                       case '1':
	                       case '2':
	                       case '3':
	                       case '4':
	                       case '5':
	                       case '6':
	                       case '7':
	                       case '8':
	                       case '9':
	                           value = (value << 4) + aChar - '0';
	                           break;
	                       case 'a':
	                       case 'b':
	                       case 'c':
	                       case 'd':
	                       case 'e':
	                       case 'f':
	                           value = (value << 4) + 10 + aChar - 'a';
	                           break;
	                       case 'A':
	                       case 'B':
	                       case 'C':
	                       case 'D':
	                       case 'E':
	                       case 'F':
	                           value = (value << 4) + 10 + aChar - 'A';
	                           break;
	                       default:
	                           throw new IllegalArgumentException(
	                                   "Malformed   \\uxxxx   encoding.");
	                       }
	                   }
	                   outBuffer.append((char) value);
	               } else {
	                   if (aChar == 't')
	                       aChar = '\t';
	                   else if (aChar == 'r')
	                       aChar = '\r';
	                   else if (aChar == 'n')
	                       aChar = '\n';
	                   else if (aChar == 'f')
	                       aChar = '\f';
	                   outBuffer.append(aChar);
	               }
	           } else
	               outBuffer.append(aChar);
	       }
	       return outBuffer.toString();
	   }
	
	 /** 
	   * 汉字转换位汉语拼音，英文字符不变 
	   * @param chines 汉字 
	   * @return 拼音 
	   */  
	   public static String converterToSpell(String chines){          
	       String pinyinName = "";  
	       char[] nameChar = chines.toCharArray();  
	       HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
	       defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
	       defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
	       for (int i = 0; i < nameChar.length; i++) {  
	           if (nameChar[i] > 128) {  
	               try {  
	                   pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];  
	               } catch (BadHanyuPinyinOutputFormatCombination e) {  
	                   e.printStackTrace();  
	               }  
	           }else{  
	               pinyinName += nameChar[i];  
	           }  
	       }  
	       return pinyinName;  
	   }
}
