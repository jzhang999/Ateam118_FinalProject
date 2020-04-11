
public class Year {
	
	private int year;
	public int[][] data;
	
	public Year(int year) {
		this.year = year;
		this.data = new int[13][];
		this.data[1] = new int[32];
		this.data[3] = new int[32];
		this.data[4] = new int[31];
		this.data[5] = new int[32];
		this.data[6] = new int[31];
		this.data[7] = new int[32];
		this.data[8] = new int[32];
		this.data[9] = new int[31];
		this.data[10] = new int[32];
		this.data[11] = new int[31];
		this.data[12] = new int[32];
		if (isLeap(year)) {
			this.data[2] = new int[30];
		} else {
			this.data[2] = new int[29];
		}
	}
	
	private boolean isLeap(int year) {
		if (year%4 == 0) {
			if (year%100 == 0) {
				if (year%400 == 0) {
					return true;
				}
				return false;
			}
			return true;
		}
		return false;
	}
}
