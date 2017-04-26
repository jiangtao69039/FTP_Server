package com.example.jiangtao.ftp_server.operator;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.example.jiangtao.ftp_server.cmdinterface.CommandOperator;
import com.example.jiangtao.ftp_server.cmdinterface.CommandParameterError;
import com.example.jiangtao.ftp_server.entity.Command;
import com.example.jiangtao.ftp_server.constant.ParseResultMsg;

public class CmdOfupload implements CommandOperator, CommandParameterError {

	@Override
	public void cmdRun(Socket socket, Command command) {
		// TODO Auto-generated method stub
		if(command.getCmdParameter().length !=1 ) //有且只有一个参数，表示文件名
		{
			try {
				this.errorExecute(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		String filename = command.getCmdParameter()[0];
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
			DataInputStream din = new DataInputStream(socket.getInputStream());
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

}
