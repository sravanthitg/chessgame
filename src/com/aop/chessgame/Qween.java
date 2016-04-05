package com.aop.chessgame;


public class Qween extends Coin {
	public Qween(boolean isWhite) {
		super(CoinType.QWEEN, isWhite);
	}

	@Override
	public boolean canMove(int dest, ChessGame game) {
		if (isStrightAndFree(getPosition(), dest, game) || isDiagnalAndFree(getPosition(), dest, game)) {
			return true;
		}

		return false;
	}

	private boolean isStrightAndFree(int position, int dest, ChessGame game) {
		int currentRow = getRow(position);
		int currentColumn = getColumn(position);
		int destRow = getRow(dest);
		int destColumn = getColumn(dest);
		if (currentRow == destRow) {
			for (int x = Math.min(currentColumn, destColumn) + 1; x < Math.max(currentColumn, destColumn) - 1; x++) {
				// make sure all are free
				if (game.getCoin(getIndex(currentRow, x)) != null) {
					return false;
				}
			}
			// nothing in between
			return true;
		} else if (currentColumn == destColumn) {
			for (int x = Math.min(currentRow, destRow) + 1; x < Math.max(currentRow, destRow) - 1; x++) {
				// make sure all are free
				if (game.getCoin(getIndex(x, currentColumn)) != null) {
					return false;
				}
			}
			// nothing in between
			return true;
		} else
			return false;
	}

	private int getIndex(int row, int column) {
		return row * 8 + column;
	}

	private boolean isDiagnalAndFree(int position, int dest, ChessGame game) {
		int currentRow = getRow(position);
		int currentColumn = getColumn(position);
		int destRow = getRow(dest);
		int destColumn = getColumn(dest);
		if (Math.abs(currentRow - destRow) == Math.abs(currentColumn - destColumn)) {
			for (int x = Math.min(currentColumn, destColumn) + 1; x < Math.max(currentColumn, destColumn) - 1; x++) {
				for (int y = Math.min(currentRow, destRow) + 1; y < Math.max(currentRow, destRow) - 1; y++) {
					// make sure all are free
					if (game.getCoin(getIndex(x, y)) != null) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

}
