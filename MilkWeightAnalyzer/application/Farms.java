package application;

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
	
	private RBT<String, Farm> farms;//The red black tree that stores all the farms
	
	/**
	 * Constructs an empty collection of farms.
	 */
	public Farms() {
		this.farms = new RBT<String, Farm>();
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
			String id = line[1];
			String milkWeight = line[2];
			int year = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			int day = Integer.parseInt(date[2]);
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
	 * Decide whether a farm is present or not.
	 * 
	 * @param f the name of the farm
	 * @return true if the farm is present, false otherwise
	 */
	public boolean contains(String f) {
		return farms.contains(f);
	}
	
	/**
	 * Accessor for a single farm
	 * @param f the name of the farm
	 * @return a farm according to the input
	 */
	public Farm getFarm(String f) {
		return farms.get(f);
	}
	
	public void changeData(String Year, String Month, String Day, String id, String Weight) {
		try {
			int year = Integer.parseInt(Year);
			int month = Integer.parseInt(Month);
			int day = Integer.parseInt(Day);
			int weight = Integer.parseInt(Weight);
			if (farms.contains(id)) {					
				if (farms.get(id).getYearData().contains(year)) {
					int weightTemp = farms.get(id).getData(year).data[month][day];
					farms.get(id).getData(year).data[month][day] = weight;
					farms.get(id).getData(year).data[month][0] += weight;
					farms.get(id).getData(year).data[month][0] -= weightTemp;
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
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid input. Check the value and format of the inputs.");
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
					String id = line[1];
					String milkWeight = line[2];
					int year = Integer.parseInt(date[0]);
					int month = Integer.parseInt(date[1]);
					int day = Integer.parseInt(date[2]);
					int weight = Integer.parseInt(milkWeight);
					if (month < 1 || month > 12 || day < 1 ||
							day > daysInMonth(year, month)) {
						throw new Exception();
					}
				}
				newLine = br.readLine();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid file.");
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
	public FarmReport getFarmRep(String id, int year) {
		if (!farms.contains(id)) {
			throw new IllegalArgumentException("Invalid farm ID.");
		}
		Year allYearData = farms.get(id).getData(year);
		int[] weight = new int[13];
		double[] percentage = new double[13];
		percentage[0] = 1.0;
		for (int i=1; i<=12; i++) {
			for (int j=1; j<=daysInMonth(year, i); j++) {
				weight[i] += allYearData.data[i][j];
			}
			weight[0] += weight[i];
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
		String[] id = new String[farms.size()];
		int[] weight = new int[farms.size()];
		double[] percentage = new double[farms.size()];
		int totalWeight = 0;
		List<String> idList = farms.getInOrderTraversal();
		int i = 0;
		for (String farmId : idList) {
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
	public TimeReport getAnnualRep(int year) {
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
	public TimeReport getMonthlyRep(int year, int month) {
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
					return 28;
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

//	public static void main(String[] args) throws IOException {
//		Farms f = new Farms();
//		
//		//Read input files
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
//		
//		//Test invalid farm id
//		System.out.println("Test invalid farm id.");
//		try {
//			FarmReport frp = f.getFarmRep(999, 2019);
//			System.out.println("No exception thrown. FAIL");
//		} catch (IllegalArgumentException e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println();
//		
//		//Test invalid year for farm report
//		System.out.println("Test invalid year for farm report.");
//		try {
//			FarmReport frp = f.getFarmRep(20, 2020);
//			System.out.println("No exception thrown. FAIL");
//		} catch (IllegalArgumentException e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println();
//		
//		//Test valid farm report
//		System.out.println("Test valid farm report.");
//		FarmReport frp = f.getFarmRep(18, 2019);
//		System.out.print(frp.getWeight(2) + " ");
//		System.out.print(frp.getPercentage(2));
//		System.out.println();
//		System.out.println();
//		
//		//Test invalid date for date range report.
//		System.out.println("Test invalid date for date range report.");
//		//Case1: invalid year for date range report
//		//In this case, regardless of the month and the date the user
//		//types, the program will return an empty TimeReport,
//		//indicating that no record is found in this year.
//		//The GUI can explain this while displaying report like the following:
//		//"If you find a bunch of 0's and NaN's, then the data base does not
//		//contain data for this year."
//		System.out.println("Case1: invalid year for date range report");
//		try {
//			TimeReport trp = f.getDateRangeRep(2020, 2, 1, 8, 27);
//			for (int i = 0; i <= 2; i++) {
//				System.out.print(trp.getId()[i] + " " + trp.getWeight()[i] 
//						+ " " + trp.getPercentage()[i]);
//				System.out.println();
//			}
//			System.out.println("No exception thrown. PASS");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println();
//		
//		//Test invalid date for date range report.
//		//Case2: valid year but month out of range
//		//In this case, an IllegalArgumentExceptiion with description
//		//will be thrown.
//		System.out.println("Case2: valid year but month out of range");
//		try {
//			TimeReport trp = f.getDateRangeRep(2019, 0, 1, 8, 27);
//			System.out.println("No exception thrown. FAIL");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		try {
//			TimeReport trp = f.getDateRangeRep(2019, 1, 1, 15, 27);
//			System.out.println("No exception thrown. FAIL");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println();
//		
//		//Test invalid date for date range report.
//		//Case3: valid year and month but day out of range
//		//In this case, an IllegalArgumentExceptiion with description
//		//will be thrown.
//		System.out.println("Case3: valid year and month but day out of range");
//		try {
//			TimeReport trp = f.getDateRangeRep(2019, 2, 29, 8, 27);
//			System.out.println("No exception thrown. FAIL");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		try {
//			TimeReport trp = f.getDateRangeRep(2019, 1, 1, 12, 333);
//			System.out.println("No exception thrown. FAIL");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println();
//		
//		//Test invalid date for date range report.
//		//Case4: valid date but start later than end
//		//In this case, an IllegalArgumentExceptiion with description
//		//will be thrown.
//		System.out.println("Case4: valid date but start later than end");
//		try {
//			TimeReport trp = f.getDateRangeRep(2019, 12, 5, 2, 27);
//			System.out.println("No exception thrown. FAIL");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println();
//		
//		
//		//Test valid date range report
//		System.out.println("Test valid date range report");
//		TimeReport trp = f.getDateRangeRep(2019, 2, 28, 9, 27);
//		for (int i = 0; i <= 2; i++) {
//			System.out.print(trp.getId()[i] + " " + trp.getWeight()[i] 
//					+ " " + trp.getPercentage()[i]);
//			System.out.println();
//		}
//		System.out.println();
//		
//		//Test invalid date for monthly report.
//		System.out.println("Test invalid date for monthly report.");
//		//Case1: invalid year for monthly report
//		//In this case, regardless of the month the user
//		//types, the program will return an empty TimeReport,
//		//indicating that no record is found in this year.
//		System.out.println("Case1: invalid year for monthly report");
//		try {
//			TimeReport trp2 = f.getMonthlyRep(2020, 2);
//			for (int i = 0; i <= 2; i++) {
//				System.out.print(trp2.getId()[i] + " " + trp2.getWeight()[i] 
//						+ " " + trp2.getPercentage()[i]);
//				System.out.println();
//			}
//			System.out.println("No exception thrown. PASS");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println();
//
//		//Test invalid date for monthly report.
//		//Case2: valid year but invalid month
//		System.out.println("Case2: valid year but invalid month");
//		try {
//			TimeReport trp2 = f.getMonthlyRep(2019, 23);
//			System.out.println("No exception thrown. FAIL");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println();
//
//		//Test valid monthly report
//		System.out.println("Test valid monthly report");
//		TimeReport trp2 = f.getMonthlyRep(2019, 11);
//		for (int i = 0; i <= 2; i++) {
//			System.out.print(trp2.getId()[i] + " " + trp2.getWeight()[i] 
//					+ " " + trp2.getPercentage()[i]);
//			System.out.println();
//		}
//		System.out.println();
//		
//		//Test invalid year for annual report.
//		System.out.println("Test invalid year for annual report.");
//		//In this case, regardless of the month the user
//		//types, the program will return an empty TimeReport,
//		//indicating that no record is found in this year.
//		try {
//			TimeReport trp3 = f.getAnnualRep(2020);
//			for (int i = 0; i <= 2; i++) {
//				System.out.print(trp3.getId()[i] + " " + trp3.getWeight()[i] 
//						+ " " + trp3.getPercentage()[i]);
//				System.out.println();
//			}
//			System.out.println("No exception thrown. PASS");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		System.out.println();
//		
//		//Test valid annual report
//		System.out.println("Test valid annual report");
//		TimeReport trp3 = f.getAnnualRep(2019);
//		for (int i = 0; i <= 2; i++) {
//			System.out.print(trp3.getId()[i] + " " + trp3.getWeight()[i] 
//					+ " " + trp3.getPercentage()[i]);
//			System.out.println();
//		}
//		System.out.println();
//	}
	
}
