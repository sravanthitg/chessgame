package com.aop.chessgame;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ChessGameApplication extends JFrame implements ChessGameListener, MoveListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Game server that is responsible for all networking stuff
	 */
	private GameServer server;

	private ChessGame game;

	private ChessBoard board;

	private boolean areWeHosting;

	private JLabel statusBar;

	private JPanel placeHolder;

	public ChessGameApplication() {
		// Create Menus
		createMenus();
		//show the size and of window 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setPreferredSize(new Dimension(800, 800));
	    pack();
	    setLocationRelativeTo(null);
	    setVisible(true);
		// create PlaceHolder for games/panes
		this.placeHolder = new JPanel();
		this.add(this.placeHolder);
		// Create Status bar
		createStatusBar();
	}

	private void createStatusBar() {
		statusBar = new JLabel();
		this.add(statusBar);
	}

	private void createMenus() {
		// Create New Game Menu
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		JMenu game = new JMenu("Game");
		JMenuItem newGame = new JMenuItem("New Game");
		game.add(newGame);
		newGame.addActionListener((e) -> onNewGame());
		JMenuItem joinGame = new JMenuItem("Join Game");
		game.add(joinGame);
		joinGame.addActionListener((e) -> onJoinGame());
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener((e) -> onExit());
		game.add(exit);
		bar.add(game);

		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		game.add(about);
		bar.add(help);
	}

	private void onExit() {
		this.setVisible(false);
		this.server.close();
		System.exit(0);
	}

	private void onJoinGame() {
		// show a pane that asks for desthost, post and user name
		this.placeHolder.removeAll();
		this.placeHolder.add(new JoinGamePane(this));
	}

	private void onNewGame() {
		// Show a pane that asks for username and port to listen on
		this.placeHolder.removeAll();
		this.placeHolder.add(new NewGamePane(this));
	}

	/**
	 * Called when user wants to start new Game
	 */
	void onNewGame(int port, String userName) {
		this.areWeHosting = true;
		// Create Server Socket and wait for connections
		this.server = new GameServer(null, port, userName);
		server.start();
		this.server.setGameListener(this);
		setStatus("Waiting for other user to join our game");
	}

	private void setStatus(String string) {
		this.statusBar.setText(string);
	}

	/**
	 * Called when user wants to join existing Game
	 * 
	 * @param userName
	 */
	void onJoinGame(String host, int port, String userName) {
		areWeHosting = false;
		this.server = new GameServer(host, port, userName);
		server.start();
		this.server.setGameListener(this);
		setStatus("Connecting to other user");
	}

	public static void main(String[] args) {
		ChessGameApplication app = new ChessGameApplication();
		app.setVisible(true);
	}

	@Override
	public void onMove(int src, int dest) {
		if (this.game.isFirstPlayerPlaying()) {
			// We are supposed to move, not the other user
			// so reject
			return;
		}
		// other user moved the coin
		if (this.game.move(src, dest)) {
			// successfully moved
			updateBoard();
		} else {
			System.err.println("This is cheating, " + this.server.getGuestUserName() + "'s move is invalid");
		}

	}

	@Override
	public void onStart(String userName, boolean areWePlayingWhite) {
		System.out.println(userName + " connected to us");

		if (areWeHosting) {
			// now we create the game
			this.game = new ChessGame();
			// Now select who is playing white and who is playing black
			boolean wePlayingWhite = this.game.isFirstPlayerWhite();
			this.server.sendPlayerColor(!wePlayingWhite);
		} else {
			// now we create the game
			this.game = new ChessGame(areWePlayingWhite);
		}

		// Lets create the board with current working directory as root
		board = new ChessBoard(".");
		board.setMoveListener(this);
		placeHolder.removeAll();
		placeHolder.add(board);

		// put the current coin positions according to game
		updateBoard();

	}

	private void updateBoard() {
		Coin[] coins = game.getCoins();
		for (int x = 0; x < 64; x++) {
			board.setCoin(x, coins[x]);
		}
	}

	@Override
	public boolean moveRequested(int src, int dest) {
		boolean isValidMove = this.game.move(src, dest);
		if (isValidMove) {
			updateBoard();
			this.server.sendMove(src, dest);
		}
		return isValidMove;
	}

	@Override
	public void onResign() {
		setStatus("Other player resigned, you won!");
	}

}
