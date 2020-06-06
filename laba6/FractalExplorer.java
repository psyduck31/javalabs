import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class FractalExplorer {
	
	private int width;
	private int height;
	private JFrame frame;
	private JPanel northP;
	private JComboBox chooseF;
	private JLabel textF;
	private JImageDisplay display;
	private Rectangle2D.Double range;
	private JPanel southP;
	private JButton resetB;
	private JButton saveB;
	private ArrayList<FractalGenerator> fractals;
	private File nowPath = null;
	private int rowsRemaining = 0;
	private class reset implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			// Сброс границ фрактала и вызов функции отрисовки
			int index = chooseF.getSelectedIndex();
			if (index >= fractals.size()) {
				FractalExplorer.this.setStartImage();
				return;
			}
			
			fractals.get(index).getInitialRange(range);
			FractalExplorer.this.drawFractal(index);
		}
	}
	
	private class save implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			JFileChooser fchooser;
			
			if (nowPath == null) {
				fchooser = new JFileChooser();		
			} else {
				fchooser = new JFileChooser(nowPath);
			}

			fchooser.setDialogTitle("Выберите путь");
			fchooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Images", "*.png"));
			fchooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG Images", "*.jpeg"));
			fchooser.addChoosableFileFilter(new FileNameExtensionFilter("BMP Images", "*.bmp"));
			fchooser.setAcceptAllFileFilterUsed(false); 

			int result = fchooser.showSaveDialog(frame);	
			if (result == JFileChooser.APPROVE_OPTION) {
				System.out.println("Directory get");
			} else {
				System.out.println("Directory get ERROR");
				return;
			}
			
			String ext = "";
			String extension = fchooser.getFileFilter().getDescription();
			
			if (extension.equals("PNG Images")) ext = "png";
			if (extension.equals("JPEG Images")) ext = "jpeg";
			if (extension.equals("BMP Images")) ext = "bmp";
			//nowPath = fchooser.getSelectedFile();
			//nowPath = new File(nowPath.getPath() + nowPath.getName() + ".png");
			//System.out.println(nowPath.getAbsoluteFile());
			//System.out.println("getPath = " + fchooser.getSelectedFile().getPath());
			//System.out.println("getName = " + fchooser.getSelectedFile().getName());
			nowPath = new File(fchooser.getSelectedFile().getPath() + "." + ext);
			
			// Запись файла на диск
			try 
			{                               
				ImageIO.write(display.getImage(), ext, nowPath);
				JOptionPane.showMessageDialog(FractalExplorer.this.frame, "Изображение сохранено!", "Сохрание изображения.", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(FractalExplorer.this.frame, "Изображение не сохранено!", "Сохранение изображения.", JOptionPane.WARNING_MESSAGE);
			}
			
		}
	}
	
	private class mouse implements MouseListener {
		
		// Событие нажатия на кнопку мыщи
		public void mouseClicked(MouseEvent e) {
			
			int index = chooseF.getSelectedIndex();
			if (index >= fractals.size()) return;
			
			// Координаты клика мыши
			int x = e.getX();
			int y = e.getY();
			
			// Перевод координат в комплексную плоскость
			double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, display.getWidth(), x);
			double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, display.getHeight(), y);
			
			// Нажатие левой кнопкой мыши
			if (e.getButton() == MouseEvent.BUTTON1) {
				// Масштабирование
				fractals.get(index).recenterAndZoomRange(range, xCoord, yCoord, 0.5);
			}
			
			// Нажатие правой кнопкой мыши
			if (e.getButton() == MouseEvent.BUTTON3) {
				// Масштабирование
				fractals.get(index).recenterAndZoomRange(range, xCoord, yCoord, 1.5);
			}
			
			// Перерисовка фрактала
			FractalExplorer.this.drawFractal(index);	
		}
		
		/*
		* Need just to override them (error with russian words here)
		*/
		public void mouseEntered(MouseEvent e) {}
 
        public void mouseExited(MouseEvent e) {}
 
        public void mousePressed(MouseEvent e) {}
 
        public void mouseReleased(MouseEvent e) {}
	}
	
	private class comboBoxClickListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			int index = chooseF.getSelectedIndex();
			
			if (index >= fractals.size()) {
				FractalExplorer.this.setStartImage();
				return;
			}
			
			fractals.get(index).getInitialRange(range);
			
			FractalExplorer.this.drawFractal(index);
		}
	}
	
	/*
	* Конструкторы
	*/
	public FractalExplorer() {
		this(800);
	}
	
	public FractalExplorer(int size) {
		this(size, size);
	}
	
	public FractalExplorer(int width, int height) {
		this.width = width;
		this.height = height;
		
		// Создание объекта, содержащего диапазон
		this.range = new Rectangle2D.Double();
		
		// Создание объектов Фракталов
		fractals = new ArrayList<FractalGenerator>();
		fractals.add(new Mandelbrot());
		
		// ---Добавлять новые фракталы сюда---		
		fractals.add(new Tricorn());
		fractals.add(new BurningShip());
	}
	
	/*
	* Создание формы с компонентами
	*/
	public void createAndShowGUI() {
		// Создание формы
		this.frame = new JFrame("Исследуем фракталы");
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.setSize(this.width, this.height);
		this.frame.setResizable(false); 
		
		// Создание панелей
		northP = new JPanel();
		southP = new JPanel();
		
		// Добавление кнопки сброса масштабирования, и сохранения
		this.resetB = new JButton("Исходное изображение");
		this.resetB.setPreferredSize(new Dimension(frame.getWidth() / 3, 30));
		southP.add(this.resetB);
		
		this.saveB = new JButton("Сохранить изображение");
		this.saveB.setPreferredSize(new Dimension(frame.getWidth() / 3, 30));
		southP.add(this.saveB);
		
		// Создание и заполнение списка элементами
		this.chooseF = new JComboBox();
		for (int i = 0; i < fractals.size(); i++) {
			chooseF.addItem(fractals.get(i).toString());
		}
		
		// Доавление начального пустого элемента
		chooseF.addItem("----");
		
		// Установка флага на пустом элементе
		chooseF.setSelectedIndex(fractals.size());
		
		// Задание размера и добавление на панель
		this.chooseF.setPreferredSize(new Dimension(frame.getWidth() / 4, 30));
		northP.add(this.chooseF);
		
		// Добавление панелей на форму
		frame.getContentPane().add(BorderLayout.NORTH, this.northP);
		frame.getContentPane().add(BorderLayout.SOUTH, this.southP);
		
		// Подгон под квадратную область после добавления панелей. 60 - сумма высот элементов панелей
		int height = frame.getHeight() - 60;
		int width = height;
		frame.setSize(width, frame.getHeight());
		
		// Создание панели рисования
		this.display = new JImageDisplay(width, height);
		frame.getContentPane().add(BorderLayout.CENTER, this.display);
		
		// Добавление слушателя нажатия мыши по элементу
		display.addMouseListener(new mouse());
		
		// Добавление слушателей нажатия на кнопки
		resetB.addActionListener(new reset());
		saveB.addActionListener(new save());
		chooseF.addActionListener(new comboBoxClickListener());
		
		frame.setVisible(true);
	}
	
	public void drawFractal(int index) {
		
		// Очистка картинки после предыдущего рисунка
		this.clearImage();
		
		// Отключение GUI на время выполнения потоков
		this.enableUI(false);
		
		// Запуск потоков на выполнение отрисовки
		int pictureHeight = display.getHeight();
		int pictureWidth = display.getWidth();
		
		rowsRemaining = pictureHeight;
		
		for (int i = 0; i < pictureHeight; i++) {
			FractalWorker tempThread = new FractalWorker(i, pictureWidth, index);
			tempThread.execute();
		}
	}
	private class FractalWorker extends SwingWorker<Object, Object> {
		private int numOfStr;
		private int picWidth;
		private int[] strValues;
		private int index;
		public FractalWorker(int y, int picWidth, int index) {
			this.numOfStr = y;
			this.picWidth = picWidth;
			this.index = index;
		}
		@Override
        protected String doInBackground() throws Exception {
			
			strValues = new int[picWidth];
			
			for (int x = 0; x < picWidth; x++) {
				double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, display.getWidth(), x);
				double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, display.getHeight(), numOfStr);
				strValues[x] = fractals.get(index).numIterations(xCoord, yCoord);
			}
			
			return null;
		}

		@Override
        protected void done() {
			try {
				int x = 0;
				for (int numOfIter: strValues) {
					int rgbColor;
					if (numOfIter != -1) {
						float hue = 0.7f + (float) numOfIter / 200f; 
						rgbColor = Color.HSBtoRGB(hue, 1f, 1f); 
					} 
					else {
						rgbColor = Color.HSBtoRGB(0, 0, 0); 
					}
					
					display.drawPixelWithNoRepaint(x, numOfStr, new Color(rgbColor));
					
					x++;
				}
			} 
			catch (Exception e) { 
				e.printStackTrace(); 
            }
			
			rowsRemaining--;
			if (rowsRemaining == 0) {
				enableUI(true);
				
				display.repaintPicture();
			}
		} 
	}
	/*
	* Управление панелью для рисования
	*/
	public void setStartImage() {
		this.display.setStartImage();
	}
	
	public void clearImage() {
		this.display.clearImage();
	}
	private void enableUI(boolean enable) {
		chooseF.setEnabled(enable);
		saveB.setEnabled(enable);
		resetB.setEnabled(enable);
	}
	
	/*
	* Точка входа
	*/
	public static void main(String[] args) {
		FractalExplorer explorer = new FractalExplorer(800);
		explorer.createAndShowGUI();
	}
}