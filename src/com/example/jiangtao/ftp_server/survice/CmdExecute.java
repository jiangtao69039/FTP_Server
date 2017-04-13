package com.example.jiangtao.ftp_server.survice;

import java.awt.Desktop;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.filechooser.FileSystemView;

import com.example.jiangtao.ftp_server.entity.Command;
import com.example.jiangtao.ftp_server.globalclass.ParseResultMsg;

public class CmdExecute {
		
	private Command command;
	private Socket socket;
	private CmdExecute(){
		
	}
	
	private static class GetInstance{
		private static CmdExecute instance = new CmdExecute();
	}
	
	public static CmdExecute getInstance(){
		return GetInstance.instance;
	}
	
	public void execute (Command command,Socket socket)throws IOException{
		this.command=command;
		this.socket= socket;
		switch(this.command.getCmdName())
		{
			case ParseResultMsg.ERROR:
				
				break;
			case ParseResultMsg.LS:
				cmd_ls();
				break;
			case ParseResultMsg.DIR:
				cmd_ls();
				break;
			case ParseResultMsg.DOWNLOAD:
				cmd_download();
				break;
			case ParseResultMsg.UPLOAD:
				cmd_upload();
				break;
				
			case ParseResultMsg.OPEN:
				cmd_open();
				break;
			default:
				
				break;
		}
		
	}
	
	
	private void cmd_Error(){
		
		/////
		//命令错误时的执行代码
	}
	private void cmd_open(){
		if(this.command.getCmdParameter().length !=1 ) //有且只有一个参数，表示文件名
		{
			cmd_Error();
			return;
		}
		String path = this.command.getCmdParameter()[0];
		try {
			Desktop.getDesktop().open(new File(path));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void cmd_upload(){
		if(this.command.getCmdParameter().length !=1 ) //有且只有一个参数，表示文件名
		{
			cmd_Error();
			return;
		}
		String filename = this.command.getCmdParameter()[0];
		String path = ParseResultMsg.DEFAULT_UPLOAD_PATH+filename;
		
		File file =new File(path);
		if(!file.exists())
		{
			try {
				file.createNewFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fout = new FileOutputStream(file);
			DataInputStream din = new DataInputStream(this.socket.getInputStream());
			byte[] bytearray = new byte[1024];
			int len;
			while((len=din.read(bytearray,0,1024))!=-1)
			{
				fout.write(bytearray, 0, len);
				fout.flush();
			}
			fout.close();
			System.out.println("上传文件成功");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void cmd_download(){
		if(this.command.getCmdParameter().length !=1 )
		{
			cmd_Error();
			return;
		}
		System.out.println("执行了download");
		String path = (this.command.getCmdParameter())[0];
		File file =new File(path);
		if(file.isDirectory())
		{
			cmd_Error();
			return;
		}
		try {
			FileInputStream fin = new FileInputStream(file);
			
			DataOutputStream dout =new DataOutputStream(this.socket.getOutputStream());
			
			byte[] bytearray = new byte[1024];
			int len;
			while((len=fin.read(bytearray,0,1024))!=-1)
			{
				dout.write(bytearray, 0, len);
				
			}
			dout.flush();
			System.out.println("返回了download数据");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	private void cmd_ls() throws IOException{
		if(this.command.getCmdParameter().length >1)
		{
			cmd_Error();
			return;
		}
		else if(this.command.getCmdParameter().length ==0)
		{
			FileSystemView sys = FileSystemView.getFileSystemView();
			
			File[] files= File.listRoots();
			for(File file:files)
				System.out.println(file.toString()+"  "+sys.getSystemTypeDescription(file)
						+file.getParent());
			String totalline = files.length+"";
			PrintWriter writer =new PrintWriter(this.socket.getOutputStream());
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
		else if(this.command.getCmdParameter().length ==1)
		{
		String path = (this.command.getCmdParameter())[0];
		
		System.out.println("执行了ls——execute;路径是:"+path);
		File file=null;
		try{
		 file = new File(path);
		 File[] filelist = file.listFiles();
		 if(filelist == null)
		 {
			String totalline = 0+"";
			PrintWriter writer =new PrintWriter(this.socket.getOutputStream());
			writer.println(totalline);
			writer.flush();
			return;
		 }
			String totalline = filelist.length+"";
			PrintWriter writer =new PrintWriter(this.socket.getOutputStream());
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
			
			}
		}
		else
		{
			cmd_Error();
			return;
		}
		
		
	}
	
}
