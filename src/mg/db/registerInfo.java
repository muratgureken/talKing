package mg.db;

import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class registerInfo {
	private static LinkedList<Integer> idList = new LinkedList<Integer>();
	private static LinkedList<String> usrList = new LinkedList<String>();
	private static LinkedList<Socket> socketList = new LinkedList<Socket>();
	private static LinkedList<String> ipList = new LinkedList<String>();
	private static LinkedList<Boolean> connStateList = new LinkedList<Boolean>();
	public HashMap<Integer, Socket> socketLib = new HashMap<Integer, Socket>();

	public LinkedList<Integer> getIdList() {
		return idList;
	}

	public void setIdList(Integer idList) {
		this.idList.add(idList);
	}

	public LinkedList<String> getUsrList() {
		return usrList;
	}

	public void setUsrList(LinkedList<String> usrList) {
		this.usrList = usrList;
	}

	public LinkedList<Socket> getSocketList() {
		return socketList;
	}

	public void setSocketList(Socket socketList) {
		this.socketList.add(socketList);
	}

	public LinkedList<String> getIpList() {
		return ipList;
	}

	public void setIpList(LinkedList<String> ipList) {
		this.ipList = ipList;
	}

	public LinkedList<Boolean> getConnStateList() {
		return connStateList;
	}

	public void setConnStateList(LinkedList<Boolean> connStateList) {
		this.connStateList = connStateList;
	}
}
