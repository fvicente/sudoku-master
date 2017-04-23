
public class SudokuMaster {
	
	public SudokuMaster() {
		
	}

	public SudokuTable solveMe(SudokuTable sudokuTable) {
		int qtySolved = 0;
		SudokuBinTable bin = new SudokuBinTable();

		for(int i = 0; i < SudokuBinTable.MAX_COLS; i++) {
			for(int j = 0; j < SudokuBinTable.MAX_ROWS; j++) {
				bin.table[i][j] = Const.NUM[sudokuTable.table[i][j]];
			}
		}
		
		System.out.println("Antes:");
		bin.print();
		
		// Set all flag for what we're sure is not
		for(int i = 0; i < SudokuBinTable.MAX_COLS; i++) {
			int not = 0;
			for(int j = 0; j < SudokuBinTable.MAX_ROWS; j++) {
				not |= Const.NOT_NUM[sudokuTable.table[i][j]];
			}
			for(int j = 0; j < SudokuBinTable.MAX_ROWS; j++) {
				if((bin.table[i][j] & Const.CELL_SOLVED) == 0) {
					bin.table[i][j] |= not;
				} else {
					qtySolved++;
					bin.table[i][j] |= Const.NOT_FULL;
				}
			}
		}
		// Repetir en sentido horizontal
		for(int i = 0; i < SudokuBinTable.MAX_COLS; i++) {
			int not = 0;
			for(int j = 0; j < SudokuBinTable.MAX_ROWS; j++) {
				not |= Const.NOT_NUM[sudokuTable.table[j][i]];
			}
			for(int j = 0; j < SudokuBinTable.MAX_ROWS; j++) {
				bin.table[j][i] |= not;
				// no necesitamos esto
				//if((bin.table[j][i] & Const.CELL_SOLVED) == 0) {
				//	bin.table[j][i] |= not;
				//} else {
				//	bin.table[j][i] |= Const.NOT_FULL;
				//}
			}
		}

		// Pasada simple por sub tablas
		int lastQtySolved = -1;
		while(qtySolved < 81 && qtySolved != lastQtySolved) {
			lastQtySolved = qtySolved;
			for(int k = 0; k < 3; k++) {
				for(int l = 0; l < 3; l++) {
					for(int m = 0; m < 3; m++) {
						for(int n = 0; n < 3; n++) {
							// where am I?
							int x = (k * 3) + m;
							int y = (l * 3) + n;
							if((bin.table[x][y] & Const.CELL_SOLVED) == 0) {
								// a ver si puede ir algo seguro
								// pruebo todos del 1 al 9
								for(int o = 0; o < 9; o++) {
									boolean can = true;
									// solo chequear lo que puede ir
									if((bin.table[x][y] & Const.NOT_NUM[o + 1]) == 0) {
										// chequear el resto de las celdas de
										// esta sub tabla
										for(int p = 0; p < 3; p++) {
											for(int q = 0; q < 3; q++) {
												int subx = (k * 3) + p;
												int suby = (l * 3) + q;
												if((!(subx == x && suby == y)) &&
												  (((Const.NUM[o + 1] & bin.table[subx][suby]) == Const.NUM[o + 1]) ||
												  ((Const.NOT_NUM[o + 1] & bin.table[subx][suby]) == 0))) {
													can = false;
													break;
												}
											}
											if(!can) break;
										}
									} else {
										continue;
									}
									if(can) {
										qtySolved++;
										bin.table[x][y] |= Const.NUM[o + 1];
										bin.table[x][y] |= Const.NOT_FULL;
										// the whole row and column can not be this number anymore
										for(int i = 0; i < SudokuBinTable.MAX_COLS; i++) {
											if(i == y) continue;
											bin.table[x][i] |= Const.NOT_NUM[o + 1];
										}
										for(int j = 0; j < SudokuBinTable.MAX_ROWS; j++) {
											if(j == x) continue;
											bin.table[j][y] |= Const.NOT_NUM[o + 1];
										}
										break;
									}
								}
							}
						}
						if(qtySolved == 81) break;
					}
					if(qtySolved == 81) break;
				}
				if(qtySolved == 81) break;
			}
			if(qtySolved == 81) break;
			
			// Find by row
			for(int x = 0; x < SudokuBinTable.MAX_COLS; x++) {
				for(int y = 0; y < SudokuBinTable.MAX_ROWS; y++) {
					// Are we interested in this cell?
					if((bin.table[x][y] & Const.CELL_SOLVED) == 0) {
						// pruebo todos del 1 al 9
						for(int o = 0; o < 9; o++) {
							// solo chequear lo que puede ir
							if((bin.table[x][y] & Const.NOT_NUM[o + 1]) == 0) {
								boolean can = true;
								for(int suby = 0; suby < SudokuBinTable.MAX_ROWS; suby++) {
									if(suby == y) continue;
									if((Const.NOT_NUM[o + 1] & bin.table[x][suby]) == 0) {
										can = false;
										break;
									}
								}
								if(can) {
									qtySolved++;
									bin.table[x][y] |= Const.NUM[o + 1];
									bin.table[x][y] |= Const.NOT_FULL;
									// the whole row and column can not be this number anymore
									for(int i = 0; i < SudokuBinTable.MAX_COLS; i++) {
										if(i == y) continue;
										bin.table[x][i] |= Const.NOT_NUM[o + 1];
									}
									for(int j = 0; j < SudokuBinTable.MAX_ROWS; j++) {
										if(j == x) continue;
										bin.table[j][y] |= Const.NOT_NUM[o + 1];
									}
									break;
								}
							}
						}
						if(qtySolved == 81) break;
					}
				}
				if(qtySolved == 81) break;
			}
			if(qtySolved == 81) break;
			
			// Find by column
			for(int y = 0; y < SudokuBinTable.MAX_ROWS; y++) {
				for(int x = 0; x < SudokuBinTable.MAX_COLS; x++) {
					// Are we interested in this cell?
					if((bin.table[x][y] & Const.CELL_SOLVED) == 0) {
						// pruebo todos del 1 al 9
						for(int o = 0; o < 9; o++) {
							// solo chequear lo que puede ir
							if((bin.table[x][y] & Const.NOT_NUM[o + 1]) == 0) {
								boolean can = true;
								for(int subx = 0; subx < SudokuBinTable.MAX_ROWS; subx++) {
									if(subx == x) continue;
									if((Const.NOT_NUM[o + 1] & bin.table[subx][y]) == 0) {
										can = false;
										break;
									}
								}
								if(can) {
									qtySolved++;
									bin.table[x][y] |= Const.NUM[o + 1];
									bin.table[x][y] |= Const.NOT_FULL;
									// the whole row and column can not be this number anymore
									for(int i = 0; i < SudokuBinTable.MAX_COLS; i++) {
										if(i == y) continue;
										bin.table[x][i] |= Const.NOT_NUM[o + 1];
									}
									for(int j = 0; j < SudokuBinTable.MAX_ROWS; j++) {
										if(j == x) continue;
										bin.table[j][y] |= Const.NOT_NUM[o + 1];
									}
									break;
								}
							}
						}
						if(qtySolved == 81) break;
					}
				}
				if(qtySolved == 81) break;
			}
		}
		System.out.println("\nDespues:");
		bin.print();
		System.out.println("\nVerificaciones:");
		if(qtySolved < 81) {
			System.out.println("- Incompleto");
		} else {
			System.out.println("- Completo");
			System.out.println("- Estado: " + ((bin.isCorrect())?"Correcto":"Incorrecto"));
		}
		return null;
	}

