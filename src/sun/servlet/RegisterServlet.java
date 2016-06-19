package sun.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import sun.bussiness.Bussiness;
import sun.event.StartListener;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader bufferedReader  = request.getReader();
		String message="";
		String str="";
		while ((str =bufferedReader.readLine())!=null){
			message=message+str;
		}
		try {
		int statrtIn=message.indexOf(":");
		int endIn= message.indexOf(",");
		String bussinessType = message.substring(statrtIn+1, endIn);
		String buClass = StartListener.bussiness.get(bussinessType.replaceAll("\"", ""));
		Bussiness bussiness = (Bussiness)Class.forName(buClass).newInstance();
		String requestMessage = bussiness.bussiness(message.substring(endIn+8, message.length()-1),null);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(requestMessage);
		response.getWriter().flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write("系统异常");
			response.getWriter().flush();
		} 
		
	}

}
