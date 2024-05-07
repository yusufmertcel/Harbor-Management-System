package liman;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.Panel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.Text;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LimanIslemleri {

	public JFrame lmnframe;
	private JTextField txtLimanId;
	private JTextField txtLimanAd;
	private JTextField txtLimanAdres;
	private JTextField txtSehir;
	private JTextField txtGemiSayi;
	private JTextField txtCalisanNo;
	private JTable table;
	private JButton btnListele;
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"Liman ID", "Liman Adý", "Adres", "Þehir", "Çalýþan Sayýsý", "Gemi Sayýsý"
			}
		);
	private JTextField textFiltre;
	private JTextField textAvg;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LimanIslemleri window = new LimanIslemleri();
					window.lmnframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LimanIslemleri() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void connect() {
		String url ="jdbc:postgresql://localhost/Liman";
		String sql ="select * from liman;";
		//String sqlfunc = "SELECT ort_lemp_num();";
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		
		try {
			Connection db=DriverManager.getConnection(url, prop);
			Statement stmt = db.createStatement();
			ResultSet rset=stmt.executeQuery(sql);
			//ResultSetMetaData rsmd = rset.getMetaData();
			
			while(rset.next()) {
				LimanData datum = new LimanData(rset.getInt("liman_id"),rset.getString("liman_Ad"),rset.getString("adres"),rset.getString("sehir"),
						rset.getInt("lemp_num"),rset.getInt("ship_num")); 
				model.addRow(datum.data2);
			}
			CallableStatement proc = db.prepareCall("{ ? = call ort_lemp_num() }");
			proc.registerOutParameter(1, Types.DOUBLE);
			proc.execute();
			
			textAvg.setText(String.valueOf(proc.getDouble(1)));
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(double limit_kutle) {
		String url ="jdbc:postgresql://localhost/Liman";
		String sql ="SELECT * FROM filtre_liman_view(?);";
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		
		try {
			Connection db=DriverManager.getConnection(url, prop);
			PreparedStatement stmt = db.prepareStatement(sql);
			stmt.setDouble(1, limit_kutle);
			ResultSet rset= stmt.executeQuery();
			
			while(rset.next()) {
				LimanData datum = new LimanData(rset.getInt("lid"),rset.getString("limanadi"),rset.getString("adres"),rset.getString("sehir"),
						rset.getInt("emp_num"),rset.getInt("ship_num")); 
				model.addRow(datum.data2);
			}
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		lmnframe = new JFrame();
		lmnframe.setBounds(100, 100, 1273, 668);
		lmnframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		lmnframe.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1146, 471);
		lmnframe.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblLianId = new JLabel("Liman ID:");
		lblLianId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblLianId.setBounds(10, 33, 150, 19);
		panel.add(lblLianId);
		
		txtLimanId = new JTextField();
		txtLimanId.setColumns(10);
		txtLimanId.setBounds(205, 33, 200, 19);
		panel.add(txtLimanId);
		
		JLabel lblLimanAd = new JLabel("Liman Ad\u0131:");
		lblLimanAd.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblLimanAd.setBounds(10, 73, 150, 19);
		panel.add(lblLimanAd);
		
		txtLimanAd = new JTextField();
		txtLimanAd.setColumns(10);
		txtLimanAd.setBounds(205, 73, 200, 19);
		panel.add(txtLimanAd);
		
		JLabel lblLimanAdresi = new JLabel("Liman Adresi:");
		lblLimanAdresi.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblLimanAdresi.setBounds(10, 113, 150, 19);
		panel.add(lblLimanAdresi);
		
		txtLimanAdres = new JTextField();
		txtLimanAdres.setColumns(10);
		txtLimanAdres.setBounds(205, 113, 200, 100);
		panel.add(txtLimanAdres);
		
		JLabel lblehir = new JLabel("\u015Eehir:");
		lblehir.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblehir.setBounds(10, 243, 150, 19);
		panel.add(lblehir);
		
		txtSehir = new JTextField();
		txtSehir.setColumns(10);
		txtSehir.setBounds(205, 243, 200, 19);
		panel.add(txtSehir);
		
		JLabel lblGemiSaysId = new JLabel("Gemi Say\u0131s\u0131:");
		lblGemiSaysId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblGemiSaysId.setBounds(10, 283, 150, 19);
		panel.add(lblGemiSaysId);
		
		txtGemiSayi = new JTextField();
		txtGemiSayi.setColumns(10);
		txtGemiSayi.setBounds(205, 283, 200, 19);
		panel.add(txtGemiSayi);
		
		JLabel lblalan = new JLabel("\u00C7al\u0131\u015Fan Say\u0131s\u0131:");
		lblalan.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblalan.setBounds(10, 323, 150, 19);
		panel.add(lblalan);
		
		txtCalisanNo = new JTextField();
		txtCalisanNo.setColumns(10);
		txtCalisanNo.setBounds(205, 323, 200, 19);
		panel.add(txtCalisanNo);
		
		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		scrollPane_1.setBounds(468, 33, 668, 309);
		panel.add(scrollPane_1);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();			
				txtLimanId.setText(model.getValueAt(index, 0).toString());
				txtLimanAd.setText(model.getValueAt(index, 1).toString());
				txtLimanAdres.setText(model.getValueAt(index, 2).toString());
				txtSehir.setText(model.getValueAt(index, 3).toString());			
				txtGemiSayi.setText(model.getValueAt(index, 4).toString());
				txtCalisanNo.setText(model.getValueAt(index, 5).toString());
			}
		});
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		scrollPane_1.setViewportView(table);
		
		btnListele = new JButton("L\u0130STELE");
		btnListele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
				
			}
		});
		btnListele.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnListele.setBounds(10, 384, 166, 54);
		panel.add(btnListele);
		
		textFiltre = new JTextField();
		textFiltre.setBounds(923, 4, 96, 19);
		panel.add(textFiltre);
		textFiltre.setColumns(10);
		
		JButton btnNewButton = new JButton("Filtre");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				if(textFiltre.getText() != null) {
					connect(Double.parseDouble(textFiltre.getText()));
				}
					
			}
		});
		btnNewButton.setBounds(1029, 3, 85, 21);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Ortalama \u0130\u015F\u00E7i Say\u0131s\u0131:");
		lblNewLabel.setBounds(880, 359, 139, 13);
		panel.add(lblNewLabel);
		
		textAvg = new JTextField();
		textAvg.setBounds(1006, 356, 130, 19);
		panel.add(textAvg);
		textAvg.setColumns(10);
	}
}
