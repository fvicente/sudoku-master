
public class Const {
	public static final int NUM[] = new int[] {
			0x00000000, // dummy
			0x00000001,
			0x00000002,
			0x00000004,
			0x00000008,
			0x00000010,
			0x00000020,
			0x00000040,
			0x00000080,
			0x00000100
	};
	
	public static final int CELL_SOLVED = NUM[1] | NUM[2] | NUM[3] | NUM[4] |
			NUM[5] | NUM[6] | NUM[7] | NUM[8] | NUM[9];
	
	public static final int NOT_NUM[] = new int[] {
			0x00000000, // dummy
			0x00001000,
			0x00002000,
			0x00004000,
			0x00008000,
			0x00010000,
			0x00020000,
			0x00040000,
			0x00080000,
			0x00100000
	};

	public static final int NOT_FULL = NOT_NUM[1] | NOT_NUM[2] |
			NOT_NUM[3] | NOT_NUM[4] | NOT_NUM[5] | NOT_NUM[6] | NOT_NUM[7] |
			NOT_NUM[8] | NOT_NUM[9];
}
