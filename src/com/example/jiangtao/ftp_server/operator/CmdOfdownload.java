package com.example.jiangtao.ftp_server.operator;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import com.example.jiangtao.ftp_server.cmdinterface.CommandOperator;
import com.example.jiangtao.ftp_server.cmdinterface.CommandParameterError;
import com.example.jiangtao.ftp_server.entity.Command;

public class CmdOfdownload implements CommandOperator, CommandParameterError {

	@Override
	public void cmdRun(Socket socket, Command command) {
		// TODO Auto-generated method stub
		if(command.getCmdParameter().length !=1 )
		{
			try {
				this.errorExecute(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		System.out.println("执行了download");
		String path = (command.getCmdParameter())[0];
		File file =new File(path);
		if(file.isDirectory())
		{
			try {
				this.errorExecute(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		try {
			FileInputStream fin = new FileInputStream(file);
			
			DataOutputStream dout =new DataOutputStream(socket.getOutputStream());
			
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

}
