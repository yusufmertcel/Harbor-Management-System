package liman;

import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TekneIslemleri {

	public JFrame tnkframe;
	private JTextField txtTekneId;
	private JTable table;
	private JTextField txtUzunluk;
	private JTextField txtMotorGucu;
	private JTextField txtVergi;
	private JTextField txtTekneAd;
	private JTextField txtCalisanSayi;
	private JTextField txtYukId;
	private JTextField txtPlaka;
	private JComboBox cmbTekneTipi;
	private JComboBox cmbModelId;
	private JComboBox cmbDenizciBel;
	private JComboBox cmbLimanId;
	private JComboBox cmbOwnerId ;
	private JButton Ekle;
	private JButton btnSil;
	Vector<Object> tekneVector = new Vector<Object>();
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tekne ID", "Model", "Liman ID", "Owner ID", "Uzunluk", "Motor G\u00FCc\u00FC", "Vergi", "Tekne Ad\u0131", "Denizci Belgesi", "\u00C7al\u0131\u015Fan Says\u0131", "Y\u00FCk ID", "Plaka"
			}
		);
	private JTextField txtDest;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TekneIslemleri window = new TekneIslemleri();
					window.tnkframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TekneIslemleri() {
		initialize();
		sifirla();
	
	}
	
	public void delete_tekne() {
		String url ="jdbc:postgresql://localhost/Liman"; 
		boolean error = false;
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		
		try {
			String teknetipi = cmbTekneTipi.getSelectedItem().toString();
			String denizciBelgesi;
			float vergi = 0;
			Connection db=DriverManager.getConnection(url, prop);
			PreparedStatement pStmt = db.prepareStatement("DELETE FROM tekne WHERE tekne_id=?");
			pStmt.setInt(1, Integer.parseInt(txtTekneId.getText()));
			
			
			pStmt.executeUpdate();
				
			db.close();
		}catch(SQLException e) {
			error = true;
			e.printStackTrace();
		}
		catch(NullPointerException e) {
			error = true;
			JOptionPane.showMessageDialog(tnkframe, "Deger girmediniz lutfen deger girip sil butonuna basýnýz.", "NULL", JOptionPane.ERROR_MESSAGE);
		}
		finally {
			if(!error)
				JOptionPane.showMessageDialog(tnkframe, "Tekne kayýdý baþarýlý bir þekilde silinmiþtir.");		
		}
	}
	
	public void update_tekne() {
		String url ="jdbc:postgresql://localhost/Liman"; 
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		String teknetipi = cmbTekneTipi.getSelectedItem().toString();
		String denizciBelgesi;
		try {
			float vergi = 0;
			Connection db=DriverManager.getConnection(url, prop);
			db.setAutoCommit(false);
			int motorGucu = Integer.parseInt(txtMotorGucu.getText());
			if(motorGucu > 10) {
				vergi = (motorGucu - 10) * 100.3f;
			}
			PreparedStatement pStmt = db.prepareStatement("UPDATE tekne SET tekne_id=?, model=?, liman_id=?, owner_id=?, uzunluk=?, motor_gucu=?, vergi=? "
					+ "WHERE tekne_id=?");
			pStmt.setInt(1, Integer.parseInt(txtTekneId.getText()));
			pStmt.setString(2, cmbModelId.getSelectedItem().toString());
			pStmt.setInt(3, Integer.parseInt(cmbLimanId.getSelectedItem().toString()));
			pStmt.setString(4, cmbOwnerId.getSelectedItem().toString());
			pStmt.setFloat(5, Float.parseFloat(txtUzunluk.getText()));
			pStmt.setInt(6, Integer.parseInt(txtMotorGucu.getText()));
			pStmt.setFloat(7, vergi);
			pStmt.setInt(8, Integer.parseInt(txtTekneId.getText()));	
			pStmt.executeUpdate();
			db.commit();		
			if(teknetipi == "Ozel Tekne") {
				if(Integer.parseInt(txtUzunluk.getText()) > 6)
					denizciBelgesi = "1";
				else
					denizciBelgesi = "0";
				pStmt = db.prepareStatement("UPDATE ozel_tekne SET ot_id=?, tekne_ad=?, denizci_belgesi=? WHERE ot_id=?");
				pStmt.setInt(1, Integer.parseInt(txtTekneId.getText()));
				pStmt.setString(2, txtTekneAd.getText());
				pStmt.setString(3, denizciBelgesi);
				pStmt.setInt(4, Integer.parseInt(txtTekneId.getText()));
				pStmt.executeUpdate();
			}
			else{
	
				pStmt = db.prepareStatement("UPDATE ticari_tekne SET tt_id=?, temp_num=?, yuk_id=currval('yuk_seq'), plaka=? WHERE tt_id=?");
				pStmt.setInt(1, Integer.parseInt(txtTekneId.getText()));
				pStmt.setInt(2, Integer.parseInt(txtCalisanSayi.getText()));		
				pStmt.setString(3, txtPlaka.getText());
				pStmt.setInt(4, Integer.parseInt(txtTekneId.getText()));
				pStmt.executeUpdate();
			}
			db.commit();
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally{
			JOptionPane.showMessageDialog(tnkframe, "Tekne kayýdý baþarýlý bir þekilde gerçekleþtirilmiþtir.");
		}
	}
	
	public Vector<Object> select_row(){
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        }
	    });
		return tekneVector;
	}
	public void sifirla() {
		cmbTekneTipi.setSelectedIndex(-1);
		cmbModelId.setSelectedIndex(-1);
		cmbLimanId.setSelectedIndex(-1);
		cmbOwnerId.setSelectedIndex(-1);
		cmbDenizciBel.setSelectedIndex(-1);
	}
	
	public void insert_tekne() {
		String url ="jdbc:postgresql://localhost/Liman"; 
		boolean error = false;
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		try {
			String teknetipi = cmbTekneTipi.getSelectedItem().toString();
			String denizciBelgesi;
			float vergi = 0;
			Connection db=DriverManager.getConnection(url, prop);
			db.setAutoCommit(false);
			int motorGucu = Integer.parseInt(txtMotorGucu.getText());
			if(motorGucu > 10) {
				vergi = (motorGucu - 10) * 100.3f;
			}
			PreparedStatement pStmt = db.prepareStatement("INSERT INTO tekne VALUES(?,?,?,?,?,?,?)");
			pStmt.setInt(1, Integer.parseInt(txtTekneId.getText()));
			pStmt.setString(2, cmbModelId.getSelectedItem().toString());
			pStmt.setInt(3, Integer.parseInt(cmbLimanId.getSelectedItem().toString()));
			pStmt.setString(4, cmbOwnerId.getSelectedItem().toString());
			pStmt.setFloat(5, Float.parseFloat(txtUzunluk.getText()));
			pStmt.setInt(6, Integer.parseInt(txtMotorGucu.getText()));
			pStmt.setFloat(7, vergi);
			pStmt.executeUpdate();
			db.commit();	
			if(teknetipi == "Ozel Tekne") {
				if(Integer.parseInt(txtUzunluk.getText()) > 6)
					denizciBelgesi = "1";
				else
					denizciBelgesi = "0";
				pStmt = db.prepareStatement("INSERT INTO ozel_tekne VALUES(?,?,?)");
				pStmt.setInt(1, Integer.parseInt(txtTekneId.getText()));
				pStmt.setString(2, txtTekneAd.getText());
				pStmt.setString(3, denizciBelgesi);
				pStmt.executeUpdate();
			}
			else{
				pStmt = db.prepareStatement("INSERT INTO ticari_tekne VALUES(?,?,nextval('yuk_seq'),?)");
				pStmt.setInt(1, Integer.parseInt(txtTekneId.getText()));
				pStmt.setInt(2, Integer.parseInt(txtCalisanSayi.getText()));
				pStmt.setString(3, txtPlaka.getText());
				pStmt.executeUpdate();
			}
			db.commit();
			db.close();
		}catch(SQLException e) {
			error = true;
			e.printStackTrace();
		}
		catch(NullPointerException e) {
			error = true;
			JOptionPane.showMessageDialog(tnkframe, "Deger girmediniz lutfen deger girip ekle butonuna basýnýz.", "NULL", JOptionPane.ERROR_MESSAGE);
		}
		finally {
			if(!error)
				JOptionPane.showMessageDialog(tnkframe, "Tekne kayýdý baþarýlý bir þekilde gerçekleþtirilmiþtir.");			
		}
		
	}
	
	public void destination(String ID) {
		String url ="jdbc:postgresql://localhost/Liman";
		String sql ="select * from tekne_ozet(?)";
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		
		try {
			Connection db=DriverManager.getConnection(url, prop);
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(ID));
			ResultSet rset= pstmt.executeQuery();
			
			
			while(rset.next()) {
				if(rset.getObject(1)!= null) {
					txtDest.setText("From :" + rset.getObject(1).toString() +"To :" +rset.getObject(2).toString());
				}else {
					txtDest.setText("Kayýt Bulunamadý");
				}
				
			}
			
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void connect() {
		String url ="jdbc:postgresql://localhost/Liman";
		String sql ="select * from tekne_bilgi";
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		
		try {
			Connection db=DriverManager.getConnection(url, prop);
			Statement stmt = db.createStatement();
			ResultSet rset=stmt.executeQuery(sql);
			//ResultSetMetaData rsmd = rset.getMetaData();
			
			while(rset.next()) {
				TekneData datum = new TekneData(rset.getInt("tekne_id"),rset.getString("model"),rset.getInt("liman_id"),rset.getString("owner_id"),
						rset.getFloat("uzunluk"),rset.getInt("motor_gucu"),rset.getFloat("vergi"),rset.getString("tekne_ad"),
						rset.getString("denizci_belgesi"),rset.getShort("temp_num"),rset.getInt("yuk_id"),rset.getString("plaka")
						); 
				tekneVector.add(datum);
				for(Integer i = 0; i < tekneVector.size(); i++) {
					TekneData ex = (TekneData)tekneVector.get(i);
				}
				model.addRow(datum.data);
			}
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		tnkframe = new JFrame();
		tnkframe.setBounds(100, 100, 1273, 668);
		tnkframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		txtTekneId = new JTextField();
		txtTekneId.setBounds(231, 58, 200, 19);
		panel.add(txtTekneId);
		txtTekneId.setColumns(10);
		
		txtUzunluk = new JTextField();
		txtUzunluk.setColumns(10);
		txtUzunluk.setBounds(231, 178, 200, 19);
		panel.add(txtUzunluk);
		
		txtMotorGucu = new JTextField();
		txtMotorGucu.setColumns(10);
		txtMotorGucu.setBounds(231, 208, 200, 19);
		panel.add(txtMotorGucu);
		
		txtVergi = new JTextField();
		txtVergi.setEditable(false);
		txtVergi.setColumns(10);
		txtVergi.setBounds(231, 238, 200, 19);
		panel.add(txtVergi);
		
		
		cmbTekneTipi = new JComboBox();
		cmbTekneTipi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(cmbTekneTipi.getSelectedItem() == "Ozel Tekne") {
					txtTekneAd.setEnabled(true);
					txtCalisanSayi.setEnabled(false);
					txtYukId.setEnabled(false);
					txtPlaka.setEnabled(false);
				}
				else{
					txtCalisanSayi.setEnabled(true);
					txtYukId.setEnabled(true);
					txtPlaka.setEnabled(true);
					txtTekneAd.setEnabled(false);
				}
			}
		});
		cmbTekneTipi.setModel(new DefaultComboBoxModel(new String[] {"Ozel Tekne", "Ticari Tekne"}));
		cmbTekneTipi.setBounds(231, 28, 200, 19);
		panel.add(cmbTekneTipi);
		
		JLabel lblTekneTipi = new JLabel("Tekne Tipi:");
		lblTekneTipi.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTekneTipi.setBounds(36, 28, 150, 19);
		panel.add(lblTekneTipi);
		
		JLabel lblTekneId = new JLabel("Tekne ID:");
		lblTekneId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTekneId.setBounds(36, 58, 150, 19);
		panel.add(lblTekneId);
		
		JLabel lblModelId = new JLabel("Model:");
		lblModelId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblModelId.setBounds(36, 88, 150, 19);
		panel.add(lblModelId);
		
		JLabel lblLimanId = new JLabel("Liman ID:");
		lblLimanId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblLimanId.setBounds(36, 118, 150, 19);
		panel.add(lblLimanId);
		
		JLabel lblOwnerId = new JLabel("Owner ID:");
		lblOwnerId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblOwnerId.setBounds(36, 148, 150, 19);
		panel.add(lblOwnerId);
		
		JLabel lblUzunluk = new JLabel("Uzunluk:");
		lblUzunluk.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblUzunluk.setBounds(36, 178, 150, 19);
		panel.add(lblUzunluk);
		
		JLabel lblMotorGucu = new JLabel("Motor G\u00FCc\u00FC:");
		lblMotorGucu.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblMotorGucu.setBounds(36, 207, 150, 19);
		panel.add(lblMotorGucu);
		
		JLabel lblVergi = new JLabel("Vergi:");
		lblVergi.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVergi.setBounds(36, 238, 150, 19);
		panel.add(lblVergi);
		
		JLabel lblTekneAdý = new JLabel("Tekne Ad\u0131:");
		lblTekneAdý.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTekneAdý.setBounds(637, 31, 150, 19);
		panel.add(lblTekneAdý);
		GroupLayout groupLayout = new GroupLayout(tnkframe.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 1249, GroupLayout.PREFERRED_SIZE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 621, GroupLayout.PREFERRED_SIZE)
		);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();			
				txtTekneId.setText(model.getValueAt(index, 0).toString());
				cmbModelId.setSelectedItem(model.getValueAt(index, 1).toString());
				cmbLimanId.setSelectedItem(model.getValueAt(index, 2).toString());	
				cmbOwnerId.setSelectedItem(model.getValueAt(index, 3).toString());
				txtUzunluk.setText(model.getValueAt(index, 4).toString());
				txtMotorGucu.setText(model.getValueAt(index, 5).toString());
				txtVergi.setText(model.getValueAt(index, 6).toString());
				
				if(model.getValueAt(index, 7) != null)
				txtTekneAd.setText(model.getValueAt(index, 7).toString());
				if(model.getValueAt(index, 8) != null) {
					String denizcibelgesi = model.getValueAt(index, 8).toString();
					if(denizcibelgesi.compareTo("1") == 0) {
						cmbDenizciBel.setSelectedItem("Var");
					}else {
						cmbDenizciBel.setSelectedItem("Yok");
					}
				}
				
				//ekle		
				destination(txtTekneId.getText());
				txtCalisanSayi.setText(model.getValueAt(index, 9).toString());
				txtYukId.setText(model.getValueAt(index, 10).toString());
				
				if(model.getValueAt(index, 11) != null)
				txtPlaka.setText(model.getValueAt(index, 11).toString());
			}
		});
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.setModel(model);
		table.setBounds(37, 388, 225, -332);
		//panel.add(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		JScrollPane scrollPane_1 = new JScrollPane(table);
		scrollPane_1.setBounds(10, 306, 1229, 315);
		panel.add(scrollPane_1);
		
		JLabel lblDenizciBel = new JLabel("Denizci Belgesi:");
		lblDenizciBel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDenizciBel.setBounds(637, 58, 150, 19);
		panel.add(lblDenizciBel);
		
		JLabel lblÇalýþanSayý = new JLabel("\u00C7al\u0131\u015Fan Say\u0131s\u0131:");
		lblÇalýþanSayý.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblÇalýþanSayý.setBounds(637, 88, 150, 19);
		panel.add(lblÇalýþanSayý);
		
		JLabel lblYukId = new JLabel("Y\u00FCk ID:");
		lblYukId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblYukId.setBounds(637, 118, 150, 19);
		panel.add(lblYukId);
		
		JLabel lblPlaka = new JLabel("Plaka:");
		lblPlaka.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPlaka.setBounds(637, 148, 150, 19);
		panel.add(lblPlaka);
		
		cmbModelId = new JComboBox();
		cmbModelId.setModel(new DefaultComboBoxModel(new String[] {"Dufour", "Hanse ", "Elan", "Jeanneau", "Benetau", "Bavaria", "Catana", "Leopard"}));
		cmbModelId.setBounds(231, 89, 200, 19);
		panel.add(cmbModelId);
		
		cmbDenizciBel = new JComboBox();
		cmbDenizciBel.setModel(new DefaultComboBoxModel(new String[] {"Var", "Yok"}));
		cmbDenizciBel.setEnabled(false);
		cmbDenizciBel.setBounds(815, 57, 200, 19);
		panel.add(cmbDenizciBel);
		
		txtTekneAd = new JTextField();
		txtTekneAd.setColumns(10);
		txtTekneAd.setBounds(815, 28, 200, 19);
		panel.add(txtTekneAd);
		
		txtCalisanSayi = new JTextField();
		txtCalisanSayi.setColumns(10);
		txtCalisanSayi.setBounds(815, 91, 200, 19);
		panel.add(txtCalisanSayi);
		
		txtYukId = new JTextField();
		txtYukId.setColumns(10);
		txtYukId.setBounds(815, 118, 200, 19);
		panel.add(txtYukId);
		
		txtPlaka = new JTextField();
		txtPlaka.setColumns(10);
		txtPlaka.setBounds(815, 148, 200, 19);
		panel.add(txtPlaka);
		
		Ekle = new JButton("EKLE\r\n");
		Ekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert_tekne();
			}
		});
		Ekle.setFont(new Font("Tahoma", Font.BOLD, 12));
		Ekle.setBounds(1078, 95, 120, 44);
		panel.add(Ekle);
		
		btnSil = new JButton("S\u0130L");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete_tekne();
			}
		});
		btnSil.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSil.setBounds(1078, 165, 120, 44);
		panel.add(btnSil);
		
		JButton btnGncelle = new JButton("G\u00DCNCELLE");
		btnGncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update_tekne();
			}
		});
		btnGncelle.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnGncelle.setBounds(1078, 235, 120, 44);
		panel.add(btnGncelle);
		
		JButton btnListele = new JButton("L\u0130STELE");
		btnListele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				connect();
			}
		});
		btnListele.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnListele.setBounds(1078, 25, 120, 44);
		panel.add(btnListele);
		
		cmbLimanId = new JComboBox();
		cmbLimanId.setModel(new DefaultComboBoxModel(new String[] {"1000", "1001", "1002", "1003", "1004", "1005", "1006", "1007", "1008"}));
		cmbLimanId.setBounds(231, 120, 200, 19);
		panel.add(cmbLimanId);
		
		cmbOwnerId = new JComboBox();
		cmbOwnerId.setModel(new DefaultComboBoxModel(new String[] {"55090358900", "47758075812", "12489563874", "28595012456", "12598645782"}));
		cmbOwnerId.setBounds(231, 149, 200, 19);
		panel.add(cmbOwnerId);
		
		JButton btnKisiBilgi = new JButton("Bilgiler");
		btnKisiBilgi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String owner_Tc;
				KisiBilgileri ksb;
				if(cmbOwnerId.getSelectedItem() != null) {
					owner_Tc = cmbOwnerId.getSelectedItem().toString();
					ksb = new KisiBilgileri(owner_Tc);
					ksb.ksbframe.setVisible(true);
				}
			}
		});
		btnKisiBilgi.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnKisiBilgi.setBounds(451, 150, 80, 19);
		panel.add(btnKisiBilgi);
		
		txtDest = new JTextField();
		txtDest.setColumns(10);
		txtDest.setBounds(451, 58, 170, 19);
		panel.add(txtDest);
		
		tnkframe.getContentPane().setLayout(groupLayout);
		
	
	}
}
