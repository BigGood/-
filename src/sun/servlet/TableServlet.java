package sun.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import sun.model.Table;
import sun.util.SessionUtil;
import sun.util.TableFactory;
@WebServlet("/enterTable")
public class TableServlet extends HttpServlet{
	private TableFactory tableFactory = TableFactory.getInstance();

	public TableServlet() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		List list = TableFactory.getTableList();
		if(list!=null){
		for (int i = 0; i < list.size(); i++) {
			if(((Table)list.get(i)).getUser().size()==1){
				((Table)list.get(i)).getUser().add(req.getSession().getId());
				//找到对战人并发送消息
				((Session)(SessionUtil.getSessionList().get(((String)(((Table)list.get(i)).getUser().get(0)))))).getBasicRemote().sendText("0");
				PrintWriter out = resp.getWriter();
				out.write("{\"tableNo\":\""+((Table)list.get(i)).getId()+"\"}");
				return;
			}
		}
		}
		Table table = tableFactory.createTable();
		List user = new ArrayList();
		System.out.println(req.getSession().getId());
		user.add(req.getSession().getId());
		table.setUser(user);
		PrintWriter out = resp.getWriter();
		out.write("{\"tableNo\":\""+table.getId()+"\",\"message\":\"0\"}");
	}
}
