package com.aop.chessgame;

public abstract class Coin {
	private boolean isWhite;
	private CoinType type;
	private int position;

	protected Coin(CoinType type, boolean isWhite) {
		this.setType(type);
		this.isWhite = isWhite;
	}

	public boolean isWhite() {
		return isWhite;
	}

	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	public CoinType getType() {
		return type;
	}

	public void setType(CoinType type) {
		this.type = type;
	}

	public abstract boolean canMove(int dest, ChessGame game);

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public int getColumn(int position) {
		return position % 8;
	}

	public int getRow(int position) {
		return position / 8;
	}

}
