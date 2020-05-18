import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;


public class JImageDisplay extends JComponent {
	private BufferedImage image;
	private Graphics graphic;

	
	public JImageDisplay(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		super.setPreferredSize(new Dimension(width, height));
		graphic = image.getGraphics();
		paintComponent(graphic);
		this.repaint();
	}


	public void clearImage() {
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				image.setRGB(i, j, 0);
			}
		}
		this.repaint();
	}


	public void drawPixel(int x, int y, Color rgbColor) {
		int rgb = rgbColor.getRGB(); 
		image.setRGB(x, y, rgb);
		this.repaint();
	}


	@Override
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		graphic.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		this.repaint();
	}
}