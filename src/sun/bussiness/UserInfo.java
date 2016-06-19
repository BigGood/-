package sun.bussiness;

import java.io.IOException;

import javax.websocket.Session;

import sun.model.userSchema;
import sun.util.SessionUtil;
import sun.util.SinoDB;
import sun.util.TableFactory;

public class UserInfo implements Bussiness{

	@Override
	public String bussiness(String message, Session session) {
		// TODO Auto-generated method stub
		String id = (String)SessionUtil.getIdList().get(session);
		String userId = TableFactory.getUserByUserId(id);
		userSchema user1 = new userSchema();
		user1.setId(id);
		user1= (userSchema)SinoDB.findOne(user1);
		
		userSchema user2 = new userSchema();
		user2.setId(userId);
		user2= (userSchema)SinoDB.findOne(user2);
		
		try {
			((Session) SessionUtil.getSessionList().get(userId)).getBasicRemote().sendText("{\"type\":\"11\",\"resultCode\":\"0\",\"message\":\"交互成功\",\"id1\":\"1\",\"user1Name\":\""+user1.getUserName()+"\",\"id2\":\"2\",\"user2Name\":\""+user2.getUserName()+"\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"type\":\"11\",\"resultCode\":\"1\",\"message\":\"发生错误\"}";
		}
		return "{\"type\":\"11\",\"resultCode\":\"0\",\"message\":\"交互成功\",\"id1\":\"2\",\"user1Name\":\""+user2.getUserName()+"\",\"id2\":\"1\",\"user2Name\":\""+user1.getUserName()+"\"}";
	}

}
