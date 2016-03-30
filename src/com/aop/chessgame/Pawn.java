package com.aop.chessgame;

public class Pawn extends Coin {

	public Pawn(boolean isWhite) {
		super(CoinType.PAWN, isWhite);
	}

	@Override
	public boolean canMove(int dest, ChessGame game) {
		int currentRow = getRow(getPosition());
		int currentColumn = getColumn(getPosition());
		int destRow = getRow(dest);
		int destColumn = getColumn(dest);
		// TODO Auto-generated method stub
		return false;
	}

}
