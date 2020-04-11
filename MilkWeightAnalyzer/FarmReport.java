
public class FarmReport {
	
	private int[] weight;
	private double[] percentage;
	
	public FarmReport(int[] weight, double[] percentage) {
		this.weight = weight;
		this.percentage = percentage;
	}
	
	public int[] getWeight() {
		return weight;
	}
	
	public int getWeight(int month) {
		return weight[month];
	}
	
	public int getTotalWeight() {
		return weight[0];
	}
	
	public double[] getPercentage() {
		return percentage;
	}
	
	public double getPercentage(int month) {
		return percentage[month];
	}
	
	public double getTotalPercentage() {
		return percentage[0];
	}
}
