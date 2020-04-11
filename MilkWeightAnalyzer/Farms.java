import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * A Farms object represents the collection of all the farms. It stores the data 
 * of all the farms by Farm objects and can generate TimeReport and FarmReport.
 * 
 * @author Xinrui Liu
 *
 */
public class Farms {
	
	private RBT<Integer, Farm> farms;//The red black tree that stores all the farms
	
	/**
	 * Constructs an empty collection of farms.
	 */
	public Farms() {
		this.farms = new RBT<Integer, Farm>();
	}
	
	/**
	 * Reads the information from a csv file. If the file has a line with errors,
	 * an IllegalArgumentException will be thrown, and that file with errors will
	 * not affect the data that is read into the program before.
	 * 
	 * @param fileName the name of the csv file
	 * @throws IOException if any exception happens while reading the file
	 * @throws IllegalArgumentException with description message if the file 
	 *                                  contains a line with error
	 */
	public void readCsvFile(String fileName) throws IOException {
		check(fileName);
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String newLine = br.readLine();
		newLine = br.readLine();
		while (newLine != null) {
			String[] line = newLine.split(",");
			String[] date = line[0].split("-");
			String[] farm = line[1].split(" ");
			String milkWeight = line[2];
			int year = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			int day = Integer.parseInt(date[2]);
			int id = Integer.parseInt(farm[1]);	
			int weight = Integer.parseInt(milkWeight);
			//Read Year class to learn details of how to access data					
			//(NOT required)
			if (farms.contains(id)) {					
				if (farms.get(id).getYearData().contains(year)) {
					farms.get(id).getData(year).data[month][day] = weight;
					farms.get(id).getData(year).data[month][0] += weight;						
				} else {
					farms.get(id).insertNewYear(year);
					farms.get(id).getData(year).data[month][day] = weight;
					farms.get(id).getData(year).data[month][0] += weight;
				}
			} else {
				farms.insert(id, new Farm(id));
				farms.get(id).insertNewYear(year);						
				farms.get(id).getData(year).data[month][day] = weight;
				farms.get(id).getData(year).data[month][0] += weight;
			}
			newLine = br.readLine();
		}
	}
	
	/**
	 * Helper method that test if the file contains errors
	 * 
	 * @param fileName the name of the file
	 * @throws IllegalArgumentException if the file contains errors
	 */
	private void check(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String newLine = br.readLine();
			newLine = br.readLine();
			while (newLine != null) {
				String[] line = newLine.split(",");
				if (line.length == 3) {
					String[] date = line[0].split("-");
					String[] farm = line[1].split(" ");
					String milkWeight = line[2];
					int year = Integer.parseInt(date[0]);
					int month = Integer.parseInt(date[1]);
					int day = Integer.parseInt(date[2]);
					int id = Integer.parseInt(farm[1]);	
					int weight = Integer.parseInt(milkWeight);
				}
				newLine = br.readLine();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("File contains errors.");
		}
	}
	
	/**
	 * Generates the report of the given farm in a given year.
	 * 
	 * For how to access data in the FarmReport object, read the FarmReport class.
	 * 
	 * @param id the id of the farm
	 * @param year the year of the report
	 * @return a FarmReport object with necessary information
	 * @throws IllegalArgumentException with description message if the farm ID is 
	 *                                  invalid or the year is invalid
	 */
	public FarmReport getFarmRep(int id, int year) {
		if (!farms.contains(id)) {
			throw new IllegalArgumentException("Invalid farm ID.");
		}
		Year allYearData = farms.get(id).getData(year);
		int[] weight = new int[13];
		double[] percentage = new double[13];
		percentage[0] = 1.0;
		for (int i=1; i<=12; i++) {
			weight[i] = allYearData.data[i][0];
			weight[0] += allYearData.data[i][0];
		}
		for (int i=1; i<=12; i++) {
			percentage[i] = ((double) weight[i]) / ((double) weight[0]);
		}
		return new FarmReport(weight, percentage);
	}
	
