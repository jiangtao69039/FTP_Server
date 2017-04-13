package com.example.jiangtao.ftp_server.survice;

import com.example.jiangtao.ftp_server.entity.Command;
import com.example.jiangtao.ftp_server.globalclass.ParseResultMsg;



public class CmdParse {
	
	
	
	private CmdParse(){
		
	}
	//静态内部类保证多线程环境下的安全问题
	private static class GetInstance{
		private static CmdParse instance = new CmdParse();
	}
	public static CmdParse getInstance(){
		return GetInstance.instance;
	}
	
	synchronized public Command parse(String cmd){
		Command command = new Command();
		String[] arr = cmd.split("\\s+");//匹配一个或多个空格
		String cmdName = arr[0];
		if(checkCmdName(cmdName))
		{
			
			int size = arr.length-1;
			String[] cmdParameter = new String[size];
			for(int i=1,j=arr.length,k=0;i<j;i++,k++)
			{
				cmdParameter[k]=arr[i];
			}
			command.setCmdName(cmdName);
			command.setCmdParameter(cmdParameter);
			System.out.println("解析命令成功，命令是："+command.toString());
			
		}
		else
		{
			command.setCmdName(ParseResultMsg.ERROR);
			command.setCmdParameter(null);
		}
		
		
		
		return command;
	}
	
	public boolean checkCmdName(String cmdname){
		boolean currect=false;
		switch(cmdname)
		{
			case ParseResultMsg.LS:
				currect=true;
				break;
			case ParseResultMsg.DIR:
				currect=true;
				break;
			case ParseResultMsg.DOWNLOAD:
				currect=true;
				break;
			case ParseResultMsg.UPLOAD:
				currect=true;
				break;
			case ParseResultMsg.OPEN:
				currect=true;
				break;
			default:
				currect = false;
				break;
		}
		return currect;
		
	}
	
	
}
