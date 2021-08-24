package Image_Processing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class image extends JFrame {
	
	private JFrame frame;
	private JLabel imageLabel;
	
	public static String loadImage() {
		
		String filepath;
		JFileChooser chooser = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "jpg", "gif", "jpeg", "png");
		chooser.setFileFilter(filter);
		
		int chosen = chooser.showOpenDialog(null);
		if(chosen != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
			filepath = "";
		}
		
		filepath = chooser.getSelectedFile().getPath();
		
		return filepath;
		
	}
	

}