	/**
	 * Generates a report of the given period of time.
	 * 
	 * For how to access data in the TimeReport object, read the TimeReport class.
	 * 
	 * @param startY the year of the period
	 * @param startM the starting month
	 * @param startD the starting day
	 * @param endM the end month
	 * @param endD the end day
	 * @return a TimeReport object that contains necessary information
	 * @throws IllegalArgumentException if the input date is invalid.
	 */
	public TimeReport getDateRangeRep(int startY, int startM, int startD,
			int endM, int endD) {
		int[] id = new int[farms.size()];
		int[] weight = new int[farms.size()];
		double[] percentage = new double[farms.size()];
		int totalWeight = 0;
		List<Integer> idList = farms.getInOrderTraversal();
		int i = 0;
		for (int farmId : idList) {
			id[i] = farmId;
			//The issue that this farm has no record in this year is solved in the
			//Farm class.
			weight[i] = farms.get(farmId).getWeightInRange(startY, startM, 
					startD, endM, endD);
			totalWeight += weight[i];
			i++;
		}
		for (int j=0; j<percentage.length; j++) {
			percentage[j] = ((double) weight[j]) / ((double) totalWeight);
		}
		return new TimeReport(id, weight, percentage, totalWeight);
	}
	
	/**
	 * Generates a report of the given year.
	 * 
	 * For how to access data in the TimeReport object, read the TimeReport class.
	 * 
	 * @param year the year of the report
	 * @return a TimeReport object that contains necessary information
	 */
	public TimeReport getAnnualReport(int year) {
		return this.getDateRangeRep(year, 1, 1, 12, 31);
	}
	
	/**
	 * Generates a report of the given month.
	 * 
	 * For how to access data in the TimeReport object, read the TimeReport class.
	 * 
	 * @param year the year of the report
	 * @param month the month of the report
	 * @return a TimeReport object that contains necessary information
	 */
	public TimeReport getMonthlyReport(int year, int month) {
		return this.getDateRangeRep(year, month, 1, month, 
				this.daysInMonth(year, month));
	}
	
	/**
	 * Helper method to decide the number of days in a month
	 * 
	 * @param year the year 
	 * @param month the month
	 * @return the number of days in a month 
	 */
	private int daysInMonth(int year, int month) {
		switch (month) {
			case 2:
				if (isLeap(year)) {
					return 29;
				} else {
					return 30;
				}
			case 4:
				return 30;
			case 6:
				return 30;
			case 9:
				return 30;
			case 11:
				return 30;
			default:
				return 31;
		}
	}
	
	/**
	 * Helper method to decide whether a year is a leap year or not.
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
	
//	A main method to test the functionality of the backend.
//	
//	public static void main(String[] args) throws IOException {
//		Farms f = new Farms();
//		
//		f.readCsvFile("2019-1.csv");
//		f.readCsvFile("2019-2.csv");
//		f.readCsvFile("2019-3.csv");
//		f.readCsvFile("2019-4.csv");
//		f.readCsvFile("2019-5.csv");
//		f.readCsvFile("2019-6.csv");
//		f.readCsvFile("2019-7.csv");
//		f.readCsvFile("2019-8.csv");
//		f.readCsvFile("2019-9.csv");
//		f.readCsvFile("2019-10.csv");
//		f.readCsvFile("2019-11.csv");
//		f.readCsvFile("2019-12.csv");
//		System.out.println();
//		
//		FarmReport frp = f.getFarmRep(18, 2019);
//		System.out.println(frp.getWeight(1));
//		
//		TimeReport trp = f.getDateRangeRep(2019, 1, 1, 12, 30);
//		for (int i = 0; i <= 2; i++) {
//			System.out.print(trp.getId()[i] + " " + trp.getWeight()[i] 
//					+ " " + trp.getPercentage()[i]);
//			System.out.println();
//		}
//		System.out.println();
//		
//		TimeReport trp2 = f.getMonthlyReport(2019, 1);
//		for (int i = 0; i <= 2; i++) {
//			System.out.print(trp2.getId()[i] + " " + trp2.getWeight()[i] 
//					+ " " + trp2.getPercentage()[i]);
//			System.out.println();
//		}
//		System.out.println();
//		
//		TimeReport trp3 = f.getAnnualReport(2019);
//		for (int i = 0; i <= 2; i++) {
//			System.out.print(trp3.getId()[i] + " " + trp3.getWeight()[i] 
//					+ " " + trp3.getPercentage()[i]);
//			System.out.println();
//		}
//		System.out.println();
//	}
}
