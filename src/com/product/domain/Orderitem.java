package com.product.domain;
//itemidvarchar(50) NOT NULL
//countint(11) NULL
//pidvarchar(50) NULL
//oidvarchar(50) NULL
public class Orderitem {
	private String itemid;	//�����Լ����id
	private int count;		//��Ʒ����
	private String pid;		//��Ʒid
	private String oid;		//����id
	private Product p;		//����pid�ҵ���Ӧ����Ʒ
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getCount() {
		return count;
	}
	public Orderitem(String itemid, int count, String pid, String oid, Product p) {
		super();
		this.itemid = itemid;
		this.count = count;
		this.pid = pid;
		this.oid = oid;
		this.p = p;
	}
	public Orderitem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	@Override
	public String toString() {
		return "Orderitem [itemid=" + itemid + ", count=" + count + ", pid=" + pid + ", oid=" + oid + ", p=" + p + "]";
	}
	
}
