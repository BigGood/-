package sun.bussiness;

import java.io.IOException;

import javax.websocket.Session;

import sun.util.SessionUtil;
import sun.util.TableFactory;

public class OutGame implements Bussiness{

	@Override
	public String bussiness(String message, Session session) {
		// TODO Auto-generated method stub
		String id = (String)SessionUtil.getIdList().get(session);
		String userId = TableFactory.getUserByUserId(id);
		try {
			((Session) SessionUtil.getSessionList().get(userId)).getBasicRemote().sendText("{\"type\":\"5\",\"resultCode\":\"0\",\"message\":\"对方退出游戏\",\"obj\":\"1\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"type\":\"5\",\"resultCode\":\"1\",\"message\":\"发生错误\",\"obj\":\"0\",}";
		}
		return "{\"type\":\"5\",\"resultCode\":\"0\",\"message\":\"退出成功\",\"obj\":\"0\"}";
	}

}
