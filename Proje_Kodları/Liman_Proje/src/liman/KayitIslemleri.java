package liman;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KayitIslemleri {

	public JFrame kytframe;
	private JTextField txtKayitId;
	private JTextField txtGemiId;
	private JTextField txtKalkisYeri;
	private JTextField txtVarisYeri;
	private JTextField txtKalkisTarih;
	private JTextField txtVarisTarih;
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"Kayýt ID", "Gemi ID","Kalkýþ Yeri", "Varýþ Yeri", "Kalkýþ Tarihi", "Varýþ Tarihi"
			}
		);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KayitIslemleri window = new KayitIslemleri();
					window.kytframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KayitIslemleri() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
public void connect() {
		
		String url ="jdbc:postgresql://localhost/Liman";
		String sql ="select * from kayit";
		Properties prop = new Properties() ;
		prop.setProperty("user", "postgres");
		prop.setProperty("password", "12345");
		
		try {
			Connection db=DriverManager.getConnection(url, prop);
			Statement stmt = db.createStatement();
			ResultSet rset=stmt.executeQuery(sql);	
			while(rset.next()) {
				KayitData datum = new KayitData(rset.getInt("kyt_id"),rset.getInt("gemi_id"),rset.getString("klks_yer"),
						rset.getString("vrs_yer"),rset.getDate("klks_trh"),rset.getDate("vrs_trh"));  
				model.addRow(datum.data4);
			}
			db.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	private void initialize() {
		kytframe = new JFrame();
		kytframe.setBounds(100, 100, 955, 529);
		kytframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		kytframe.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 931, 482);
		kytframe.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblKaytId = new JLabel("Kay\u0131t ID:");
		lblKaytId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblKaytId.setBounds(10, 35, 150, 19);
		panel.add(lblKaytId);
		
		txtKayitId = new JTextField();
		txtKayitId.setColumns(10);
		txtKayitId.setBounds(205, 35, 200, 19);
		panel.add(txtKayitId);
		
		JLabel lblGemiId = new JLabel("Gemi ID:");
		lblGemiId.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblGemiId.setBounds(10, 75, 150, 19);
		panel.add(lblGemiId);
		
		txtGemiId = new JTextField();
		txtGemiId.setColumns(10);
		txtGemiId.setBounds(205, 75, 200, 19);
		panel.add(txtGemiId);
		
		JLabel lblKalkYeri = new JLabel("Kalk\u0131\u015F Yeri:");
		lblKalkYeri.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblKalkYeri.setBounds(10, 115, 150, 19);
		panel.add(lblKalkYeri);
		
		txtKalkisYeri = new JTextField();
		txtKalkisYeri.setColumns(10);
		txtKalkisYeri.setBounds(205, 115, 200, 19);
		panel.add(txtKalkisYeri);
		
		JLabel lblVarYeri = new JLabel("Var\u0131\u015F Yeri:");
		lblVarYeri.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVarYeri.setBounds(10, 155, 150, 19);
		panel.add(lblVarYeri);
		
		txtVarisYeri = new JTextField();
		txtVarisYeri.setColumns(10);
		txtVarisYeri.setBounds(205, 155, 200, 19);
		panel.add(txtVarisYeri);
		
		JLabel lblTekneId_3_1 = new JLabel("Kalk\u0131\u015F Tarihi:");
		lblTekneId_3_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTekneId_3_1.setBounds(10, 195, 150, 19);
		panel.add(lblTekneId_3_1);
		
		txtKalkisTarih = new JTextField();
		txtKalkisTarih.setColumns(10);
		txtKalkisTarih.setBounds(205, 195, 200, 19);
		panel.add(txtKalkisTarih);
		
		JLabel lblTekneId_3_1_1 = new JLabel("Var\u0131\u015F Tarihi:");
		lblTekneId_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblTekneId_3_1_1.setBounds(10, 235, 150, 19);
		panel.add(lblTekneId_3_1_1);
		
		txtVarisTarih = new JTextField();
		txtVarisTarih.setColumns(10);
		txtVarisTarih.setBounds(205, 235, 200, 19);
		panel.add(txtVarisTarih);
		
		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		scrollPane_1.setBounds(421, 28, 500, 357);
		panel.add(scrollPane_1);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();			
				txtKayitId.setText(model.getValueAt(index, 0).toString());
				txtGemiId.setText(model.getValueAt(index, 1).toString());
				txtKalkisYeri.setText(model.getValueAt(index, 2).toString());			
				txtVarisYeri.setText(model.getValueAt(index, 3).toString());
				txtKalkisTarih.setText(model.getValueAt(index, 4).toString());
				txtVarisTarih.setText(model.getValueAt(index, 5).toString());
			}
		});
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		scrollPane_1.setViewportView(table);
		
		JButton btnListele = new JButton("L\u0130STELE");
		btnListele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		btnListele.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnListele.setBounds(205, 300, 206, 54);
		panel.add(btnListele);
	}
}
