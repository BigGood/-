package sun.event;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



import sun.util.DBUtil;

public class StartListener implements ServletContextListener {
	public static HashMap<String, String> bussiness=new HashMap<String, String>();
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		InputStream in;
		Properties pps = new Properties();
		String filePath = "/opt/apache-tomcat-8.0.29/webapps/sun/WEB-INF/classes/bussiness.properties";
		System.out.println("系统加载文件:["+filePath+"]");
	    try {
		in = new BufferedInputStream(new FileInputStream(filePath));
		pps.load(in);
		Enumeration en = pps.propertyNames(); //得到配置文件的名字
		while(en.hasMoreElements()) {
		       String key = (String) en.nextElement();
	           String value = pps.getProperty(key);
	           bussiness.put(key, value);
		 }
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
