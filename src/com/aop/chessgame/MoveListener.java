package com.aop.chessgame;

public interface MoveListener {
	/**
	 * called if user tries to move a coin
	 * @param src
	 * @param dst
	 * @return
	 */
	boolean moveRequested(int src, int dst);
}
