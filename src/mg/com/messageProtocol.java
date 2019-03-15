package mg.com;

import java.util.LinkedList;

public class messageProtocol {
	public String clientMessage(LinkedList<Integer> receiveId, int sendId, String messageToSend)
	{
		String messageT2M="1";

		int size = receiveId.size() + 1;
		String buffer = Integer.toString(size);
		messageT2M = messageT2M+zeroPad(buffer,8);
		buffer = Integer.toString(sendId);
		messageT2M = messageT2M + zeroPad(buffer,8);

		for(int i=0;i<receiveId.size();i++)
		{
			buffer = Integer.toString(receiveId.get(i));
			messageT2M = messageT2M + zeroPad(buffer,8);
		}

		messageT2M = messageT2M + messageToSend;

		return messageT2M;
	}
	
	public String registerUserMessage(String name, String password)
	{
	    String messageT2M="3";
            messageT2M = messageT2M + zeroPad(name,50);
            messageT2M = messageT2M + zeroPad(password,50);
            return messageT2M;
	}
	
        public String updateUserMessage(int sendId, boolean connState)
        {
            int state=0;
            if(connState)
            {
                state = 1;
            }
            
            String messageT2M="2";
            String buffer = Integer.toString(sendId);
            messageT2M = messageT2M + zeroPad(buffer,8);
            buffer = Integer.toString(state);
            messageT2M = messageT2M + buffer;
            return messageT2M;
        }        
          
        public String IdQueryUserMessage(String name)
        {
            String messageT2M="4";
            messageT2M = messageT2M + name;
            
            return messageT2M;
        }
        
        public String serverMessage(String name, String message)
        {
            String messageT2M="1";
            messageT2M = messageT2M + zeroPad(name, 50);
            messageT2M = messageT2M + message;
            return messageT2M;
        }
        
        public String IdResponseMessage(int Id)
        {
            String messageT2M="3";
            String buffer = Integer.toString(Id);
            messageT2M = messageT2M + zeroPad(buffer,8);
            return messageT2M;
        }
        
        public String allDatabaseMessage(int count, LinkedList<String> dbList)
        {
           String messageT2M="2";
           messageT2M = messageT2M + Integer.toString(count);
           
           for(int i=0;i<count;i++)
           {
               messageT2M = messageT2M + zeroPad(dbList.get(i*3),8);
               messageT2M = messageT2M + zeroPad(dbList.get(i*3+1),50);
               messageT2M = messageT2M + dbList.get(i*3+2);              
           }
           
           return messageT2M;
        }
                
	private String zeroPad(String input, int padding)
	{
		String padded="";

		int diff = padding - input.length();

		for (int i=0;i<diff;i++)
		{
			padded = padded+"0";            
		}
		padded = padded + input;

		return padded;
	}
}
