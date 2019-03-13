package mg.com;

import java.util.LinkedList;

public class messageProtocol {
	public String clientMessage(LinkedList<Integer> receiveId, int sendId, String messageToSend)
	{
		String messageT2M=null;

		int size = receiveId.size() + 1;
		String buffer = Integer.toString(size);
		messageT2M = zeroPad(buffer);
		buffer = Integer.toString(sendId);
		messageT2M = messageT2M + zeroPad(buffer);

		for(int i=0;i<receiveId.size();i++)
		{
			buffer = Integer.toString(receiveId.get(i));
			messageT2M = messageT2M + buffer;
		}

		messageT2M = messageT2M + messageToSend;

		return messageT2M;
	}

	public String serverMessage()
	{
		return null;
	}
	
	public void registerUserMessage()
	{
		
	}
	
	private String zeroPad(String input)
	{
		String padded="";

		int diff = 8 - input.length();

		for (int i=0;i<diff;i++)
		{
			padded = padded+"0";            
		}
		padded = padded + input;

		return padded;
	}
}
