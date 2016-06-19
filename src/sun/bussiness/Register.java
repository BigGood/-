package sun.bussiness;

import java.util.UUID;

import javax.websocket.Session;

import sun.model.userSchema;
import sun.util.DateUtil;
import sun.util.SinoDB;

import com.google.gson.Gson;

public class Register implements Bussiness{
	
	@Override
	public String bussiness(String message,Session session) {
		// TODO Auto-generated method stub
		userSchema userSchema = new Gson().fromJson(message, userSchema.class);
		UUID uuid = UUID.randomUUID();
		String id = UUID.randomUUID().toString();
		id=id.replace("-",""); 
		userSchema.setId(id);
		userSchema user = new userSchema();
		user.setUserName(userSchema.getUserName());
		user = (userSchema)SinoDB.findOne(user);
		if(user.getId()!=null&&!user.getId().isEmpty()){
			return	"{\"type\":\"1\",\"resultCode\":\"1\",\"message\":\"用户名已存在\"}";
		}
		if(SinoDB.save(userSchema)){
			return "{\"type\":\"1\",\"resultCode\":\"0\",\"message\":\"注册成功\",\"userId\":\""+userSchema.getId()+"\"}";
		}
		return "{\"type\":\"1\",\"resultCode\":\"1\",\"message\":\"注册失败\"}";
	}
	
	
	
	
}
