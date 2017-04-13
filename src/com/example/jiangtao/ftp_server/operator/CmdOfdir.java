package com.example.jiangtao.ftp_server.operator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.filechooser.FileSystemView;

import com.example.jiangtao.ftp_server.cmdinterface.CommandOperator;
import com.example.jiangtao.ftp_server.cmdinterface.CommandParameterError;
import com.example.jiangtao.ftp_server.entity.Command;

public class CmdOfdir implements CommandOperator,CommandParameterError {

	
	
	@Override
	public void cmdRun(Socket socket, Command command) {
		// TODO Auto-generated method stub
		if(command.getCmdParameter().length >1)
		{
			//cmd_Error();
			try {
				this.errorExecute(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		else if(command.getCmdParameter().length ==0)
		{
			FileSystemView sys = FileSystemView.getFileSystemView();
			
			File[] files= File.listRoots();
			for(File file:files)
				System.out.println(file.toString()+"  "+sys.getSystemTypeDescription(file)
						+file.getParent());
			String totalline = files.length+"";
			PrintWriter writer=null;
			try {
				writer = new PrintWriter(socket.getOutputStream());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writer.println(totalline);
			for(File f:files)
			{
				
				
				String send = f.toString()+"  "+sys.getSystemTypeDescription(f)+">"+" "+
						">"+" "+">"+f.isDirectory()+">"+f.getAbsolutePath()+">"+f.getParent()+">";
				System.out.println("当前:"+f.getAbsolutePath()+"p:"+
						f.getParent());
				writer.println(send);
				
			}
			writer.flush();
			
		}
		else if(command.getCmdParameter().length ==1)
		{
		String path = (command.getCmdParameter())[0];
		
		System.out.println("执行了ls——execute;路径是:"+path);
		File file=null;
		try{
		 file = new File(path);
		 File[] filelist = file.listFiles();
		 if(filelist == null)
		 {
			String totalline = 0+"";
			PrintWriter writer =new PrintWriter(socket.getOutputStream());
			writer.println(totalline);
			writer.flush();
			return;
		 }
			String totalline = filelist.length+"";
			PrintWriter writer =new PrintWriter(socket.getOutputStream());
			writer.println(totalline);
			for(File f:filelist)
			{
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(f.lastModified());
				
				String send = f.getName()+">"+cal.getTime().toLocaleString()+
						">"+f.length()/1024+"KB"+">"+f.isDirectory()+">"+f.getAbsolutePath()+">"+
						f.getParentFile().getParent()+">";
				
				//System.out.println("当前:"+f.getAbsolutePath()+"p:"+
				//		f.getParent());
				writer.println(send);
				
			}
			writer.flush();
		//	writer.close();
			System.out.println("返回数据了");
		}catch(NullPointerException e){
			
			e.printStackTrace();
			
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else
		{
			try {
				this.errorExecute(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
	}
	

}
