package sun.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

public class SessionUtil {
	private static Map<String, Session> sessionMap ;
	private static Map<Session, String> idMap ;
	public SessionUtil() {
		// TODO Auto-generated constructor stub
	}
	public static Map getSessionList(){
		if(sessionMap==null){
			sessionMap = new HashMap<>();
		}
		return sessionMap;
	}
	public static Map getIdList(){
		if(idMap==null){
			idMap = new HashMap<>();
		}
		return idMap;
	}

}
