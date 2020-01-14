package gui;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import database.Database;

public class ContainerFrame{
	Database db;
	
	public static boolean isLoggedIn = false;
	
	public ContainerFrame(){
		
		db = Database.getInstance();
		JFrame mf = new JFrame();
		mf.setSize(new Dimension(1366,768));	// minimum allowed screen size
		try {
			//set icon for the frame
//			System.out.println(getClass().getClassLoader().getResource("PAANCI").toString());
			System.out.println(new File(System.getProperty("user.dir") + "/AOOP_Project/res/book.png").getAbsolutePath());
			mf.setIconImage(ImageIO.read(new File(System.getProperty("user.dir") + "/res/book.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mf.setTitle("Simple Dictionary For Kids");
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mf.setLocationRelativeTo(null);
		mf.add(new ContentPanel(mf));
		mf.setVisible(true);
	}
}
