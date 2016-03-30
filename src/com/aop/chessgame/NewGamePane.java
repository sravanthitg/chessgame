package com.aop.chessgame;

import javax.swing.JPanel;

public class NewGamePane extends JPanel {

	private ChessGameApplication chessGameApplication;

	public NewGamePane(ChessGameApplication chessGameApplication) {
		this.chessGameApplication = chessGameApplication;
		String userName = null;
		int port = 0;
		// set some layout
		// create required controls
		// create two buttons, cancel, create
		// on create call
		chessGameApplication.onNewGame(port, userName);
	}

}
