package AnyQuantProject.dataStructure;

import java.text.DecimalFormat;

/**
 * Created by G on 16/4/1.
 */
public class IndustryInfo {
    String industry;
    double updown;//涨跌幅,百分数
    double pure;//净额,
    double total;
    int companySum;
    double price;
    String leader;
    double leaderPrice;
    double leaderUpdown;
    public void setCompanySum(int companySum) {
		this.companySum = companySum;
	}

	@Deprecated
    public IndustryInfo(){
    	updown=0;
    }

    public IndustryInfo(String name){
        this.industry=name;
    }

    public void  setUpdown(double updown){
    	DecimalFormat  df  = new DecimalFormat(".000");   
    	updown =Double.parseDouble(df.format(updown));
    	//updown = Double.parseDouble(String .format("%.3f",updown ));
        this.updown=updown;
    }
    public  void  setPure(double pure){
    	pure/=1e8;
    	DecimalFormat    df   = new DecimalFormat(".000");   
		 pure =Double.parseDouble(df.format( pure));
        this.pure=pure;
    }
    public void setTotal(double total){
    	this.total=total;
    }
    
    public void setPrice(double price) {
		this.price = price;
	}
    
	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	public void setLeaderPrice(double leaderPrice) {
		this.leaderPrice = leaderPrice;
	}

	public void setLeaderUpdown(double leaderUpdown) {
		// leaderUpdown	= Double.parseDouble(String .format("%.3f", leaderUpdown ));
		DecimalFormat    df   = new DecimalFormat(".000");   
		 leaderUpdown =Double.parseDouble(df.format( leaderUpdown));
		this.leaderUpdown = leaderUpdown;
	}

	
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public double getUpdown() {
		return updown;
	}

	public double getPure() {
		return pure;
	}

	public double getTotal() {
		return total;
	}

	public int getCompanySum() {
		return companySum;
	}

	public double getPrice() {
		return price;
	}

	public String getLeader() {
		return leader;
	}

	public double getLeaderPrice() {
		return leaderPrice;
	}

	public double getLeaderUpdown() {
		return leaderUpdown;
	}

	@Override
    public String toString(){
    	return "Industry "+this.industry+" updown pure total "+updown+" "+pure+" "+total;
    }
}
