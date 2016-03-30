package com.aop.chessgame;

public class Bishop extends Coin {
	public Bishop(boolean isWhite) {
		super(CoinType.BISHOP, isWhite);
	}

	@Override
	public boolean canMove(int dest, ChessGame game) {
		// TODO Auto-generated method stub
		return false;
	}
}
