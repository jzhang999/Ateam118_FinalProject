package application;

/**
 * A Farm object represents a single farm. It stores the ID of a farm and a red 
 * black tree that contains all the data. The red black tree stores several Year 
 * objects.
 * 
 * @author Xinrui Liu
 *
 */
public class Farm {
	
	private String id;//The id of this farm
	private RBT<Integer, Year> yearData;//The data of this farm
	
	/**
	 * Constructs a farm with the given ID and empty data.
	 * 
	 * @param id the id of this farm
	 */
	public Farm(String id) {
		this.id = id;
		this.yearData = new RBT<Integer, Year>();
	}
	
	/**
	 * Insert a year that is not present in the data of this farm.
	 * 
	 * @param year the number of the new year
	 */
	public void insertNewYear(int year) {
		this.yearData.insert(year, new Year(year));
	}
	
	/**
	 * Get the data of the given year
	 * 
	 * @param year the number of the given year
	 * @return a Year object that contains the data of this year
	 * @throws IllegalArgumentException if the data does not contains the given year
	 */
	public Year getData(int year) {
		if (!yearData.contains(year)) {
			throw new IllegalArgumentException("Invalid year.");
		}
		return yearData.get(year);
	}
	
	/**
	 * Accessor of the farm id.
	 * 
	 * @return the id of this farm
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Accessor of the data of this farm.
	 * 
	 * @return the data of this farm
	 */
	public RBT<Integer, Year> getYearData(){
		return this.yearData;
	}
	
	/**
	 * Get the total milk weight of this farm in the given period of time
	 * 
	 * @param startY the year of the period
	 * @param startM the starting month 
	 * @param startD the starting day
	 * @param endM the end month
	 * @param endD the end day
	 * @return the milk weight of this farm in this period. 0 if this farm does not
	 *         have any record of the given period.
	 */
	public int getWeightInRange(int startY, int startM, int startD,
			int endM, int endD) {
		
		int totalWeight = 0;
		Year year = null;
		
		try {
			year = yearData.get(startY);
		} catch (IllegalArgumentException e) {
			return 0;
		}
		
		if (startM < 1 || startM > 12 || endM < 1 || endM > 12
				|| startD < 1 || endD < 1
				|| year.data[startM].length-1 < startD
				|| year.data[endM].length-1 < endD) {
			throw new IllegalArgumentException("Invalid date.");
		}
		
		if (startM > endM) {
			throw new IllegalArgumentException("Invalid date.");
		}
		
		if (startM == endM && startD > endD) {
			throw new IllegalArgumentException("Invalid date.");
		}
		
		for (int i=startM; i<=endM; i++) {
			if (i == endM && i == startM) {
				for (int j = startD; j <= endD; j++) {
					totalWeight += year.data[i][j];
				}
			} else if (i == startM) {
				for (int j = startD; j <= year.data[i].length - 1; j++) {
					totalWeight += year.data[i][j];
				}
			} else if (i == endM) {
				for (int j = 1; j <= endD; j++) {
					totalWeight += year.data[i][j];
				}
			} else {
				for (int j = 1; j <= year.data[i].length - 1; j++) {
					totalWeight += year.data[i][j];
				}
			}
		}
		return totalWeight;
	}
}
