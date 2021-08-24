package Image_Processing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class second extends JFrame {
	
	JFrame frame;
	JPanel original;
	JPanel edited;
	BufferedImage originalImage;
	Graphics g;
	
	static final int WIDTH = 1400;
	static final int HEIGHT = 700;
	
	/*
	 * 
	 */
	String imagePath;
	
	public static void main(String args[]) {
		
		new second();
		
	}

	
	public second() {
		
		frame = new JFrame();
		frame.setBounds(25, 50, WIDTH, HEIGHT);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		initialize();
		
	}
	
	private void initialize() {
		
		// Panels
		
		original = new JPanel();
		original.setSize(600, 600);
		original.setLocation(25, 25);
		original.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(original);
		
		edited = new JPanel();
		edited.setSize(600, 600);
		edited.setLocation(650, 25);
		edited.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(edited);
		
		// Menu Bar
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		menuBar.setBounds(0, 0, WIDTH, 30);
		
		JMenu file = new JMenu("파일");
		menuBar.add(file);
		
		JMenu edit = new JMenu("편집");
		menuBar.add(edit);
		
		JMenuItem load = new JMenuItem("불러오기");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagePath = image.loadImage();
				if(imagePath == null) {
					JOptionPane.showMessageDialog(null, "");
				}
				File input = new File(imagePath);
				try {
					originalImage = ImageIO.read(input);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Image img = originalImage.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
				g = original.getGraphics();
				g.drawImage(img, 0, 0, null);
			}
		});
		file.add(load);
		
		JMenuItem save = new JMenuItem("저장하기");
		file.add(save);
		
		// Buttons
		
		JButton blackNwhite = new JButton("흑백");
		
		frame.setVisible(true);
		
		
	}
	
}