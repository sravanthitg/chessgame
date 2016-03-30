package com.aop.chessgame;

public class Rook extends Coin {

	public Rook(boolean isWhite) {
		super(CoinType.ROOK, isWhite);
	}

	@Override
	public boolean canMove(int dest, ChessGame game) {
		// TODO Auto-generated method stub
		return false;
	}

}
