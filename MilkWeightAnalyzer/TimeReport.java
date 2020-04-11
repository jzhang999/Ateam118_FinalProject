
public class TimeReport {
	
	private int[] id;
	private int[] weight;
	private double[] percentage;
	private int totalWeight;
	
	public TimeReport(int[] id, int[] weight, double[] percentage, int totalWeight) {
		this.id = id;
		this.weight = weight;
		this.percentage = percentage;
		this.totalWeight = totalWeight;
	}
	
	public int[] getId() {
		return id;
	}
	
	public int[] getWeight() {
		return weight;
	}
	
	public double[] getPercentage() {
		return percentage;
	}
}
