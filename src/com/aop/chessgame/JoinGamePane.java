package com.aop.chessgame;

import javax.swing.JPanel;

public class JoinGamePane extends JPanel {

	private ChessGameApplication chessGameApplication;

	public JoinGamePane(ChessGameApplication chessGameApplication) {
		this.chessGameApplication = chessGameApplication;
		String userName = null;
		int port = 0;
		String host = null;
		// set some layout
		// create required controls
		// create two buttons, cancel, join
		// on join call
		chessGameApplication.onJoinGame(host, port, userName);
	}

}
