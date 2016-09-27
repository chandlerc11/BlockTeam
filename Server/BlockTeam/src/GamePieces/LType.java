package GamePieces;

import Game.*;
import Game.GameConstants.PolyominoeType;
import Game.GameConstants.TetrisBlockColor;

public class LType extends Polyominoes {

	public LType(TetrisBlock[][] tetrisBoard) {
		// TODO Auto-generated constructor stub
		super(PolyominoeType.LTYPE, tetrisBoard);
		createNewPieceFromArray(GameConstants.LTYPE_START, TetrisBlockColor.ORANGE);
	}
}
