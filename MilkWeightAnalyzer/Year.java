/**
 * A Year object represents the data of a farm in a certain year. It stores the year 
 * number and an 2D array that stores the data of every day for a farm in this year.
 * 
 * @author Xinrui Liu
 *
 */
public class Year {
	
	private int year;//The number of this year
	public int[][] data;//The array that stores all the data of this year
	
	/*
	 * data is intentionally made to be public so that it is easy to write data into
	 * the year object. The size of the data is larger than the required size so that
	 * we can access the data by the format "data[month][day]". If we make the data 
	 * to be exactly the size we need, then we have to access the data by the format
	 * "data[month-1][day-1]", which is fairly easy to cause confusion.
	 */
	
	
	//data[month][0] is used to store the total milk weight of this month
	
	/**
	 * Constructs a Year object without any record.
	 * 
	 * @param year
	 */
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
	
	/**
	 * Helper method to decide whether a year is a leap year or not
	 * 
	 * @param year the number of the year
	 * @return true if this year is leap year, false otherwise
	 */
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
