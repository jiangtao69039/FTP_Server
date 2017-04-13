package com.example.jiangtao.ftp_server.cmdinterface;

import java.net.Socket;

import com.example.jiangtao.ftp_server.entity.Command;

public interface CommandOperator {
	
	public void cmdRun(Socket socket,Command command);
	
}
