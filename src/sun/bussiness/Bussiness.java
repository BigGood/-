package sun.bussiness;

import javax.websocket.Session;

public interface Bussiness {
	public String bussiness(String message, Session session);
}
