package sun.bussiness;

import java.sql.SQLException;

import javax.websocket.Session;

import com.google.gson.Gson;

import sun.model.userSchema;
import sun.util.DBUtil;
import sun.util.SessionUtil;
import sun.util.SinoDB;
import sun.util.StringUtil;

public class Login implements Bussiness{

	@Override
	public String bussiness(String message,Session session) {
		// TODO Auto-generated method stub
		
		userSchema userSchema = new Gson().fromJson(message, userSchema.class);
		userSchema=(userSchema) SinoDB.findOne(userSchema);
		SessionUtil.getSessionList().put(userSchema.getId(), session);
		SessionUtil.getIdList().put(session,userSchema.getId());
		if(userSchema.getId()!=null&&!userSchema.getId().isEmpty()){
			return "{\"type\":\"2\",\"resultCode\":\"0\",\"message\":\"登录成功\",\"userId\":\""+userSchema.getId()+"\"}";
		}
		
		
		return "{\"type\":\"2\",\"resultCode\":\"1\",\"message\":\"账号或密码错误\"}";
	}
	
}