	public static void main(String[] args) {
		SudokuMaster sm = new SudokuMaster();
		// Easy Puzzle 7,261,211,975
		int table[][] = new int[][] {
				{7, 0, 1, 0, 0, 0, 0, 0, 0},
				{0, 2, 0, 1, 4, 7, 0, 0, 0},
				{0, 4, 0, 2, 0, 0, 0, 0, 5},
				{0, 7, 3, 4, 0, 2, 8, 6, 0},
				{0, 8, 4, 7, 0, 6, 5, 3, 0},
				{0, 5, 2, 3, 0, 8, 9, 4, 0},
				{2, 0, 0, 0, 0, 4, 0, 9, 0},
				{0, 0, 0, 6, 8, 1, 0, 7, 0},
				{0, 0, 0, 0, 0, 0, 6, 0, 4}};
/*
		int table[][] = new int[][] {
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0}};
*/
		int table2[][] = new int[][] {
				{5, 0, 0, 1, 7, 0, 0, 3, 9},
				{0, 0, 7, 9, 0, 0, 0, 0, 0},
				{0, 1, 0, 0, 0, 0, 4, 0, 0},
				{0, 0, 0, 8, 0, 2, 7, 0, 0},
				{3, 0, 8, 0, 0, 0, 6, 0, 2},
				{0, 0, 5, 4, 0, 6, 0, 0, 0},
				{0, 0, 2, 0, 0, 0, 0, 5, 0},
				{0, 0, 0, 0, 0, 5, 9, 0, 0},
				{1, 5, 0, 0, 2, 9, 0, 0, 7}};

		SudokuTable st = new SudokuTable(table2);
		sm.solveMe(st);
	}
}