package com.cnmobi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class SocketTest {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			
			Socket socket = serverSocket.accept();
			
			InputStream bf = socket.getInputStream();
		
			String tempString="";
			String laststr="";
			int n = 1024;   
	        byte buffer[] = new byte[n];   
	        // 读取输入流
	       
	        bf.read(buffer);
	        laststr+= new String(buffer);
	        String[] strs=laststr.split("\r\n\r\n");	
	        System.out.println(strs[0]);	
	        System.out.println(strs[1]);
			
	        
	
			OutputStreamWriter ow = new OutputStreamWriter(socket.getOutputStream()); 
			BufferedWriter bw = new BufferedWriter(ow);
			bw.write("go");
			
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
