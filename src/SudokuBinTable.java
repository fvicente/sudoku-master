
public class SudokuBinTable {
	public static final int MAX_COLS = 9;
	public static final int MAX_ROWS = 9;

	public int table[][];

	public SudokuBinTable() {
		createTable();
	}
	
	private void createTable() {
		table = new int[MAX_COLS][MAX_ROWS];
		for(int i = 0; i < MAX_COLS; i++) {
			for(int j = 0; j < MAX_ROWS; j++) {
				table[i][j] = 0;
			}
		}
	}
	
	private int getSolvedValue(int binValue) {
		for(int i = 0; i < MAX_COLS; i++) {
			if((binValue & Const.NUM[i + 1]) != 0) {
				return (i + 1);
			}
		}
		return 0;
	}

	public void print() {
		for(int i = 0; i < MAX_COLS; i++) {
			if((i != 0) && ((i % 3) == 0)) {
				System.out.println("------+-------+-------");
			}
			for(int j = 0; j < MAX_ROWS; j++) {
				if((j != 0) && ((j % 3) == 0)) {
					System.out.print("| ");
				}
				int val = getSolvedValue(table[i][j]);
				System.out.print(((val == 0)?" ":val+"") + " ");
			}
			System.out.println();
		}
		
	}
	
	public boolean isCorrect() {
		boolean ret = true;
		String sumx = "", sumy = "";

		for(int i = 0; i < MAX_COLS; i++) {
			int status = 0;
			int tot = 0;
			for(int j = 0; j < MAX_ROWS; j++) {
				status |= Const.NUM[getSolvedValue(table[i][j])];
				tot += getSolvedValue(table[i][j]);
			}
			sumx += tot + " ";
			if(status != Const.CELL_SOLVED) {
				ret = false;
				break;
			}
		}
		if(ret) {
			for(int i = 0; i < MAX_COLS; i++) {
				int status = 0;
				int tot = 0;
				for(int j = 0; j < MAX_ROWS; j++) {
					status |= Const.NUM[getSolvedValue(table[j][i])];
					tot += getSolvedValue(table[j][i]);
				}
				sumy += tot + " ";
				if(status != Const.CELL_SOLVED) {
					ret = false;
					break;
				}
			}
		}
		System.out.println(sumx);
		System.out.println(sumy);
		return ret;
	}
}