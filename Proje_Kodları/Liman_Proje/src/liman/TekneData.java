package liman;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.Vector;

public class TekneData {
	private int tekne_id;
	private String model;
	private int liman_id;
	private String owner_id;
	private float uzunluk;
	private int motor_gucu;
	private float vergi;
	private String tekne_ad;
	private String denizci_belgesi;
	private short temp_num;
	private int yuk_id;
	private String plaka;
	Vector<Object> data;
	
	
	
	public TekneData(int tekne_id, String model, int liman_id, String owner_id, float uzunluk, int motor_gucu,
			float vergi, String tekne_ad, String denizci_belgesi, short temp_num, int yuk_id, String plaka) {
		super();
		this.tekne_id = tekne_id;
		this.model = model;
		this.liman_id = liman_id;
		this.owner_id = owner_id;
		this.uzunluk = uzunluk;
		this.motor_gucu = motor_gucu;
		this.vergi = vergi;
		this.tekne_ad = tekne_ad;
		this.denizci_belgesi = denizci_belgesi;
		this.temp_num = temp_num;
		this.yuk_id = yuk_id;
		this.plaka = plaka;
		data = new Vector<Object>();
		data.add(tekne_id);
		data.add(model);
		data.add(liman_id);
		data.add(owner_id);
		data.add(uzunluk);
		data.add(motor_gucu);
		data.add(vergi);
		data.add(tekne_ad);
		data.add(denizci_belgesi);
		data.add(temp_num);
		data.add(yuk_id);
		data.add(plaka);
		

	}
	

	public int getTekne_id() {
		return tekne_id;
	}
	public void setTekne_id(int tekne_id) {
		this.tekne_id = tekne_id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getLiman_id() {
		return liman_id;
	}
	public void setLiman_id(int liman_id) {
		this.liman_id = liman_id;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public float getUzunluk() {
		return uzunluk;
	}
	public void setUzunluk(float uzunluk) {
		this.uzunluk = uzunluk;
	}
	public int getMotor_gucu() {
		return motor_gucu;
	}
	public void setMotor_gucu(int motor_gucu) {
		this.motor_gucu = motor_gucu;
	}
	public float getVergi() {
		return vergi;
	}
	public void setVergi(float vergi) {
		this.vergi = vergi;
	}
	public String getTekne_ad() {
		return tekne_ad;
	}
	public void setTekne_ad(String tekne_ad) {
		this.tekne_ad = tekne_ad;
	}
	public String getDenizci_belgesi() {
		return denizci_belgesi;
	}
	public void setDenizci_belgesi(String denizci_belgesi) {
		this.denizci_belgesi = denizci_belgesi;
	}
	public short getTemp_num() {
		return temp_num;
	}
	public void setTemp_num(short temp_num) {
		this.temp_num = temp_num;
	}
	public int getYuk_id() {
		return yuk_id;
	}
	public void setYuk_id(int yuk_id) {
		this.yuk_id = yuk_id;
	}
	public String getPlaka() {
		return plaka;
	}
	public void setPlaka(String plaka) {
		this.plaka = plaka;
	}
	
}
