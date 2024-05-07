package liman;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Button;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_1 = new JPanel();
		//panel_1.setBackground(new Color(153,180,209));
		panel_1.setForeground(Color.WHITE);
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Tekne Ýþlemleri");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TekneIslemleri tnk = new TekneIslemleri();
				tnk.tnkframe.setVisible(true);
			}
		});
		btnNewButton_1.setBackground(new Color(153, 204, 204));
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\fecev\\OneDrive\\Masa\u00FCst\u00FC\\Ders\\D\u00F6nem-3.1\\Veri Taban\u0131 Y\u00F6netimi\\Proje\\redim\\cargo-ship.png"));
		btnNewButton_1.setActionCommand("Tekne \u0130\u015Flemleri");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1.setBounds(132, 126, 231, 89);
		panel_1.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Liman Ba\u015Fkanl\u0131\u011F\u0131 Bilgi Sistemi");
		lblNewLabel.setBackground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(165, 42, 42));
		lblNewLabel.setBounds(453, 24, 241, 73);
		panel_1.add(lblNewLabel);
		
		JButton btnNewButton_1_1 = new JButton("Liman Ýþlemleri");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LimanIslemleri lmn = new LimanIslemleri();
				lmn.lmnframe.setVisible(true);
			}
		});
		btnNewButton_1_1.setBackground(new Color(153, 204, 204));
		btnNewButton_1_1.setIcon(new ImageIcon("C:\\Users\\fecev\\OneDrive\\Masa\u00FCst\u00FC\\Ders\\D\u00F6nem-3.1\\Veri Taban\u0131 Y\u00F6netimi\\Proje\\redim\\cargo-ship.png"));
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1_1.setActionCommand("Tekne \u0130\u015Flemleri");
		btnNewButton_1_1.setBounds(132, 250, 231, 100);
		panel_1.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("Giriþ/Çýkýþ Kayýtlarý");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KayitIslemleri kyt = new KayitIslemleri();
				kyt.kytframe.setVisible(true);
			}
		});
		btnNewButton_1_2.setBackground(new Color(153, 204, 204));
		btnNewButton_1_2.setIcon(new ImageIcon("C:\\Users\\fecev\\OneDrive\\Masa\u00FCst\u00FC\\Ders\\D\u00F6nem-3.1\\Veri Taban\u0131 Y\u00F6netimi\\Proje\\redim\\cargo-ship.png"));
		btnNewButton_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1_2.setActionCommand("Tekne \u0130\u015Flemleri");
		btnNewButton_1_2.setBounds(132, 375, 231, 100);
		panel_1.add(btnNewButton_1_2);
		
		JButton btnNewButton_1_3 = new JButton("Tekne Sahipleri");
		btnNewButton_1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sahipler shp = new Sahipler();
				shp.shpframe.setVisible(true);
			}
		});
		btnNewButton_1_3.setBackground(new Color(153, 204, 204));
		btnNewButton_1_3.setIcon(new ImageIcon("C:\\Users\\fecev\\OneDrive\\Masa\u00FCst\u00FC\\Ders\\D\u00F6nem-3.1\\Veri Taban\u0131 Y\u00F6netimi\\Proje\\redim\\cargo-ship.png"));
		btnNewButton_1_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1_3.setActionCommand("Tekne \u0130\u015Flemleri");
		btnNewButton_1_3.setBounds(132, 500, 231, 100);
		panel_1.add(btnNewButton_1_3);
		
		ImagePanel panel = new ImagePanel(
		        new ImageIcon("C:\\Users\\fecev\\OneDrive\\Masaüstü\\Ders\\Dönem-3.1\\Veri Tabaný Yönetimi\\Proje\\redim\\harbor-crane.png").getImage());
		panel.setBounds(450, 150, 512, 512);
		panel_1.add(panel);
	}
	
	class ImagePanel extends JPanel {

		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image img;

		  public ImagePanel(String img) {
		    this(new ImageIcon(img).getImage());
		  }

		  public ImagePanel(Image img) {
		    this.img = img;
		    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		    setPreferredSize(size);
		    setMinimumSize(size);
		    setMaximumSize(size);
		    setSize(size);
		    setLayout(null);
		  }

		  public void paintComponent(Graphics g) {
		    g.drawImage(img, 0, 0, null);
		  }
}
}
