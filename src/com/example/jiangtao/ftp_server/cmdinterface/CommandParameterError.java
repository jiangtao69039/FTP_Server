package com.example.jiangtao.ftp_server.cmdinterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.example.jiangtao.ftp_server.constant.ParseResultMsg;

public interface CommandParameterError {
	
	default public void errorExecute(Socket socket) throws IOException{
		//PrintWriter pw = new PrintWriter(socket.getOutputStream());
		//pw.println(ParseResultMsg.RESPONSE_PARAMETER_ERROR);
		
	}
}
