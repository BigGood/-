package sun.bussiness;

import java.io.IOException;

import javax.websocket.Session;

import com.google.gson.Gson;

import sun.model.BuyMode;
import sun.model.costModel;
import sun.util.SessionUtil;
import sun.util.TableFactory;

public class Cost implements Bussiness{

	@Override
	public String bussiness(String message, Session session) {
		// TODO Auto-generated method stub
		costModel buyMode = new Gson().fromJson(message, costModel.class);
		String id = (String)SessionUtil.getIdList().get(session);
		String userId = TableFactory.getUserByUserId(id);
		
		try {
			((Session) SessionUtil.getSessionList().get(userId)).getBasicRemote().sendText("{\"type\":\"9\",\"resultCode\":\"0\",\"message\":\"购买成功\",\"obj\":\"1\",\"cost1\":\""+buyMode.getCost()+"\",\"cost2\":\"-"+buyMode.getCost()+"\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"type\":\"9\",\"resultCode\":\"1\",\"message\":\"发生错误\"}";
		}
		return "{\"type\":\"9\",\"resultCode\":\"0\",\"message\":\"购买成功\",\"obj\":\"0\",\"cost1\":\"-"+buyMode.getCost()+"\",\"cost2\":\""+buyMode.getCost()+"\"}";
	}

}
