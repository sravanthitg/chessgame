package com.aop.chessgame;

/**
 * Listener used to know when other user moved something
 * 
 * @author rajesh
 *
 */
public interface ChessGameListener {
	/**
	 * A Coin from src position is moved to dest position
	 * 
	 * @param src
	 *            source position from 0-63
	 * @param dest
	 *            destination position from 0-63
	 */
	void onMove(int src, int dest);

	/**
	 * Called when other user connected to our server
	 * 
	 * @param userName
	 */
	void onStart(String userName, boolean areWePlayingWhite);

	/**
	 * Called if other player resigns
	 */
	void onResign();
}
