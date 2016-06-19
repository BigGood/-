package sun.bussiness;

import java.io.IOException;

import javax.websocket.Session;

import com.google.gson.Gson;

import sun.model.BuyMode;
import sun.model.userSchema;
import sun.util.SessionUtil;
import sun.util.SinoDB;
import sun.util.TableFactory;

public class Buy implements Bussiness{

	@Override
	public String bussiness(String message, Session session) {
		// TODO Auto-generated method stub
		BuyMode buyMode = new Gson().fromJson(message, BuyMode.class);
		String id = (String)SessionUtil.getIdList().get(session);
		String userId = TableFactory.getUserByUserId(id);
		
		try {
			((Session) SessionUtil.getSessionList().get(userId)).getBasicRemote().sendText("{\"type\":\"8\",\"resultCode\":\"0\",\"message\":\"购买成功\",\"obj\":\"1\",\"cost1\":\"0\",\"cost2\":\"-"+buyMode.getCost()+"\",\"index\":\""+buyMode.getIndex()+"\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"type\":\"8\",\"resultCode\":\"1\",\"message\":\"发生错误\"}";
		}
		return "{\"type\":\"8\",\"resultCode\":\"0\",\"message\":\"购买成功\",\"obj\":\"0\",\"cost1\":\"-"+buyMode.getCost()+"\",\"cost2\":\"0\",\"index\":\""+buyMode.getIndex()+"\"}";

	}

}
