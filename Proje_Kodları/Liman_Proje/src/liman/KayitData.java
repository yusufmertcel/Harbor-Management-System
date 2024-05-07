package liman;

import java.sql.Date;
import java.util.Vector;

public class KayitData {
	private int kyt_id;
	private int gemi_id;
	private String klks_yer;
	private String vrs_yer;
	private Date klks_trh;
	private Date vrs_trh;
	Vector<Object> data4;
	
	
	public KayitData(int kyt_id, int gemi_id,String klks_yer, String vrs_yer, Date klks_trh,
			Date vrs_trh) {
		super();
		this.kyt_id = kyt_id;
		this.gemi_id = gemi_id;
		this.klks_yer = klks_yer;
		this.vrs_yer = vrs_yer;
		this.klks_trh = klks_trh;
		this.vrs_trh = vrs_trh;
		
		data4 = new Vector<Object>();
		data4.add(kyt_id);
		data4.add(gemi_id);
		data4.add(klks_yer);
		data4.add(vrs_yer);
		data4.add(klks_trh);
		data4.add(vrs_trh);
	}
	public int getKyt_id() {
		return kyt_id;
	}
	public void setKyt_id(int kyt_id) {
		this.kyt_id = kyt_id;
	}
	public int getGemi_id() {
		return gemi_id;
	}
	public void setGemi_id(int gemi_id) {
		this.gemi_id = gemi_id;
	}
	public String getKlks_yer() {
		return klks_yer;
	}
	public void setKlks_yer(String klks_yer) {
		this.klks_yer = klks_yer;
	}
	public String getVrs_yer() {
		return vrs_yer;
	}
	public void setVrs_yer(String vrs_yer) {
		this.vrs_yer = vrs_yer;
	}
	public Date getKlks_trh() {
		return klks_trh;
	}
	public void setKlks_trh(Date klks_trh) {
		this.klks_trh = klks_trh;
	}
	public Date getVrs_trh() {
		return vrs_trh;
	}
	public void setVrs_trh(Date vrs_trh) {
		this.vrs_trh = vrs_trh;
	}
	public Vector<Object> getData4() {
		return data4;
	}
	public void setData4(Vector<Object> data4) {
		this.data4 = data4;
	}

	
}
