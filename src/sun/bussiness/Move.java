package sun.bussiness;

import java.io.IOException;
import java.util.Random;




import javax.websocket.Session;

import sun.util.SessionUtil;
import sun.util.TableFactory;

public class Move implements Bussiness{

	@Override
	public String bussiness(String message, Session session) {
		// TODO Auto-generated method stub
		String id = (String)SessionUtil.getIdList().get(session);
		String userId = TableFactory.getUserByUserId(id);
		Random random = new Random();
		int num = random.nextInt(6)+1;
		try {
			((Session) SessionUtil.getSessionList().get(userId)).getBasicRemote().sendText("{\"type\":\"6\",\"resultCode\":\"0\",\"message\":\"交互成功\",\"obj\":\"1\",\"num\":\""+num+"\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"type\":\"6\",\"resultCode\":\"1\",\"message\":\"发生错误\",\"obj\":\"0\",\"num\":\""+num+"\"}";
		}
		return "{\"type\":\"6\",\"resultCode\":\"0\",\"message\":\"交互成功\",\"obj\":\"0\",\"num\":\""+num+"\"}";
	}

}
