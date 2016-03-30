package com.aop.chessgame;

public class King extends Coin {
	public King(boolean isWhite) {
		super(CoinType.KING, isWhite);
	}

	@Override
	public boolean canMove(int dest, ChessGame game) {
		// TODO Auto-generated method stub
		return false;
	}
}
