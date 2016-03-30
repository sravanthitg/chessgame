package com.aop.chessgame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Create a game server that listens on a port
 * 
 * @author rajesh
 *
 */
public class GameServer extends Thread {

	private static final byte MOVE = 1;
	private static final byte RESIGN = 2;

	private int port;

	private String hostUserName;

	private String guestUserName;

	private ChessGameListener listener;

	/**
	 * If this is null, we will connect as client, else we will create server
	 * socket
	 */
	private String hostname;

	private Socket socket;

	private DataInputStream in;

	private DataOutputStream out;

	private boolean color;

	public GameServer(String hostname, int port, String hostUserName) {
		this.hostname = hostname;
		this.port = port;
		this.setHostUserName(hostUserName);
	}

	public void run() {
		// create server socket or socket based on hostname
		try {
			if (hostname == null) {
				ServerSocket ss = new ServerSocket(port);
				setSocket(ss.accept());
				sendUserName();
				String userName = readUserName();
				this.listener.onStart(userName, false);
				sendColor();
			} else {
				setSocket(new Socket(hostname, port));
				String userName = readUserName();
				boolean isWhite = readColor();
				this.listener.onStart(userName, isWhite);
			}
			// read for the moves
			while (true) {
				int type=in.read();
				switch (type) {
				case MOVE:
					int start=in.read();
					int end=in.read();
					listener.onMove(start, end);
					break;
				case RESIGN:
					listener.onResign();
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean readColor() throws IOException {
		return this.in.read() == 1;
	}

	private String readUserName() throws IOException {
		return this.in.readUTF();
	}

	private void sendColor() throws IOException {
		// if white send 1 else 0
		this.out.write(color ? 1 : 0);
	}

	private void sendUserName() throws IOException {
		this.out.writeUTF(hostUserName);
	}

	private void setSocket(Socket socket) throws IOException {
		this.socket = socket;
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}

	public void onClientConnect(Socket socket) {

	}

	public void setGameListener(ChessGameListener listener) {
		this.listener = listener;
	}

	public String getHostUserName() {
		return hostUserName;
	}

	public void setHostUserName(String hostUserName) {
		this.hostUserName = hostUserName;
	}

	public String getGuestUserName() {
		return guestUserName;
	}

	public void setGuestUserName(String guestUserName) {
		this.guestUserName = guestUserName;
	}

	public void sendPlayerColor(boolean isWhite) {
		this.color = isWhite;
	}

	public void sendMove(int src, int dest) {
		try {
			this.out.write(MOVE);
			this.out.write(src);
			this.out.write(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
