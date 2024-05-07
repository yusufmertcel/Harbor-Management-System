package liman;

import java.util.Vector;

public class SahipData {
	private String owner_id;
	private String ad_soyad;
	private String tel_no;
	private String adres;
	private String mail;
	private String hesap_no;
	Vector<Object> data3;
	
	
	public SahipData(String owner_id, String ad_soyad, String tel_no, String adres, String mail, String hesap_no) {
		super();
		this.owner_id = owner_id;
		this.ad_soyad = ad_soyad;
		this.tel_no = tel_no;
		this.adres = adres;
		this.mail = mail;
		this.hesap_no = hesap_no;
		
		data3 = new Vector<Object>();
		data3.add(owner_id);
		data3.add(ad_soyad);
		data3.add(tel_no);
		data3.add(adres);
		data3.add(mail);
		data3.add(hesap_no);
		
	}
	public Vector<Object> getData3() {
		return data3;
	}
	public void setData3(Vector<Object> data3) {
		this.data3 = data3;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getAd_soyad() {
		return ad_soyad;
	}
	public void setAd_soyad(String ad_soyad) {
		this.ad_soyad = ad_soyad;
	}
	public String getTel_no() {
		return tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getHesap_no() {
		return hesap_no;
	}
	public void setHesap_no(String hesap_no) {
		this.hesap_no = hesap_no;
	}

}
