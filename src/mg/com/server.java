package mg.com;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mg.db.registerInfo;
import mg.db.userdao;

public class server extends registerInfo{
	/*private static String url="jdbc:postgresql://127.0.0.1:5432/talktome";
	private static String username="postgres";
	private static String password="sifre123";*/
	private Connection conn;
	private Socket[]  socket = new Socket[1000]; 
	private ServerSocket    server   = null; 
	private DataInputStream in       =  null; 
	private DataOutputStream out     = null; 
	private int maxThreadNumber=100, socketCounter=0;
	private Thread[] threads = new Thread[maxThreadNumber];
	private boolean[] states = new boolean[maxThreadNumber];     
	private int maxIdValue=0;
	public HashMap<Integer, Socket> socketLibLocal = new HashMap<Integer, Socket>();
	public HashMap<Integer, Integer> threadLib = new HashMap<Integer, Integer>();


	public server(int port) throws SQLException{

		userdao dao = new userdao();
		messageProtocol msgp = new messageProtocol();

		/*try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, password); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/

		//conn.close();
		dao.bringAllRegisters();
		dao.makeAllRegistersPassive();

		try {
			server = new ServerSocket(port);
			server.setSoTimeout(100000);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		for(int i=0; i<maxThreadNumber; i++)
		{
			states[i] = false;
		}

		while(true)
		{
			// starts server and waits for a connection 
			try
			{ 
				System.out.println("Server started"); 

				System.out.println("Waiting for a client ..."); 

				socket[socketCounter] = server.accept(); 
				System.out.println("Client accepted socket id : "+socketCounter+" socket : "+socket[socketCounter]); 
				socketCounter++;

				//her accepten sonra bir thread olustur.
				int threadIndex = availableThread();
				if(threadIndex!=-1)
				{
					states[threadIndex] = true;

					threads[threadIndex] = new Thread()
					{
						private int socketLokalInd = socketCounter-1;
						private int grupNo;
						private int receiveId, messageSize;
						private String messageIn;	
						private LinkedList<Integer> sendIds = new LinkedList<Integer>();

						String line = "";   

						public void run()
						{								
							try {
								in = new DataInputStream(
										new BufferedInputStream(socket[socketLokalInd].getInputStream()));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}                                                                              
							for(;;)
							{
								if(states[threadIndex])
								{
									try
									{						
										// takes input from the client socket
										line ="";
										//reads message
										line = in.readUTF();
										//read message id
										int clientId = Integer.parseInt(line.substring(0,1));
										int grupCount, connState;
										int senderId, receiverId=0;
										Socket socketLokal;
										String messageToSend, messageTcp, usrname, usrpassword;
										boolean contChat=true;
										switch(clientId)
										{
										case 1:
											//receive client chat message
											grupCount = Integer.parseInt(line.substring(1,9));                                                                                        
											senderId  = Integer.parseInt(line.substring(9,17));
											messageToSend = line.substring(17+grupCount*8);
											for(int i=0;i<grupCount;i++)    
											{
												receiverId = Integer.parseInt(line.substring(17+i*8,17+(i+1)*8));
												//find receiver socket number
												socketLokal = socketLib.get(receiverId);
												//send message
												int index = getIdList().indexOf(receiverId);
												messageTcp = msgp.serverMessage(getUsrList().get(index), messageToSend);
												out = new DataOutputStream(socketLokal.getOutputStream()); 
												out.writeUTF(messageTcp);
												//out.close();
											}
											break;
										case 2:
											System.out.println("2 nolu mesaj:"+line);
											senderId  = Integer.parseInt(line.substring(1,9));
											connState = Integer.parseInt(line.substring(9,10));
											if(connState==0)
											{
												//kill thread
												contChat = false;
												states[threadIndex] = false;
											}
											//send update to all users
											//update lists
											int indexlocal = getIdList().indexOf(senderId);
											getConnStateList().set(indexlocal, states[threadIndex]);

											messageTcp = msgp.allDatabaseMessage(getIdList(), getUsrList(), getConnStateList());
											System.out.println("server: alldatabase: "+messageTcp);
											System.out.println("Client gonder mesajID:2 socket id : "+socketLokalInd+" socket : "+socket[socketLokalInd]); 
											out = new DataOutputStream(socket[socketLokalInd].getOutputStream()); 
											out.writeUTF(messageTcp);
											//out.close();
											break;
										case 3:
											//register user to database
											maxId();
											maxIdValue++;
											System.out.println("3 nolu mesaj: boy:"+line.substring(1,9)+", "+Integer.parseInt(line.substring(1,9)));
											messageSize = Integer.parseInt(line.substring(1,9));
											usrname = line.substring(9,9+messageSize);
											usrpassword = line.substring(9+messageSize);

											try {
												dao.insertRegister(maxIdValue, usrname, usrpassword);
											} catch (SQLException ex) {
												Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
											}

											//update lists
											getIdList().add(maxIdValue);
											getUsrList().add(usrname);
											getConnStateList().add(true);

											//send update to all users

											//addvalues to hashmaps
											socketLibLocal.put(maxIdValue, socket[socketLokalInd]);
											threadLib.put(maxIdValue, threadIndex);
											//register user socket number
											socketLib.put(receiverId, socket[socketLokalInd]);
											messageTcp = msgp.IdResponseMessage(maxIdValue);
											out = new DataOutputStream(socket[socketLokalInd].getOutputStream()); 
											out.writeUTF(messageTcp);
											//out.close();
											break;
										case 4:
											messageTcp = msgp.allDatabaseMessage(getIdList(), getUsrList(), getConnStateList());
											out = new DataOutputStream(socket[socketLokalInd].getOutputStream()); 
											out.writeUTF(messageTcp);
											//out.close();
											break;
										case 5:
											senderId  = Integer.parseInt(line.substring(1,9));
											try {
												closeMessagging(senderId);
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											break;
										default:
											break;
										}

										System.out.println(line);                     
									} 
									catch(IOException ex) 
									{ 
										Logger.getLogger(server.class.getName()).log(Level.SEVERE, null,ex); 
									}  
								}
								else
								{
									break;
								}
							}

						}
					};
					threads[threadIndex].start();
				}
				else
				{
					System.out.println("Thread pool max size reached!...");     
				}

			} 
			catch(IOException i) 
			{ 
				System.out.println(i); 
			}
		}
	}

	public int availableThread()
	{
		int threadIndex=-1;

		for(int i=0; i<maxThreadNumber; i++)
		{
			if(!states[i])
			{
				threadIndex = i;
				break;
			}
		}

		return threadIndex;
	}

	public void closeMessagging(int id) throws SQLException{
		Socket sckt = socketLibLocal.get(id);
		int indexThr = threadLib.get(id);

		try {
			states[indexThr] = false;
			System.out.println("Closing connection");

			// close connection
			sckt.close(); 
			in.close();
		} catch (IOException ex) {
			Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void maxId()
	{
		int value=-1;
		for(int i=0;i<getIdList().size();i++)
		{
			value = getIdList().get(i);
			if(value>maxIdValue)
			{
				maxIdValue = value;
			}
		}
	}
}
