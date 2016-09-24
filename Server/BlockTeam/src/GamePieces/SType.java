package GamePieces;

import Game.*;
import Game.GameConstants.PolyominoeType;
import Game.GameConstants.TetrisBlockColor;

public class SType extends Polyominoes {

	public SType(TetrisBlock[][] tetrisBoard) {
		// TODO Auto-generated constructor stub
		super(PolyominoeType.STYPE, tetrisBoard);
	}

	@Override
	protected boolean rotate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean createPiece() {
		createNewPieceFromArray(GameConstants.STYPE_START, TetrisBlockColor.GREEN, this);
		return checkIfGameOver();
	}

}
