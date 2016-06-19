package sun.bussiness;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import sun.model.Table;
import sun.util.SessionUtil;
import sun.util.TableFactory;

public class Enter implements Bussiness{
	private TableFactory tableFactory = TableFactory.getInstance();
	@Override
	public String bussiness(String message, Session session) {
		
		// TODO Auto-generated method stub
		List list = TableFactory.getTableList();
		if(list!=null){
		for (int i = 0; i < list.size(); i++) {
			if(((Table)list.get(i)).getUser().size()==1){
				if((((Table)list.get(i)).getUser().get(0)).equals(SessionUtil.getIdList().get(session))){
					continue;
				}
				((Table)list.get(i)).getUser().add(SessionUtil.getIdList().get(session));
				//找到对战人并发送消息
				try {
					((Session)(SessionUtil.getSessionList().get(((String)(((Table)list.get(i)).getUser().get(0)))))).getBasicRemote().sendText("{\"type\":\"4\",\"resultCode\":\"0\",\"message\":\"进入游戏\"}");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "{\"type\":\"4\",\"resultCode\":\"1\",\"message\":\"进入失败\"}";
				}
				System.out.println(TableFactory.getTableList());
				System.out.println(SessionUtil.getIdList());
				System.out.println(SessionUtil.getSessionList());
				return "{\"type\":\"4\",\"resultCode\":\"0\",\"message\":\"进入游戏\"}";
			}
		}
		}
		Table table = tableFactory.createTable();
		List user = new ArrayList();
		user.add(SessionUtil.getIdList().get(session));
		table.setUser(user);
		return "{\"type\":\"4\",\"resultCode\":\"2\",\"message\":\"等待玩家加入\"}";
	}

}
