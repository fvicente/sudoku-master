
public class SudokuTable {
	private final int MAX_COLS = 9;
	private final int MAX_ROWS = 9;

	public int table[][];

	public SudokuTable(int[][] tableInfo) {
		createTable();
		if(tableInfo != null) {
			for(int i = 0; i < tableInfo.length && i < MAX_COLS; i++) {
				for(int j = 0; j < tableInfo[i].length && j < MAX_ROWS; j++) {
					table[i][j] = tableInfo[i][j];
				}
			}
		}
	}

	public SudokuTable() {
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
}