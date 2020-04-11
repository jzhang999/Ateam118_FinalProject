
public class Farm {
	
	private int id;
	private RBT<Integer, Year> yearData;
	
	public Farm(int id) {
		this.id = id;
		this.yearData = new RBT<Integer, Year>();
	}
	
	public void insertNewYear(int year) {
		this.yearData.insert(year, new Year(year));
	}
	
	public Year getData(int year) {
		if (!yearData.contains(year)) {
			throw new IllegalArgumentException("Invalid year.");
		}
		return yearData.get(year);
	}
	
	public int getId() {
		return id;
	}
	
	public RBT<Integer, Year> getYearData(){
		return this.yearData;
	}
	
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
		
		for (int i=startM; i<=endM; i++) {
			if (i == startM) {
				for (int j = startD; j <= year.data[startM].length - 1; j++) {
					totalWeight += year.data[startM][j];
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
