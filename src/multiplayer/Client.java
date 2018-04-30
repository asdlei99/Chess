package multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import gui.ChessBoard;

public class Client implements ConnectionInterface, Runnable {

	private String ip;
	private int port;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private ChessBoard board;
	
	public Client(ChessBoard board, String ip, int port) {
		this.board = board;
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public void run() {
		
		try {
			socket = new Socket(ip, port);

			if(socket.isConnected()) {
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());
				handshake();
				notifyConnection();
			}
			
			while(socket.isConnected()) {
				waitForResponse();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	//Si hei til hverandre og bestem ting som f.eks. hvilken spiller som er hvilken farge
	private void handshake() {
		
		try {
			System.out.println("CLIENT: handshake");
			Message sendTestMsg = new Message("Hei fra klienten");
			output.writeObject(sendTestMsg);
			
			Message getTestMsg = (Message)input.readObject();
			System.out.println(getTestMsg.getMessage());	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
			
	private void waitForResponse() {
		
		try {
			Message response = null;	
			System.out.println("Klient venter p� respons");
			
			response = (Message)input.readObject();
			
			System.out.println("CLIENT: " + response.getMessage());
			updateChessBoard(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	//Send en melding til den andre spilleren, sendes n�r en brikke blir flyttet
	public void sendResponse(Message response) {
		
		try {
			output.writeObject(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void updateChessBoard(Message msg) {
		board.getUpdateFromSocket(msg);
	}
	
	private void notifyConnection() {
		board.onPlayerConnected();
	}
	
}
