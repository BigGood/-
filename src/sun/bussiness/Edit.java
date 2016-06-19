package sun.bussiness;

import javax.websocket.Session;

import sun.model.EditModel;
import sun.model.userSchema;
import sun.util.SessionUtil;
import sun.util.SinoDB;

import com.google.gson.Gson;

public class Edit implements Bussiness{

	@Override
	public String bussiness(String message, Session session) {
		// TODO Auto-generated method stub
		EditModel edit = new Gson().fromJson(message, EditModel.class);
		userSchema schema = new userSchema();
		schema.setPassword(edit.getPassword());
		schema.setId((String)SessionUtil.getIdList().get(session));
		schema = (userSchema)SinoDB.findOne(schema);
		if(schema.getUserName()==null||schema.getUserName().isEmpty()){
			return "{\"type\":\"7\",\"resultCode\":\"1\",\"message\":\"原密码错误\"}";
		}else{
			schema.setPassword(edit.getNewPassword());
			SinoDB.update(schema);
			return "{\"type\":\"7\",\"resultCode\":\"0\",\"message\":\"修改密码成功\"}";
		}
	}

}
