package sun.bussiness;

import java.io.IOException;

import javax.websocket.Session;

import com.google.gson.Gson;

import sun.model.BuyMode;
import sun.model.UpModel;
import sun.util.SessionUtil;
import sun.util.TableFactory;

public class LvUp implements Bussiness{

	@Override
	public String bussiness(String message, Session session) {
		// TODO Auto-generated method stub
		UpModel buyMode = new Gson().fromJson(message, UpModel.class);
		String id = (String)SessionUtil.getIdList().get(session);
		String userId = TableFactory.getUserByUserId(id);
		
		try {
			((Session) SessionUtil.getSessionList().get(userId)).getBasicRemote().sendText("{\"type\":\"10\",\"resultCode\":\"0\",\"message\":\"购买成功\",\"obj\":\"1\",\"cost1\":\"0\",\"cost2\":\"-"+buyMode.getCost()+"\",\"index\":\""+buyMode.getIndex()+"\",\"lv\":\""+String.valueOf((Integer.parseInt(buyMode.getLv())+1))+"\",\"cost\":\""+String.valueOf((Integer.parseInt(buyMode.getLv())+1)*500)+"\"}");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"type\":\"10\",\"resultCode\":\"1\",\"message\":\"发生错误\"}";
		}
		return "{\"type\":\"10\",\"resultCode\":\"0\",\"message\":\"购买成功\",\"obj\":\"0\",\"cost1\":\"-"+buyMode.getCost()+"\",\"cost2\":\"0\",\"index\":\""+buyMode.getIndex()+"\",\"lv\":\""+String.valueOf((Integer.parseInt(buyMode.getLv())+1))+"\",\"cost\":\""+String.valueOf((Integer.parseInt(buyMode.getLv())+1)*500)+"\"}";

	}

}
