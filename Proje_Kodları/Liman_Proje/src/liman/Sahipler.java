package liman;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sahipler {

	public JFrame shpframe;
	private JTable table;
	private JTextField txtID;
	private JTextField txtAdSoyad;
	private JTextField txtTelNo;
	private JTextField txtAdres;
	private JTextField txtMail;
	private JTextField txtHesapNo;
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"Owner ID", "Ad Soyad", "Telefon No", "Adres", "Mail", "Hesap No"
			}
		);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sahipler window = new Sahipler();
					window.shpframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Sahipler() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void connect() {
		
		String url ="jdbc:postgresql://localhost/Liman";
		String sql ="select * from sahip";
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		
		try {
			Connection db=DriverManager.getConnection(url, prop);
			Statement stmt = db.createStatement();
			ResultSet rset=stmt.executeQuery(sql);	
			while(rset.next()) {
				SahipData datum = new SahipData(rset.getString("owner_id"),rset.getString("ad_soyad"),rset.getString("tel_no"),rset.getString("adres"),
						rset.getString("mail"),rset.getString("hesap_no"));  
				model.addRow(datum.data3);
			}
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}


	
	private void initialize() {
		shpframe = new JFrame();
		shpframe.setBounds(100, 100, 1025, 537);
		shpframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		shpframe.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1001, 490);
		shpframe.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		scrollPane_1.setBounds(491, 49, 500, 357);
		panel.add(scrollPane_1);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();			
				txtID.setText(model.getValueAt(index, 0).toString());
				txtAdSoyad.setText(model.getValueAt(index, 1).toString());
				txtTelNo.setText(model.getValueAt(index, 2).toString());
				txtAdres.setText(model.getValueAt(index, 3).toString());			
				txtMail.setText(model.getValueAt(index, 4).toString());
				txtHesapNo.setText(model.getValueAt(index, 5).toString());
			}
		});
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		scrollPane_1.setViewportView(table);
		
		JLabel lblKiiSahibiId = new JLabel("Ki\u015Fi Sahibi ID");
		lblKiiSahibiId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblKiiSahibiId.setBounds(10, 49, 150, 19);
		panel.add(lblKiiSahibiId);
		
		txtID = new JTextField();
		txtID.setColumns(10);
		txtID.setBounds(205, 49, 200, 19);
		panel.add(txtID);
		
		JLabel lblAdSoyad = new JLabel("Ad\u0131 Soyad\u0131:");
		lblAdSoyad.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblAdSoyad.setBounds(10, 89, 150, 19);
		panel.add(lblAdSoyad);
		
		JLabel lblTelefonNumaras = new JLabel("Telefon Numaras\u0131:");
		lblTelefonNumaras.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTelefonNumaras.setBounds(10, 129, 150, 19);
		panel.add(lblTelefonNumaras);
		
		txtAdSoyad = new JTextField();
		txtAdSoyad.setColumns(10);
		txtAdSoyad.setBounds(205, 89, 200, 19);
		panel.add(txtAdSoyad);
		
		txtTelNo = new JTextField();
		txtTelNo.setColumns(10);
		txtTelNo.setBounds(205, 129, 200, 19);
		panel.add(txtTelNo);
		
		JLabel lblAdresi = new JLabel("Adresi:");
		lblAdresi.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblAdresi.setBounds(10, 169, 150, 19);
		panel.add(lblAdresi);
		
		JLabel lblTekneId_3_1 = new JLabel("Maili:");
		lblTekneId_3_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTekneId_3_1.setBounds(10, 309, 150, 19);
		panel.add(lblTekneId_3_1);
		
		JLabel lblTekneId_3_1_1 = new JLabel("Hesap Numaras\u0131:");
		lblTekneId_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTekneId_3_1_1.setBounds(10, 349, 150, 19);
		panel.add(lblTekneId_3_1_1);
		
		txtAdres = new JTextField();
		txtAdres.setColumns(10);
		txtAdres.setBounds(205, 169, 200, 100);
		panel.add(txtAdres);
		
		txtMail = new JTextField();
		txtMail.setColumns(10);
		txtMail.setBounds(205, 309, 200, 19);
		panel.add(txtMail);
		
		txtHesapNo = new JTextField();
		txtHesapNo.setColumns(10);
		txtHesapNo.setBounds(205, 349, 200, 19);
		panel.add(txtHesapNo);
		
		JButton btnListele = new JButton("L\u0130STELE");
		btnListele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		btnListele.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnListele.setBounds(205, 403, 200, 54);
		panel.add(btnListele);
	}

}
