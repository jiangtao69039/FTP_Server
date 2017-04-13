package com.example.jiangtao.ftp_server.socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

import com.example.jiangtao.ftp_server.entity.Command;
import com.example.jiangtao.ftp_server.survice.CmdExecute;
import com.example.jiangtao.ftp_server.survice.CmdParse;

public class CmdServerSocket {
	private ServerSocket serversocket;
	public CmdServerSocket(){
		
	}
	
	public void work(){
		try {
			serversocket = new ServerSocket(9095);
			System.out.println("服务器启动");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new AcceptClientSocket().start();
	}
	
	
	class AcceptClientSocket extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(this.isAlive())
			{
				try {
					Socket socket = serversocket.accept();
					if(socket!=null){
						System.out.println("客户端连接："+socket.getInetAddress());
						new ExecuteClientSocket(socket).start();
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
			
		}

	}
	
	
	class ExecuteClientSocket extends Thread{
		private Socket socket;
		public ExecuteClientSocket(Socket socket){
			this.socket=socket;
			try {
				this.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public void run() {
			try {
				this.sleep(100);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
			    String cmd = reader.readLine();
			    System.out.println("命令收到："+cmd);
			    
			   // reader.close();
			    /////////////////////////
			    //命令解析操作__--->  parse(cmd)
			    /////////////////////////
			    //解析命令和参数
			    CmdParse cmdparse = CmdParse.getInstance();
			    Command command = cmdparse.parse(cmd); 
			    //执行命令
			    CmdExecute cmdexecute = CmdExecute.getInstance();
			    cmdexecute.execute(command, socket);	    
			    /////////////////////////
			   
			    //socket.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	

}



