package Game;

public class GameConstants {
	public enum GameMoves {
	    START, PAUSE, LEFT, RIGHT, DOWN, ALLDOWN, ROTATE, UPDATE
	}
	
	public enum TetrisBlockColor{
		CYAN, BLUE, ORANGE, YELLOW, GREEN, PURPLE, RED
	}
	
	public enum PolyominoeType{
		ITYPE, JTYPE, LTYPE, OTYPE, STYPE, TTYPE, ZTYPE  
	}
	
	public static int[][] ITYPE_START = {{5, 23}, {5, 22}, {5, 21}, {5, 20}};
	public static int[][] JTYPE_START = {{5, 23}, {5, 22}, {5, 21}, {4, 21}};
	public static int[][] LTYPE_START = {{4, 23}, {4, 22}, {4, 21}, {5, 21}};
	public static int[][] OTYPE_START = {{5, 23}, {4, 23}, {5, 22}, {4, 22}};
	public static int[][] STYPE_START = {{4, 23}, {4, 22}, {5, 22}, {5, 21}};
	public static int[][] TTYPE_START = {{4, 22}, {5, 23}, {5, 22}, {6, 22}};
	public static int[][] ZTYPE_START = {{5, 23}, {5, 22}, {4, 22}, {4, 21}};
	
	
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 24;
	public static final int NUMPIECES = 7;
	public static final long RATE = 1000;
}
