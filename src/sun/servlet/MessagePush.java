package sun.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import sun.util.SessionUtil;

/**
 * Servlet implementation class MessagePush
 */
@WebServlet("/MessagePush")
public class MessagePush extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map sessionList = SessionUtil.getSessionList();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessagePush() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String str = request.getParameter("id");
		String message = request.getParameter("message");
		if(("all").equals(str)){
			for (Object key : sessionList.keySet()) {
				System.out.println(key);
				System.out.println(sessionList.get(key));
				((Session)sessionList.get(key)).getBasicRemote().sendText("{\"type\":\"12\",\"resultCode\":\"0\",\"message\":\""+message+"\"}");
				  }
		}else{
			((Session)sessionList.get(str)).getBasicRemote().sendText("{\"type\":\"12\",\"resultCode\":\"0\",\"message\":\""+message+"\"}");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
