package Image_Processing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main extends JFrame implements ActionListener, MouseMotionListener{
	JPanel originalImage = new JPanel();
	JPanel editedImage = new JPanel();
	JPanel Menu = new JPanel();
	
	JButton openFile = new JButton("파일 불러오기");
	JButton saveFile = new JButton("저장하기");
	JButton GrayScale = new JButton("흑백으로 변환");
	JButton edge = new JButton("Edge 추출");
	JButton manipulation = new JButton("합성하기");
	JButton magnifier = new JButton("돋보기");
	JButton contrast = new JButton("밝기 조절");
	
	Image imageResize;
	BufferedImage image = null;

	
	JFileChooser choose = new JFileChooser();

	JLabel magni = new JLabel();
	
	JSlider a = new JSlider();
	
	String mode;
	
	boolean isContrast = false;
	
	double[][] filter = { { 1, 1, 1 }, { 1, -8, 1 }, { 1, 1, 1 } };
	double[][] filterBlur = { { 0.088, 0.107, 0.088 }, { 0.107,0.214, 0.107 }, { 0.088, 0.107 , 0.088 } };

	
	
	public static void main(String[] args) {
		Main main = new Main();
	}
	
	
	Main(){
		setSize(1200,800);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		Menu.setBounds(1000,0,150,800);
		Menu.setLayout(null);
		
		originalImage.setBounds(50,200,425,550);
		originalImage.setBackground(Color.white);
		
		editedImage.setBounds(525,200,425,550);
		editedImage.addMouseMotionListener(this);
		editedImage.setBackground(Color.white);
		
		openFile.setBounds(10,30,140,90);
		openFile.addActionListener(this);
		
		saveFile.setBounds(10,130,140,90);
		saveFile.addActionListener(this);;
		
		GrayScale.setBounds(10,230,140,90);
		GrayScale.addActionListener(this);
		
		edge.setBounds(10,330,140,90);
		edge.addActionListener(this);
		
		manipulation.setBounds(10,430,140,90);
		
		magnifier.setBounds(10,530,140,90);
		magnifier.addActionListener(this);
		
		contrast.setBounds(10,630,140,90);
		contrast.addActionListener(this);
		
		magni.setBounds(10,10,150,150);
		magni.setBorder(BorderFactory.createLineBorder(Color.black));
		
		a.setBounds(200,0,35,35);
		
		Menu.add(openFile);
		Menu.add(saveFile);
		Menu.add(GrayScale);
		Menu.add(edge);
		Menu.add(manipulation);
		Menu.add(magnifier);
		Menu.add(contrast);

		add(magni);
		add(originalImage);
		add(editedImage);
		add(Menu);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==openFile) {
			choose.setCurrentDirectory(new File("C:\\Users\\GP62MVR\\Desktop"));
			mode = "open";
			if(choose.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
				System.out.println(choose.getSelectedFile().toString());
				File input = new File(choose.getSelectedFile().toString());
				try {
					image = ImageIO.read(input);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				imageResize = image.getScaledInstance(425, 550, Image.SCALE_SMOOTH);
				Graphics g = originalImage.getGraphics();
				g.drawImage(imageResize, 0, 0, null);
				g = editedImage.getGraphics();
				g.drawImage(imageResize, 0, 0, null);
			}
		}else if(e.getSource()==saveFile) {
			mode = "save";
			
			BufferedImage temp = new BufferedImage(425,550,BufferedImage.TYPE_INT_RGB);
			Graphics bg = temp.getGraphics();
		    bg.drawImage(imageResize, 0, 0, null);
		    bg.dispose();
		    
			 FileDialog fsaveDlg = new FileDialog(this, "File Save", FileDialog.SAVE);
	         fsaveDlg.setVisible(true);
	            
	         String dir = fsaveDlg.getDirectory();
	         String file = fsaveDlg.getFile();
	            
	         if(dir == null || file == null) {
	               return;
	         }

	         try{

		            ImageIO.write(temp,"png", new File(dir,file.concat(".png")));

		            JOptionPane.showMessageDialog(this, "파일저장 성공");

		        }catch(Exception ex){

		             ex.printStackTrace();

		        }
	         
	         
		}else if(e.getSource()==GrayScale) {
			mode = "gray";
			for(int y = 0; y < image.getHeight(); y++) {
				   for(int x = 0; x < image.getWidth(); x++) {
				       Color colour = new Color(image.getRGB(x, y));
//				       Choose one from below
//				       int Y = (int) (0.299 * colour.getRed() + 0.587 * colour.getGreen() + 0.114 * colour.getBlue());
				       int Y = (int) (0.2126 * colour.getRed() + 0.7152 * colour.getGreen() + 0.0722 * colour.getBlue());
				       image.setRGB(x, y, new Color(Y, Y, Y).getRGB());
				   }
			}
			imageResize = image.getScaledInstance(425, 550, Image.SCALE_SMOOTH);
			Graphics g = editedImage.getGraphics();
			g.drawImage(imageResize, 0, 0, null);
		}else if(e.getSource()==magnifier) {
			mode = "magni";

		}else if(e.getSource()==contrast) {
			if(!isContrast) {
			isContrast = true;
			mode = "contrast";
			JFrame slide = new JFrame();
			JSlider slider = new JSlider(-100,100);
			
			slide.setBounds(850,670,300,100);
			slide.setVisible(true);
			
			slider.setMajorTickSpacing(10);
			slider.setMinorTickSpacing(5);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true); 
			slider.addChangeListener(new ChangeListener() {
			      public void stateChanged(ChangeEvent evt) {
			    	  BufferedImage modifiedImage = deepCopy(image);
			    	  JSlider a = (JSlider)evt.getSource();
			    	  for(int y = 0; y < modifiedImage.getHeight(); y++) {
						   for(int x = 0; x < modifiedImage.getWidth(); x++) {
						       Color colour = new Color(modifiedImage.getRGB(x, y));
//						       Choose one from below
//						       int Y = (int) (0.299 * colour.getRed() + 0.587 * colour.getGreen() + 0.114 * colour.getBlue());
						       int R = (int) (a.getValue()+colour.getRed());
						       int G = (int) (a.getValue()+colour.getGreen());
						       int B = (int) (a.getValue()+colour.getBlue());
						       if(R<0) R = 0;
						       if(R>255) R = 255;
						       if(G<0) G = 0;
						       if(G>255) G = 255;
						       if(B<0) B = 0;
						       if(B>255) B = 255;
						       modifiedImage.setRGB(x, y, new Color(R, G, B).getRGB());
						   }
					}
					imageResize = modifiedImage.getScaledInstance(425, 550, Image.SCALE_SMOOTH);
					Graphics g = editedImage.getGraphics();
					g.drawImage(imageResize, 0, 0, null);
			      }
			    });
			
			slide.add(slider);
			}else {
				isContrast = false;
			}
			
			
		
		}else if(e.getSource()==edge) {
			BufferedImage temp = new BufferedImage(425,550,BufferedImage.TYPE_INT_RGB);
			Graphics bg = temp.getGraphics();
		    bg.drawImage(imageResize, 0, 0, null);
		    bg.dispose();
			double[][] arr = im2ar(temp);
			arr = convolution(arr,filterBlur);
			double[][] con = convolution(arr,filter);
			con = arrayInColorBound(con);
			con = arrayColorInverse(con);
			BufferedImage output = ar2im(con);
			imageResize = output.getScaledInstance(425, 550, Image.SCALE_SMOOTH);
			Graphics g = editedImage.getGraphics();
			g.drawImage(imageResize, 0,0, null);
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(mode=="magni") {
			BufferedImage temp = new BufferedImage(425,550,BufferedImage.TYPE_INT_RGB);
			Graphics bg = temp.getGraphics();
		    bg.drawImage(imageResize, 0, 0, null);
		    bg.dispose();
		    int x = e.getX();
		    int y = e.getY();
		    if(x<25) x = 25;
		    else if(x>400) x = 400;
		    if(y<25) y = 25;
		    else if(y>525) x = 525;
		    
		    
			try{
				magni.setIcon(new ImageIcon(temp.getSubimage(x-25,y-25,50,50).getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
			}catch(Exception a) {
				
			}
			
			
		}
	}
	
	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	
	public static double[][] convolution(double[][] map, double[][] filter) {
		int c = 0;
		if (filter.length % 2 == 1) {
		int w = filter.length / 2;
		double[][] output = new double[map.length][map[0].length];
		for (int y = 0; y < map.length; y++) {
		for (int x = 0; x < map[y].length; x++) {
		for (int i = 0; i < filter.length; i++) {
		for (int j = 0; j < filter[i].length; j++) {
		try {
		output[y][x] += map[y - i + w][x - j + w] * filter[i][j];
		c++;
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		}
		}
		}
		}
		return output;
		} else {
		//필터 크기가 짝수인 경우 무시
		return null;
		}
		}
	
	public static double[][] im2ar(BufferedImage bi) {
		double[][] output = new double[bi.getHeight()][bi.getWidth()];
		for (int y = 0; y < bi.getHeight(); y++) {
		for (int x = 0; x < bi.getWidth(); x++) {
		Color c = new Color(bi.getRGB(x, y));
		output[y][x] += c.getRed();
		output[y][x] += c.getGreen();
		output[y][x] += c.getBlue();
		output[y][x] /= 3.0;
		}
		}
		return output;
		}
	
	public static BufferedImage ar2im(double[][] ar) {
		BufferedImage output = new BufferedImage(ar[0].length, ar.length, BufferedImage.TYPE_INT_BGR);
		for (int y = 0; y < ar.length; y++) {
		for (int x = 0; x < ar[y].length; x++) {
		output.setRGB(x, y, new Color((int) ar[y][x], (int) ar[y][x], (int) ar[y][x]).getRGB());
		}
		}
		return output;
		}
	
	public static double[][] arrayInColorBound(double[][] ar) {
		for (int i = 0; i < ar.length; i++) {
		for (int j = 0; j < ar[i].length; j++) {
		ar[i][j] = Math.max(0, ar[i][j]);
		ar[i][j] = Math.min(225, ar[i][j]);
		}
		}
		return ar;
		}

		public static double[][] arrayColorInverse(double[][] ar) {
		for (int i = 0; i < ar.length; i++) {
		for (int j = 0; j < ar[i].length; j++) {
		ar[i][j] = 255 - ar[i][j];
		}
		}
		return ar;
		}
	}



