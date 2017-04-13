package com.example.jiangtao.ftp_server.operator;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import com.example.jiangtao.ftp_server.cmdinterface.CommandOperator;
import com.example.jiangtao.ftp_server.cmdinterface.CommandParameterError;
import com.example.jiangtao.ftp_server.entity.Command;

public class CmdOfopen implements CommandOperator, CommandParameterError {

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
		String path = command.getCmdParameter()[0];
		try {
			Desktop.getDesktop().open(new File(path));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
