import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Farms {
	
	private RBT<Integer, Farm> farms;
	
	public Farms() {
		this.farms = new RBT<Integer, Farm>();
	}
	
	public void readCsvFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String newLine = br.readLine();
		newLine = br.readLine();
		while (newLine != null) {
			String[] line = newLine.split(",");
			if (line.length == 3) {
				String[] date = line[0].split("-");
				String[] farm = line[1].split(" ");
				String milkWeight = line[2];
				try {
					int year = Integer.parseInt(date[0]);
					int month = Integer.parseInt(date[1]);
					int day = Integer.parseInt(date[2]);
					int id = Integer.parseInt(farm[1]);
					int weight = Integer.parseInt(milkWeight);
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
				} catch (Exception e) {
					
				}
			}
			newLine = br.readLine();
		}
	}
	
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
			weight[i] = farms.get(farmId).getWeightInRange(startY, startM, startD, endM, endD);
			totalWeight += weight[i];
			i++;
		}
		for (int j=0; j<percentage.length; j++) {
			percentage[j] = ((double) weight[j]) / ((double) totalWeight);
		}
		return new TimeReport(id, weight, percentage, totalWeight);
	}
	
	public TimeReport getAnnualReport(int year) {
		return this.getDateRangeRep(year, 1, 1, 12, 31);
	}
	
	public TimeReport getMonthlyReport(int year, int month) {
		return this.getDateRangeRep(year, month, 1, month, this.daysInMonth(year, month));
	}
	
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
	
	public static void main(String[] args) throws IOException {
		Farms f = new Farms();
		f.readCsvFile("2019-1.csv");
		System.out.println();
		FarmReport frp = f.getFarmRep(0, 2019);
		System.out.println(frp.getTotalWeight());
		TimeReport trp = f.getDateRangeRep(2019, 1, 1, 1, 31);
		for (int i = 0; i <= 2; i++) {
			System.out.print(trp.getId()[i] + " " + trp.getWeight()[i] + " " + trp.getPercentage()[i]);
			System.out.println();
		}
		TimeReport trp2 = f.getMonthlyReport(2019, 1);
		for (int i = 0; i <= 2; i++) {
			System.out.print(trp.getId()[i] + " " + trp.getWeight()[i] + " " + trp.getPercentage()[i]);
			System.out.println();
		}
		TimeReport trp3 = f.getAnnualReport(2019);
		for (int i = 0; i <= 2; i++) {
			System.out.print(trp.getId()[i] + " " + trp.getWeight()[i] + " " + trp.getPercentage()[i]);
			System.out.println();
		}
	}
}
