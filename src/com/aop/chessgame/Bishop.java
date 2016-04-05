package com.aop.chessgame;

public class Bishop extends Coin {
	public Bishop(boolean isWhite) {
		super(CoinType.BISHOP, isWhite);
	}

	@Override
	public boolean canMove(int dest, ChessGame game) {
		if (isDiagnalAndFree(getPosition(), dest, game)) {
			return true;
		}
		return false;
	}

	private boolean isDiagnalAndFree(int position, int dest, ChessGame game) {
		int curRow=getRow(position);
		int curColumn=getColumn(position);
		int destRow=getRow(dest);
		int destColumn=getColumn(dest);
		
		if(game.getCoin(dest)==null && this.isWhite()!=game.getCoin(dest).isWhite())
		{
		if(Math.abs(curRow-destRow) == Math.abs(curColumn-destColumn))
		{
			for(int i=Math.min(curRow, destRow)+1; i< Math.max(curRow, destRow); i++)
				{
					for(int j=Math.min(curColumn, destColumn)+1;j< Math.max(curColumn, destColumn); j++)
						{
							if(game.getCoin(getIndex(i,j)) !=null)
							{
								return false;
							}
						}
				}
		 
		}
		else
		{
			return false;
		}
		return true;
		}
		else
			return false;
		
	}


	private int getIndex(int i, int j) {
		
		return (i*8+j);
	}
}
