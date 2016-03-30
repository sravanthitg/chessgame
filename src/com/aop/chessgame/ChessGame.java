package com.aop.chessgame;

public class ChessGame {
	Coin[] coins;
	private boolean firstPlayerWhite;
	private boolean isFirstPlayerPlaying;

	public ChessGame() {
		this(chooseColorByRandom());
	}

	private static boolean chooseColorByRandom() {
		return Math.random() > 0.5;
	}

	public ChessGame(boolean firstPlayerWhite) {
		this.firstPlayerWhite = firstPlayerWhite;
		// only white will move first
		isFirstPlayerPlaying = this.firstPlayerWhite;

		// create the coins
		createCoins();
	}

	private void createCoins() {
		coins = new Coin[64];
		// position the board for playing
		for (int x = 0; x < 8; x++) {
			// 2nd row from top is white pawns
			coins[8 + x] = new Pawn(true);
			// 7th row from top is white pawns
			coins[48 + x] = new Pawn(false);
		}
		coins[0] = new Rook(true);
		coins[7] = new Rook(true);
		coins[56] = new Rook(false);
		coins[63] = new Rook(false);
		// TODO create rest of coins

	}

	public boolean move(int src, int dest) {
		Coin coin = coins[src];
		if (coin == null) {
			// there is no coin there, so invalid
			return false;
		}
		Coin destCoin = coins[src];
		if (destCoin != null) {
			// there is some coin there, is it ours?
			if (destCoin.isWhite() == coin.isWhite()) {
				// there is another coin from same side there
				// sorry we can not move
				return false;
			}
		}

		boolean canMove = coin.canMove(dest, this);
		if (!canMove) {
			// coin says it can not move there
			return false;
		}
		// all fine, lets move
		coin.setPosition(dest);
		coins[dest] = coin;
		coins[src] = null;

		// toggle setting on who should play next
		isFirstPlayerPlaying = !isFirstPlayerPlaying;
		// all done
		return true;
	}

	public boolean isFirstPlayerWhite() {
		return firstPlayerWhite;
	}

	public Coin[] getCoins() {
		return coins;
	}

	public boolean isFirstPlayerPlaying() {
		return isFirstPlayerPlaying;
	}

	public Coin getCoin(int index) {
		return coins[index];
	}

}
