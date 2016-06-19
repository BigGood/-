package sun.servlet;



import java.io.IOException;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.tomcat.websocket.WsSession;

import sun.model.Table;
import sun.util.SessionUtil;
import sun.util.TableFactory;


@ServerEndpoint(value="/websocket")
public class CilentServlet{
	private Map sessionList = SessionUtil.getSessionList();
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		// TODO Auto-generated method stub
		System.out.println("remote:"+session.getBasicRemote());
		System.out.println("ID:"+session.getId());
		SessionUtil.getSessionList().put(((WsSession)session).getHttpSessionId(), session);
		RemoteEndpoint.Basic remote =  session.getBasicRemote();
		try {
			System.out.println("ws:"+((WsSession)session).getHttpSessionId());
			remote.sendText(session.getId()==null?"":session.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@OnMessage
	public void onMessage(String message,Session session){
		String userId = TableFactory.getUserByUserId(((WsSession)session).getHttpSessionId());
		if(userId!=null){
			try {
				((Session)(SessionUtil.getSessionList().get(userId))).getBasicRemote().sendText(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	}
	
}
