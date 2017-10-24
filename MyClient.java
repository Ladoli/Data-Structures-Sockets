import java.net.Socket;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

public class MyClient extends Thread
{
	private BufferedReader serverInput = null ;
	private PrintWriter pw = null;
	private Socket connectionSock = null;
	public boolean ok_connect = true;
	private ClientGUI gui;

	public MyClient(String host, String vport, ClientGUI gui) throws UnknownHostException, IOException {
		String hostname = host;
		int port = Integer.parseInt(vport);
		gui.displayMessage("Connecting to server on port " + port);
		connectionSock = new Socket(hostname, port);
		InputStreamReader isr = new InputStreamReader(connectionSock.getInputStream());
		serverInput = new BufferedReader(isr);
		pw = new PrintWriter(connectionSock.getOutputStream(),true);
		this.gui = gui;
	}
	
	public void close() throws IOException
	{
		pw.close();
		serverInput.close();
		connectionSock.close();
	}
	
	public void sendData(String msg)
	{
		pw.println(msg); 
	}
	
	public void run()
	{
		String serverMsg;
		
		// used to listen message from server
		try
		{
			while(true)
			{
				serverMsg = serverInput.readLine(); 
				if (serverMsg == null) break;
				else if(serverMsg.charAt(0) == '#') //Used to filter messages meant to update list.
				{
					String toList = serverMsg.replaceFirst("#,","");

					String[] newList = toList.split(",");
					
					gui.setNumberList(newList);
				}
				else if (serverMsg.charAt(0) == '~')
				{
					String rawList = serverMsg.replaceFirst("~,", "");
					String[] clientList = rawList.split(",");
					gui.setClientList(clientList);
				}

				else if (serverMsg.charAt(0)== '!' && serverMsg.charAt(1) == '@' )
				{
					
					int i = 15;
					while(serverMsg.charAt(i) != ':')
					{
						i++;
					}
					String target = serverMsg.substring(15,i-1);
					PMGUI pmWIN = new PMGUI(target, gui);
					pmWIN.receivePM(serverMsg.substring(2));
				}
				else if (serverMsg.charAt(0)== '!' && serverMsg.charAt(1) == '#' )
				{
					
					int i = 15;
					while(serverMsg.charAt(i) != ':')
					{
						i++;
					}
					String target = serverMsg.substring(15,i-1);
					PMGUI pmWIN = new PMGUI(target, gui);
					pmWIN.receivePM(serverMsg.substring(2));
				}

				else
				{
					gui.displayMessage(serverMsg);
				}
			}
		}
		catch (Exception ex)
		{
			ok_connect = false;
		}
	}
}
