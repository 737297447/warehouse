package com.lingdian.ui.main.bean;

public class SaleBean implements Comparable<SaleBean>{

	private String id;
	private String spname;
	private Integer spprise;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpname() {
		return spname;
	}

	public void setSpname(String spname) {
		this.spname = spname;
	}


	public Integer getSpprise() {
		return spprise;
	}

	public void setSpprise(Integer spprise) {
		this.spprise = spprise;
	}

	public String getSpnum() {
		return spnum;
	}

	public void setSpnum(String spnum) {
		this.spnum = spnum;
	}

	private String spnum;

	
	@Override
	public int compareTo(SaleBean arg0) {
		// TODO Auto-generated method stub
		
		return this.getSpprise().compareTo(arg0.getSpprise());
	}

}
