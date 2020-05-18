import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class FractalExplorer {
	
	private int width;
	private int height;
	private JImageDisplay display;
	private Rectangle2D.Double range;
	private JFrame frame;
	private JButton resetButton;
	private Mandelbrot mandelbrot;
	private class reset implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			mandelbrot.getInitialRange(range);
			FractalExplorer.this.drawFractal();
		}
	}
	
	private class mouse implements MouseListener {
		public void mouseClicked(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, display.getWidth(), x);
			double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, display.getHeight(), y);
			if (event.getButton() == MouseEvent.BUTTON1) {
				mandelbrot.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
			}
			if (event.getButton() == MouseEvent.BUTTON3) {
				mandelbrot.recenterAndZoomRange(range, xCoord, yCoord, 1.5);
			}
			FractalExplorer.this.drawFractal();	
		}
		

		public void mouseEntered(MouseEvent e) {
			//
		}
 
        public void mouseExited(MouseEvent e) {
        	return;
        }
 
        public void mousePressed(MouseEvent e) {

        }
 
        public void mouseReleased(MouseEvent e) {}
	}
	
	
	public FractalExplorer(int width, int height) {
		this.width = width;
		this.height = height;
		this.range = new Rectangle2D.Double();
		this.mandelbrot = new Mandelbrot();
		mandelbrot.getInitialRange(range);	
	}
	

	public void createAndShowGUI() {
		this.frame = new JFrame("Исследуем фракталы");
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.setSize(this.width, this.height);
		this.frame.setResizable(false); 
		this.resetButton = new JButton("Исходное изображение");
		frame.getContentPane().add(BorderLayout.SOUTH, this.resetButton);
		resetButton.addActionListener(new reset());
		this.display = new JImageDisplay(this.frame.getWidth(), this.frame.getHeight());
		frame.getContentPane().add(BorderLayout.CENTER, this.display);
		display.addMouseListener(new mouse());
		frame.setVisible(true);
	}


	public void drawFractal() {
		for (int xCoord = 0; xCoord < this.width; xCoord++) {
			for (int yCoord = 0; yCoord < this.height; yCoord++) {
				int rgbColor = Color.HSBtoRGB(0, 0 ,0);
				double x = FractalGenerator.getCoord(range.x, range.x + range.width, display.getWidth(), xCoord);
				double y = FractalGenerator.getCoord(range.y, range.y + range.height, display.getHeight(), yCoord);
				int iterations = mandelbrot.numIterations(x, y);
				if (iterations != -1) {
					float hue = 0.7f + (float) iterations / 200f; 
					rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
				} 
				display.drawPixel(xCoord, yCoord, new Color(rgbColor));
			}
		}
	}
	

	public static void main(String[] args) {
		int size = 800;
		FractalExplorer explorer = new FractalExplorer(size, size);
		explorer.createAndShowGUI();
		explorer.drawFractal();
	}
}