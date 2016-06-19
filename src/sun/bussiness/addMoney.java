package sun.bussiness;

import java.io.IOException;

import javax.websocket.Session;

import sun.util.SessionUtil;
import sun.util.TableFactory;

public class addMoney implements Bussiness{

	@Override
	public String bussiness(String message, Session session) {
		// TODO Auto-generated method stub
		String id = (String)SessionUtil.getIdList().get(session);
		String userId = TableFactory.getUserByUserId(id);
		try {
			((Session) SessionUtil.getSessionList().get(userId)).getBasicRemote().sendText("{\"type\":\"13\",\"resultCode\":\"0\",\"message\":\"奖励成功\",\"obj\":\"1\",\"cost1\":\"0\",\"cost2\":\"2000\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"type\":\"13\",\"resultCode\":\"1\",\"message\":\"发生错误\"}";
		}
		return "{\"type\":\"13\",\"resultCode\":\"0\",\"message\":\"奖励成功\",\"obj\":\"0\",\"cost1\":\"2000\",\"cost2\":\"0\"}";

	}

}
