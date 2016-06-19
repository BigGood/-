package sun.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import sun.servlet.test;

public class Start  implements ServletContextListener {
	private static Logger logger = Logger.getLogger(Start.class);
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		logger.info("服务准备停止....");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		logger.info("服务启动完毕....");
		
	}

}
