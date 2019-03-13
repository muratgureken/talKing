package mg.com;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*; 
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class server {
	private static String url="jdbc:postgresql://127.0.0.1:5432/KullaniciYonetimi";
	private static String username="postgres";
	private static String password="sifre123";
	private Connection conn;
	private Socket          socket   = null; 
	private ServerSocket    server   = null; 
	private DataInputStream in       =  null; 
	private int maxThreadNumber=10;
	private Thread[] threads = new Thread[maxThreadNumber];
	private boolean[] states = new boolean[maxThreadNumber];      

	public server(int port) throws SQLException{
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, password); //try/catch'e gerek yok, metot throwable yapildi.
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		conn.close();

		for(int i=0; i<maxThreadNumber; i++)
		{
			states[i] = false;
		}

		while(true)
		{
			// starts server and waits for a connection 
			try
			{ 
				server = new ServerSocket(port); 
				System.out.println("Server started"); 

				System.out.println("Waiting for a client ..."); 

				socket = server.accept(); 
				System.out.println("Client accepted"); 

				//her accepten sonra bir thread olustur.
				int threadIndex = availableThread();
				if(threadIndex!=-1)
				{
					threads[threadIndex] = new Thread()
					{
						private int grupNo;
						private int receiveId;
						private String messageIn;	
						private LinkedList<Integer> sendIds = new LinkedList<Integer>();
						public void run()
						{								
							for(;;)
							{
								if(states[threadIndex])
								{
									try
									{
										// takes input from the client socket
										in = new DataInputStream(
												new BufferedInputStream(socket.getInputStream()));

										String line = "";

										//reads message
										line = in.readUTF();
										//mesaj oku

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

	public void closeMessagging(int threadIndex) throws SQLException{
		try {
			states[threadIndex] = false;
			System.out.println("Closing connection");

			// close connection
			socket.close(); 
			in.close();
		} catch (IOException ex) {
			Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
