package liman;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class KisiBilgileri {

	JFrame ksbframe;
	private JTextField txtAdSoyad;
	private JTextField txtTelNo;
	private JTextField txtAdres;
	private JTextField txtMail;
	private JTextField txtHesapNo;
	private JTable table;
	private static String tc = null;
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tekne ID", "Tekne Ad/Plaka"
			}
		);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KisiBilgileri window = new KisiBilgileri(tc);
					window.ksbframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KisiBilgileri(String tc) {
		this.tc = tc;
		initialize();
		connect(tc);
	}
	
	
	public void ozel_tekne_listele(String tc) {
		String url ="jdbc:postgresql://localhost/Liman";
		String sql ="SELECT DISTINCT ot_id, tekne_ad\r\n"
				+ "FROM ozel_tekne,sahip,tekne "
				+ "WHERE ot_id=tekne_id AND tekne.owner_id = ? AND ot_id IN (SELECT tekne_id "
				+ "FROM tekne "
				+ "EXCEPT "
				+ "SELECT tekne_id "
				+ "FROM tekne, ticari_tekne "
				+ "WHERE tekne.tekne_id = tt_id)";
		System.out.println("TC="+tc);
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		
		try {
			Connection db=DriverManager.getConnection(url, prop);
			PreparedStatement stmt = db.prepareStatement(sql);
			stmt.setString(1, tc);
			ResultSet rset=stmt.executeQuery();
			//ResultSetMetaData rsmd = rset.getMetaData();
			
			while(rset.next()) {
				int ot_id = rset.getInt("ot_id");
				String tekne_ad = rset.getString("tekne_ad");
				Vector<Object> ozel_tekneV = new Vector<Object>();
				ozel_tekneV.add(ot_id);
				ozel_tekneV.add(tekne_ad);
				
				model.addRow(ozel_tekneV);
			}
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void ticari_tekne_listele(String tc) {
		String url ="jdbc:postgresql://localhost/Liman";
		String sql ="SELECT DISTINCT tt_id, plaka\r\n"
				+ "FROM ticari_tekne,sahip,tekne "
				+ "WHERE  tt_id=tekne_id AND tekne.owner_id = ? AND tt_id IN (SELECT tekne_id "
				+ "FROM tekne "
				+ "EXCEPT "
				+ "SELECT tekne_id "
				+ "FROM tekne, ozel_tekne "
				+ "WHERE tekne.tekne_id = ot_id)";
		System.out.println("TC="+tc);
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		
		try {
			Connection db=DriverManager.getConnection(url, prop);
			PreparedStatement stmt = db.prepareStatement(sql);
			stmt.setString(1, tc);
			ResultSet rset=stmt.executeQuery();
			ResultSetMetaData rsmd = rset.getMetaData();
			System.out.println(rsmd.getColumnCount());
			if(tc == null  || rsmd.getColumnCount() == 0) {
				JOptionPane.showMessageDialog(ksbframe, "Tabloda aranan veri bulunamadý.");
			}
			while(rset.next()) {
				int tt_id = rset.getInt("tt_id");
				String tekne_plaka = rset.getString("plaka");
				Vector<Object> ticari_tekneV = new Vector<Object>();
				ticari_tekneV.add(tt_id);
				ticari_tekneV.add(tekne_plaka);
				
				model.addRow(ticari_tekneV);
			}
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
public void connect(String tc) {
		
		String url ="jdbc:postgresql://localhost/Liman";
		String sql ="select * from sahip where owner_id=?";
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		
		try {
			Connection db=DriverManager.getConnection(url, prop);
			PreparedStatement stmt = db.prepareStatement(sql);
			stmt.setString(1, tc);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
	            txtAdSoyad.setText(rs.getString("ad_soyad"));
	            txtTelNo.setText(rs.getString("tel_no"));
	            txtAdres.setText(rs.getString("adres"));
	            txtMail.setText(rs.getString("mail"));
	            txtHesapNo.setText(rs.getString("hesap_no"));
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
		ksbframe = new JFrame();
		ksbframe.setBounds(100, 100, 877, 513);
		ksbframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ksbframe.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 10, 10);
		ksbframe.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblAdSoyad = new JLabel("Ad\u0131 Soyad\u0131:");
		lblAdSoyad.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblAdSoyad.setBounds(10, 20, 150, 19);
		ksbframe.getContentPane().add(lblAdSoyad);
		
		txtAdSoyad = new JTextField();
		txtAdSoyad.setColumns(10);
		txtAdSoyad.setBounds(205, 20, 200, 19);
		ksbframe.getContentPane().add(txtAdSoyad);
		
		txtTelNo = new JTextField();
		txtTelNo.setColumns(10);
		txtTelNo.setBounds(205, 60, 200, 19);
		ksbframe.getContentPane().add(txtTelNo);
		
		JLabel lblTelefonNumaras = new JLabel("Telefon Numaras\u0131:");
		lblTelefonNumaras.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTelefonNumaras.setBounds(10, 60, 150, 19);
		ksbframe.getContentPane().add(lblTelefonNumaras);
		
		JLabel lblAdresi = new JLabel("Adresi:");
		lblAdresi.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblAdresi.setBounds(10, 100, 150, 19);
		ksbframe.getContentPane().add(lblAdresi);
		
		txtAdres = new JTextField();
		txtAdres.setColumns(10);
		txtAdres.setBounds(205, 100, 200, 100);
		ksbframe.getContentPane().add(txtAdres);
		
		JLabel lblTekneId_3_1 = new JLabel("Maili:");
		lblTekneId_3_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTekneId_3_1.setBounds(10, 240, 150, 19);
		ksbframe.getContentPane().add(lblTekneId_3_1);
		
		txtMail = new JTextField();
		txtMail.setColumns(10);
		txtMail.setBounds(205, 240, 200, 19);
		ksbframe.getContentPane().add(txtMail);
		
		JLabel lblTekneId_3_1_1 = new JLabel("Hesap Numaras\u0131:");
		lblTekneId_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTekneId_3_1_1.setBounds(10, 280, 150, 19);
		ksbframe.getContentPane().add(lblTekneId_3_1_1);
		
		txtHesapNo = new JTextField();
		txtHesapNo.setColumns(10);
		txtHesapNo.setBounds(205, 280, 200, 19);
		ksbframe.getContentPane().add(txtHesapNo);
		
		JButton btnListeleOzel = new JButton("\u00D6zel Tekne Listele");
		btnListeleOzel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				ozel_tekne_listele(tc);
			}
		});
		btnListeleOzel.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnListeleOzel.setBounds(22, 361, 160, 54);
		ksbframe.getContentPane().add(btnListeleOzel);
		
		JButton btnListeleTicari = new JButton("Ticari Tekne Listele");
		btnListeleTicari.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				ticari_tekne_listele(tc);
			}
		});
		btnListeleTicari.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnListeleTicari.setBounds(245, 361, 160, 54);
		ksbframe.getContentPane().add(btnListeleTicari);
		
		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		scrollPane_1.setBounds(467, 30, 386, 357);
		ksbframe.getContentPane().add(scrollPane_1);
		
		table = new JTable();
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		scrollPane_1.setViewportView(table);
		
		
	}
}
