package com.example.jiangtao.ftp_server.app;

import com.example.jiangtao.ftp_server.socket.CmdServerSocket;

public class ServerSocketApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CmdServerSocket cmdserversocket = new CmdServerSocket();
		cmdserversocket.work();
	}

}
