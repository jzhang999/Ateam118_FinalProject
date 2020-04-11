/**
 * A FarmReport represents a single piece of farm report. It stores an array of 
 * int (int[]) and an array of double (double[]). The int array stores the total 
 * milk weight of that farm in each month. The double array stores the percentage 
 * of the milk weight each month.
 * 
 * @author Xinrui Liu
 *
 */
public class FarmReport {
	
	private int[] weight;//the total milk weight of that farm in teach month
	private double[] percentage;//the percentage of milk weight of each month
	
	//The place with index 0 of the 2 arrays stores the total weight/percentage of 
	//the given farm.
	
	/**
	 * Constructs a FarmReport with the given arrays
	 * 
	 * @param weight the array that stores the milk weight in each month
	 * @param percentage the array that stores the percentage of milk weight in each
	 *                   month
	 */
	public FarmReport(int[] weight, double[] percentage) {
		this.weight = weight;
		this.percentage = percentage;
	}
	
	/**
	 * Accessor of the weight array
	 * 
	 * @return weight that stores the milk weight in each month
	 */
	public int[] getWeight() {
		return weight;
	}
	
	/**
	 * Accessor of the milk weight of a given month
	 * 
	 * @param month the number of the target month
	 * @return the milk weight of the given month
	 */
	public int getWeight(int month) {
		return weight[month];
	}
	
	/**
	 * Accessor of the total milk weight of this farm in a given year
	 * 
	 * @return the total milk weight of this farm in a given year
	 */
	public int getTotalWeight() {
		return weight[0];
	}
	
	/**
	 * Accessor of the percentage array
	 * 
	 * @return the percentage array
	 */
	public double[] getPercentage() {
		return percentage;
	}
	
	/**
	 * Accessor of the percentage of a given month
	 * 
	 * @param month the number of the month
	 * @return the percentage of milk weight in this month
	 */
	public double getPercentage(int month) {
		return percentage[month];
	}
	
	/**
	 * Accessor of the total percentage, which is 1.0
	 * 
	 * @return 1.0
	 */
	public double getTotalPercentage() {
		return percentage[0];
	}
}
