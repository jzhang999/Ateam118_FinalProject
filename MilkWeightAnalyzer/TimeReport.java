/**
 * A TimeReport represents a single piece of report that contains all the farms for 
 * a certain range of time. It stores several arrays for farm id, weight, and 
 * percentage.
 * 
 * @author Xinrui Liu
 *
 */
public class TimeReport {
	
	private int[] id;//The farm id that is stored in ascending order
	private int[] weight;//The total milk weight of the farms
	private double[] percentage;//The percentage of the milk weight of each farm
	private int totalWeight;//The total weight of the milk
	
	/*
	 * The weight and the percentage are stored in the same order with the ID.
	 * 
	 * For example, if there are 3 farms with ID 1, 2 and 3:
	 * id:         {         1,                   2,                   3         } 
	 * weight:     { weight for farm 1,   weight for farm 2,   weight for farm 3 }
	 * percentage: {   % for farm 1,       % for farm 2,        % for farm 3,    }
	 * 
	 * To access the data corresponding to each farm in a TimeReport called trp, 
	 * just write a for loop like:
	 * 
	 * for (int i=0; i<trp.getID().length; i++) {
	 * 		int curID = trp.getID[i];
	 *  	int curWeight = trp.getWeight[i];
	 *  	int curPercentage = trp.getPercentage[i];
	 *      //Then, you can get the data corresponds to the farm with the id curID.
	 *      //After that, you can put them into a label or a chart to display them.
	 * }
	 */
	
	/**
	 * Constructs a TimeReport with the given parameters
	 * 
	 * @param id the array that stores all the farm IDs.
	 * @param weight the array that stores the total milk weight for each farm
	 * @param percentage the array that stores the percentage of milk weight for 
	 *                   each farm
	 * @param totalWeight the int that stores the total weight of the milk of all 
	 *                    farms
	 */
	public TimeReport(int[] id, int[] weight, double[] percentage, int totalWeight) {
		this.id = id;
		this.weight = weight;
		this.percentage = percentage;
		this.totalWeight = totalWeight;
	}
	
	/**
	 * Accessor of the id array
	 * 
	 * @return the id array
	 */
	public int[] getId() {
		return id;
	}
	
	/**
	 * Accessor of the weight array
	 * 
	 * @return the weight array
	 */
	public int[] getWeight() {
		return weight;
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
	 * Accessor of the total milk weight of all farms
	 * 
	 * @return the total milk weight of all farms
	 */
	public int getTotalWeight() {
		return totalWeight;
	}
}
