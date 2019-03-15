package mg.db;

import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

public class registerInfo {
	private LinkedList<Integer> idList;
	private LinkedList<String> usrList;
	private LinkedList<Integer> socketList;
	private LinkedList<String> ipList;
	private LinkedList<Boolean> connStateList;
        public HashMap<Integer, Socket> socketLib = new HashMap<Integer, Socket>();

	public LinkedList<Integer> getIdList() {
		return idList;
	}

	public void setIdList(LinkedList<Integer> idList) {
		this.idList = idList;
	}

	public LinkedList<String> getUsrList() {
		return usrList;
	}

	public void setUsrList(LinkedList<String> usrList) {
		this.usrList = usrList;
	}

	public LinkedList<Integer> getSocketList() {
		return socketList;
	}

	public void setSocketList(LinkedList<Integer> socketList) {
		this.socketList = socketList;
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
