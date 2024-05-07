package liman;

import java.sql.Date;
import java.util.Vector;

public class LimanData {
	private int liman_id;
	private String liman_Ad;
	private String adres;
	private String sehir;
	private int lemp_num;
	private int ship_num;
	Vector<Object> data2;
	
	
	public LimanData(int liman_id, String liman_Ad, String adres, String sehir, int lemp_num, int ship_num) {
		super();
		this.liman_id = liman_id;
		this.liman_Ad = liman_Ad;
		this.adres = adres;
		this.sehir = sehir;
		this.lemp_num = lemp_num;
		this.ship_num = ship_num;
		
		data2 = new Vector<Object>();
		data2.add(liman_id);
		data2.add(liman_Ad);
		data2.add(adres);
		data2.add(sehir);
		data2.add(lemp_num);
		data2.add(ship_num);

	}
	public int getLiman_id() {
		return liman_id;
	}
	public void setLiman_id(int liman_id) {
		this.liman_id = liman_id;
	}
	public String getLiman_Ad() {
		return liman_Ad;
	}
	public void setLiman_Ad(String liman_Ad) {
		this.liman_Ad = liman_Ad;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getSehir() {
		return sehir;
	}
	public void setSehir(String sehir) {
		this.sehir = sehir;
	}
	public int getLemp_num() {
		return lemp_num;
	}
	public void setLemp_num(int lemp_num) {
		this.lemp_num = lemp_num;
	}
	public int getShip_num() {
		return ship_num;
	}
	public void setShip_num(int ship_num) {
		this.ship_num = ship_num;
	}
	public Vector<Object> getData() {
		return data2;
	}
	public void setData(Vector<Object> data) {
		this.data2 = data;
	}


}
