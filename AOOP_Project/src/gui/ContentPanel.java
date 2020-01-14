package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import database.Database;

public class ContentPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JTextField searchField;
	private JButton searchButton;
	private JTextArea resultArea;
	private JTextArea recentSearchArea;
	
	public ContentPanel(JFrame frame) {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		while(ContainerFrame.isLoggedIn == false) {
			LogIn(frame);
		}
		addComponent();
	}
	
	public void LogIn(JFrame frame) {
		
		JDialog credentials = new JDialog();
		JTextField username = new JTextField(10);
		JPasswordField password = new JPasswordField(10);
		JButton login = new JButton("Login");
		JLabel user = new JLabel("username: ");
		JLabel pass = new JLabel("password: ");
		JLabel regs = new JLabel("register");
		credentials.setTitle("Login");
		credentials.setLayout(new GridLayout(3, 2));
		credentials.setSize(new Dimension(300,300));
		credentials.setLocationRelativeTo(frame);
		credentials.setModal(true);
		user.setLabelFor(username);
		pass.setLabelFor(password);
		credentials.add(user);
		credentials.add(username);
		credentials.add(pass);
		credentials.add(password);
		credentials.add(login);
		credentials.add(regs);
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Database db = Database.getInstance();
				String u = username.getText().toString();
				String p = String.valueOf(password.getPassword());
				ResultSet rs = db.getSingleUserCredential(u, p);
				try {
					if(rs.next()) {
						JOptionPane.showMessageDialog(frame, "Hello, " + u);
						ContainerFrame.isLoggedIn = !ContainerFrame.isLoggedIn;
						credentials.dispose();
					}
					else {
						JOptionPane.showMessageDialog(frame, "Wrong Credentials");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		regs.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				credentials.dispose();
				Register(frame);
			}
		});
		
		try {
			credentials.setIconImage(ImageIO.read(getClass().getResource("/book.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		credentials.pack();
		credentials.setVisible(true);
	}
	
	public void Register(JFrame frame) {
		JDialog register = new JDialog();
		JLabel user = new JLabel("username: ");
		JLabel pass = new JLabel("password: ");
		JLabel login = new JLabel("login");
		JTextField username = new JTextField(10);
		JTextField password = new JTextField(10);
		JButton regis = new JButton("Register");
		regis.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String u = username.getText().toString();
				String p = password.getText().toString();
				Database db = Database.getInstance();
				db.insertUserTable(u, p);
				LogIn(frame);
				JOptionPane.showMessageDialog(frame, "Successfully Registered!!");
				register.dispose();
			}
		});
		login.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				register.dispose();
				LogIn(frame);
			}
		});
		register.setTitle("Register");
		register.setSize(new Dimension(300,300));
		register.setLayout(new GridLayout(3, 2));
		register.setLocationRelativeTo(frame);
		register.setModal(true);
		register.add(user);
		register.add(username);
		register.add(pass);
		register.add(password);
		register.add(regis);
		register.add(login);
		try {
			register.setIconImage(ImageIO.read(getClass().getResource("/book.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		register.pack();
		register.setVisible(true);
	}
	
	public void addComponent() {
		JPanel topContainer = new JPanel();
		JPanel botContainer = new JPanel();
		topContainer.setPreferredSize(new Dimension(1366,150));
		topContainer.setBackground(Color.BLACK);
		botContainer.setPreferredSize(new Dimension(1366,550));
		botContainer.setBackground(Color.GRAY);
		botContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.add(topContainer, BorderLayout.NORTH);
		this.add(botContainer, BorderLayout.SOUTH);
		
		addSearchComponent(topContainer);
		addBottomComponent(botContainer);
	}
	
	public void addSearchComponent(JPanel upperPane) {
		upperPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel ppad = new JPanel();
		ppad.setPreferredSize(new Dimension(1000,150));
		ppad.setBackground(Color.BLACK);
		
		searchButton = new JButton("Search");
		searchButton.setPreferredSize(new Dimension(150,100));
		searchButton.setFont(new Font("Courier", Font.BOLD, 25));
		
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Database db = Database.getInstance();
				String word = searchField.getText().toString();
				ResultSet rs = db.getSingleContent(word);
				try {
					if(rs.next()) {
						
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(750,125));
		searchField.setFont(new Font("Courier", Font.PLAIN, 25));
		ppad.add(searchField);
		ppad.add(searchButton);
		
		upperPane.add(ppad);
	}
	
	public void addBottomComponent(JPanel lowerPane) {
		addRecentSearchComponent(lowerPane);
		addResultComponent(lowerPane);
		addMiscComponent(lowerPane);
	}
	
	public void addResultComponent(JPanel lowerPane)  {
		JPanel resultPanel = new JPanel();
		resultPanel.setPreferredSize(new Dimension(766,525));
		resultPanel.setBackground(Color.YELLOW);
		
		resultArea = new JTextArea(10, 10);
		resultArea.setEditable(false);
		
		lowerPane.add(resultPanel);
	}
	
	public void addRecentSearchComponent(JPanel lowerPane) {
		JPanel recentSearch = new JPanel();
		recentSearch.setPreferredSize(new Dimension(250, 525));
		recentSearch.setBackground(Color.RED);
		lowerPane.add(recentSearch);
	}
	
	public void addMiscComponent(JPanel lowerPane) {
		JPanel miscComp = new JPanel();
		miscComp.setPreferredSize(new Dimension(250, 525));
		miscComp.setBackground(Color.CYAN);
		lowerPane.add(miscComp);
	}
}