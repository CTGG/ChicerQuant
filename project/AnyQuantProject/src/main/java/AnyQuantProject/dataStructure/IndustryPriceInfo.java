package AnyQuantProject.dataStructure;
/**
* AnyQuantProject/AnyQuantProject.dataStructure/IndustryPriceInfo.java
* @author cxworks
* 2016年4月12日 下午10:54:56
*/
public class IndustryPriceInfo {
	String industry;
	double open;
	double close;
	double max;
	double min;
	@Deprecated
	public IndustryPriceInfo() {
	}
	public IndustryPriceInfo(String industry){
		this.industry=industry;
	}
	
	public void setOpen(double open) {
		this.open = open;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public void setMax(double max) {
		this.max = max;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public double getOpen() {
		return open;
	}
	public double getClose() {
		return close;
	}
	public double getMax() {
		return max;
	}
	public double getMin() {
		return min;
	}
	
	
}
