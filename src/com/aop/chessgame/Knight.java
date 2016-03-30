package com.aop.chessgame;

public class Knight extends Coin {
	public Knight(boolean isWhite) {
		super(CoinType.KNIGHT, isWhite);
	}

	@Override
	public boolean canMove(int dest, ChessGame game) {
		// TODO Auto-generated method stub
		return false;
	}
}
