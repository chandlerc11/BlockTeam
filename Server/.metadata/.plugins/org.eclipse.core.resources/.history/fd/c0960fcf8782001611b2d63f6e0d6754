package GamePieces;

import Game.*;
import Game.GameConstants.PolyominoeType;
import Game.GameConstants.TetrisBlockColor;

public class ZType extends Polyominoes {

	public ZType(TetrisBlock[][] tetrisBoard) {
		// TODO Auto-generated constructor stub
		super(PolyominoeType.ZTYPE, tetrisBoard);
		
	}

	@Override
	protected boolean rotate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean createPiece() {
		createNewPieceFromArray(GameConstants.ZTYPE_START, TetrisBlockColor.RED, this);
		return checkIfGameOver();
	}

}
