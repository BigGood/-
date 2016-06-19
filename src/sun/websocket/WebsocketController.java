package sun.websocket;



import java.io.IOException;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.tomcat.websocket.WsSession;

import sun.bussiness.Bussiness;
import sun.event.StartListener;
import sun.model.Table;
import sun.util.SessionUtil;
import sun.util.TableFactory;


@ServerEndpoint(value="/websocket1")
public class WebsocketController{
	private Map sessionList = SessionUtil.getSessionList();
	private Map IDList = SessionUtil.getIdList();
	//监听连接
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		// TODO Auto-generated method stub		
		System.out.println("连接session为"+session.getId());
		System.out.println("连接session为:"+session);
//		try {
//			remote.sendText(session.getId()==null?"":session.getId());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	//接收消息
	@OnMessage
	public void onMessage(String message,Session session){
//		String userId = TableFactory.getUserByUserId(((WsSession)session).getHttpSessionId());
//		if(userId!=null){
		if(("exit").equals(message)){
			TableFactory.getTableList().remove(TableFactory.getTableByUserId((String)(IDList.get(session))));
			return;
		}
			try {
				int statrtIn=message.indexOf(":");
				int endIn= message.indexOf(",");
				String bussinessType = message.substring(statrtIn+1, endIn);
				String buClass = StartListener.bussiness.get(bussinessType.replaceAll("\"", ""));
				Bussiness bussiness = (Bussiness)Class.forName(buClass).newInstance();
				System.out.println("发送session为"+session.getId());
				System.out.println("发送session为:"+session);
				session.getBasicRemote().sendText(bussiness.bussiness(message.substring(endIn+8, message.length()-1),session));
				//((Session)(SessionUtil.getSessionList().get(userId))).getBasicRemote().sendText(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
//		}
	}
	//关闭连接
	@OnClose
	public void onClose(Session session){
		String id = (String)SessionUtil.getIdList().get(session);
		String userId = TableFactory.getUserByUserId(id);
		if(userId!=null){
			try {
				((Session) SessionUtil.getSessionList().get(userId)).getBasicRemote().sendText("{\"type\":\"5\",\"resultCode\":\"0\",\"message\":\"对方退出游戏\",\"obj\":\"1\"}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TableFactory.getInstance().getTableList().remove(TableFactory.getTableByUserId(id));
		sessionList.remove(IDList.get(session));
		IDList.remove(session);
		
	}
	//消息发送
	public void sendMessage(String message,Session session) throws IOException{
		session.getBasicRemote().sendText(message);
	}
}
