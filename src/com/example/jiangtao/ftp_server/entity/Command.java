package com.example.jiangtao.ftp_server.entity;

import java.util.Arrays;

public class Command {
	private String cmdName="";
	private String[] cmdParameter=null;
	public String getCmdName() {
		return cmdName;
	}
	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}
	public String[] getCmdParameter() {
		return cmdParameter;
	}
	public void setCmdParameter(String[] cmdParameter) {
		this.cmdParameter = cmdParameter;
	}
	@Override
	public String toString() {
		return "Command [cmdName=" + cmdName + ", cmdParameter="
				+ Arrays.toString(cmdParameter) + "]";
	}
	
}
