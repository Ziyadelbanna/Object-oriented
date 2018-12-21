package eg.edu.alexu.csd.oop.draw.cs27;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import eg.edu.alexu.csd.oop.draw.Shape;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;

public class paint extends JPanel {

	public JFrame frame;
	private Image img;
	MyDrawEngine drawEngine = new MyDrawEngine();
	private static final int IMG_WIDTH = 30;
	private static final int IMG_HEIGHT = 25;
	private static final Color SHAPE_COLOR = Color.BLACK;
	private static final int GAP = 3;
	ArrayList<Shape> selectedShape;
	int x = GAP;
	int y = x;
	int x2 = IMG_WIDTH - 2 * x;
	int y2 = IMG_WIDTH - 2 * y;
	int width = IMG_WIDTH - 2 * x;
	int height = IMG_HEIGHT - 2 * y;
	int[] xt = { (int) 13, (int) IMG_WIDTH - 4, (int) ((IMG_WIDTH - 4) - (25)) };
	int[] yt = { (int) 1, (int) IMG_HEIGHT - 4, (int) IMG_HEIGHT - 4 };
	boolean released = false;
	MyPanel mp = new MyPanel();
	Color fillcolor = Color.WHITE;
	Color bordercolor = Color.BLACK;
	private Graphics g2d;
	boolean delete, resize, move, copy = false;
	int movedwinth, movedheight;
	ArrayList<Shape> selected = new ArrayList<>();
	Shape drawn;
	ArrayList<JButton> buttons = new ArrayList<>();
	ArrayList<JRadioButton> rbtns = new ArrayList<>();
	private JTextField filename = new JTextField(), dir = new JTextField();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					paint window = new paint();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public paint() throws InstantiationException, IllegalAccessException {
		initialize();
	}
	/**
	 * Initialise the contents of the frame.
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("deprecation")
	private void initialize() throws InstantiationException, IllegalAccessException {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBounds(100, 100, 903, 670);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Paint");
		mp.setBackground(Color.white);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(SystemColor.activeCaption));
		panel_2.setBackground(SystemColor.inactiveCaption);
		panel_2.setBounds(12, 0, 1350, 162);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		JLabel lblColors = new JLabel("Border Color");
		lblColors.setBounds(73, 52, 79, 16);
		panel_2.add(lblColors);

		JPanel panel = new JPanel();
		panel.setBounds(10, 0, 218, 41);
		panel_2.add(panel);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setLayout(null);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(bordercolor);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(119, 11, 33, 23);
		panel.add(btnNewButton);
		JButton btnChange = new JButton("Change");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(null, "Choose a color", bordercolor);
				bordercolor = newColor;
				btnNewButton.setBackground(newColor);

			}
		});
		btnChange.setBounds(10, 11, 79, 23);
		panel.add(btnChange);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_4.setBackground(SystemColor.inactiveCaption);
		panel_4.setBounds(10, 79, 218, 45);
		panel_2.add(panel_4);

		JButton button_1 = new JButton("");
		button_1.setBackground(fillcolor);
		button_1.setBounds(119, 11, 33, 23);
		panel_4.add(button_1);
		JButton button = new JButton("Change");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(null, "Choose a color", fillcolor);
				fillcolor = newColor;
				button_1.setBackground(newColor);

			}
		});
		button.setBounds(10, 11, 79, 23);
		panel_4.add(button);

		JLabel lblFillColor = new JLabel("Fill Color");
		lblFillColor.setBounds(96, 135, 56, 16);
		panel_2.add(lblFillColor);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(238, 79, 1092, 45);
		panel_2.add(panel_1);
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setLayout(null);

		for (int i = 0; i < drawEngine.classeslist.size(); i++) {
			buttons.add(i, new JButton(drawEngine.classeslist.get(i).getSimpleName()));
			buttons.get(i).setBounds(5 + i * 105, 5, 100, 30);
			final int k = i;
			buttons.get(i).addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Class<? extends Shape> x = drawEngine.classeslist.get(k);
					try {
						drawn = (Shape) x.newInstance();

					} catch (InstantiationException | IllegalAccessException e1) {
						e1.printStackTrace();
					}
					Map<String, Double> prop = new HashMap<>();
					for (String key : drawn.getProperties().keySet()) {
						String v = JOptionPane.showInputDialog(key);
						if (v.isEmpty()) {
							drawn = null;
							break;
						}
						double value = Double.valueOf(v);
						prop.put(key, value);
					}
					if (drawn != null)
						drawn.setProperties(prop);

				}
			});
			panel_1.add(buttons.get(i));
		}

		JButton btnClear = new JButton("CLEAR");
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnClear.setHorizontalAlignment(SwingConstants.LEFT);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawEngine.clear();
				drawn = null;
				selected.clear();
				rbtns.clear();
				drawEngine.refresh(mp.getGraphics());
				mp.getGraphics().clearRect(0, 0, 1000, 1000);
			}
		});
		btnClear.setIcon(
				new ImageIcon(paint.class.getResource("/com/sun/javafx/scene/control/skin/modena/dialog-error.png")));
		btnClear.setBounds(1204, 13, 136, 45);
		panel_2.add(btnClear);

		JButton btnUndo = new JButton("undo");
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawEngine.undo();
				mp.getGraphics().clearRect(0, 0, 1000, 1000);
				drawEngine.refresh(mp.getGraphics());
			}
		});
		btnUndo.setBounds(238, 13, 89, 23);
		panel_2.add(btnUndo);

		JButton btnRedo = new JButton("redo");
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				drawEngine.redo();
				mp.getGraphics().clearRect(0, 0, 1000, 1000);
				drawEngine.refresh(mp.getGraphics());
			}
		});
		btnRedo.setBounds(440, 13, 89, 23);
		panel_2.add(btnRedo);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(1215, 173, 137, 521);
		frame.getContentPane().add(panel_5);
		JPanel panel_3 = new JPanel();
		// (12, 164, 1700, 541)
		panel_3.setBounds(0, 164, 1205, 541);
		;
		panel_3.add(mp);
		frame.getContentPane().add(panel_3);
		JButton btnSelect = new JButton("select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_5.removeAll();
				rbtns.clear();
				LinkedList<Shape> temp = drawEngine.shapeslists.get(drawEngine.currentindex);
				for (int i = 0; i < temp.size(); i++) {
					rbtns.add(new JRadioButton((i + 1) + ". " + temp.get(i).getClass().getSimpleName()));
					panel_5.add(rbtns.get(i));
				}
				panel_5.revalidate();
			}
		});
		btnSelect.setBounds(341, 13, 89, 23);
		panel_2.add(btnSelect);
		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete = true;
				panel_5.removeAll();
				panel_5.repaint();

				mp.paintComponent(mp.getGraphics());
				// panel_3.revalidate();

			}
		});

		btnDelete.setBounds(238, 49, 89, 23);
		panel_2.add(btnDelete);
		JButton btnResize = new JButton("resize");
		btnResize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp.removeAll();
				panel_5.removeAll();
				resize = true;

				panel_5.revalidate();
				mp.paintComponent(mp.getGraphics());

			}

		});
		btnResize.setBounds(440, 49, 89, 23);
		panel_2.add(btnResize);
		JButton btnmove = new JButton("move");
		btnmove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mp.removeAll();
				panel_5.removeAll();
				panel_5.repaint();
				move = true;
				mp.paintComponent(mp.getGraphics());

			}
		});
		btnmove.setBounds(337, 49, 93, 23);
		panel_2.add(btnmove);

		JButton btnPlugin = new JButton("PlugIn");
		btnPlugin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filename = File.separator + "tmp";
				JFileChooser fc = new JFileChooser(new File(filename));
				int result = fc.showOpenDialog(null);
				String selectedFilePath = fc.getSelectedFile().getPath().toString();
				// savepath(selectedFilePath);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						JarInputStream jarFile = new JarInputStream(new FileInputStream(selectedFilePath));
						JarEntry jarEntry;
						java.net.URL[] urls = { new java.net.URL("jar:file:" + selectedFilePath + "!/") };
						ClassLoader loader = getClass().getClassLoader();
						final ClassLoader loa = URLClassLoader.newInstance(urls, loader);
						jarEntry = jarFile.getNextJarEntry();
						// System.out.println(jarEntry);
						if (jarEntry.getName().endsWith(".class")) {

							String classBinName = jarEntry.getName().replaceAll("/", "\\.");
							classBinName = classBinName.substring(0, classBinName.length() - 6);
							// System.out.println(classBinName);
							System.out.println((Class<? extends Shape>) loa.getClass().forName(classBinName, true, loa)
									+ "hahhahaha");
							Class<? extends Shape> c1 = (Class<? extends Shape>) loa.getClass().forName(classBinName,
									true, loa);
							drawEngine.classeslist.add(c1);
							buttons.clear();
							panel_1.removeAll();
							for (int i = 0; i < drawEngine.classeslist.size(); i++) {
								buttons.add(i, new JButton(drawEngine.classeslist.get(i).getSimpleName()));
								buttons.get(i).setBounds(5 + i * 105, 5, 100, 30);
								final int k = i;
								buttons.get(i).addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										Class<? extends Shape> x = drawEngine.classeslist.get(k);
										try {
											drawn = (Shape) x.newInstance();

										} catch (InstantiationException | IllegalAccessException e1) {
											e1.printStackTrace();
										}
										Map<String, Double> prop = new HashMap<>();

										for (String key : drawn.getProperties().keySet()) {
											String v = JOptionPane.showInputDialog(key);
											if (v.isEmpty()) {
												drawn = null;

											}
											double value = Double.valueOf(v);
											prop.put(key, value);
										}
										if (drawn != null)
											drawn.setProperties(prop);

									}
								});
								panel_1.add(buttons.get(i));
								panel_1.repaint();

							}
						}
					} catch (Exception e) {

					}
				}
			}
		});
		btnPlugin.setBounds(586, 18, 89, 23);
		panel_2.add(btnPlugin);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("choose save place");
				int rVal = chooser.showSaveDialog(mp);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					File f = chooser.getSelectedFile();
					// if (f.getName().contains("xml")) {
					// drawEngine.save(f.getAbsolutePath());
					// } else {
					// f = new File(f.toString() + ".xml"); // append .xml if "foo.jpg.xml" is OK
					// f = new File(f.getParentFile(),
					// FilenameUtils.getBaseName(f.getName())+".xml"); // ALTERNATIVELY: remove the
					// extension (if any) and replace it with ".xml"
					drawEngine.save(f.getAbsolutePath());
				}
				// if (f.getName().contains("json")) {
				// drawEngine.save(f.getAbsolutePath());
				// } else {
				// f = new File(f.toString() + ".json"); // append .xml if "foo.jpg.xml" is OK
				// // f = new File(f.getParentFile(),
				// // FilenameUtils.getBaseName(f.getName())+".xml"); // ALTERNATIVELY: remove
				// the
				// // extension (if any) and replace it with ".xml"
				// drawEngine.save(f.getAbsolutePath());
				// }
				// }
				else {
					System.out.println("No Selection ");
				}
			}
		});

		btnSave.setBounds(789, 13, 89, 23);
		panel_2.add(btnSave);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("choose load place");
				int rval = chooser.showOpenDialog(mp);
				if (rval == JFileChooser.APPROVE_OPTION) {
					File f = chooser.getSelectedFile();
					drawEngine.load(f.getAbsolutePath());
					System.out.println(drawEngine.shapeslists.getLast().size());
					drawEngine.refresh(g2d);
					mp.repaint();
				} else {
					System.out.println("No Selection");
				}
			}
		});
		btnLoad.setBounds(911, 13, 89, 23);
		panel_2.add(btnLoad);

		JButton btnCopy = new JButton("copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mp.removeAll();
				panel_5.removeAll();
				panel_5.repaint();
				copy = true;
				mp.paintComponent(mp.getGraphics());

			}
		});
		btnCopy.setBounds(586, 49, 89, 23);
		panel_2.add(btnCopy);

	}

	public class MyPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int x, y;

		MyPanel() {
			x = y = 0;
			MyMouseListener listener = new MyMouseListener();
			addMouseListener(listener);
			addMouseMotionListener(listener);
		}

		public void setStartPoint(int x, int y) {
			this.x = x;
			this.y = y;
		}

		class MyMouseListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {

				setStartPoint(e.getX(), e.getY());
				paintComponent(g2d);
			}

		}

		private void SetSelectedShapes() {
			selected.clear();
			LinkedList<Shape> temp = drawEngine.shapeslists.get(drawEngine.currentindex);

			for (int i = 0; i < rbtns.size(); i++) {
				if (rbtns.get(i).isSelected())
					selected.add(temp.get(i));
			}

		}

		public void paintComponent(Graphics g) {
			g2d = (Graphics2D) g;
			super.paintComponent(g2d);
			drawEngine.refresh(g2d);
			if (delete) {
				SetSelectedShapes();
				for (Shape shape : selected) {
					drawEngine.removeShape(shape);
				}
				rbtns.clear();
				selected.clear();
				delete = false;
				drawEngine.refresh(mp.getGraphics());
				mp.revalidate();
			} else if (resize) {
				SetSelectedShapes();
				if (!(selected == null)) {
					Map<String, Double> prop = new HashMap<>();
					for (int i = 0; i < selected.size(); i++) {
						System.out.println(i);
						for (String key : selected.get(i).getProperties().keySet()) {

							// System.out.println(selected.get(i).getProperties().keySet().size());
							double value = Double.valueOf(JOptionPane.showInputDialog(key));
							prop.put(key, value);
						}
						Class<? extends Shape> x = selected.get(i).getClass();
						Shape temp = null;
						try {
							temp = (Shape) x.newInstance();
							temp.setProperties(new HashMap<>(prop));
							temp.setColor(selected.get(i).getColor());
							temp.setFillColor(selected.get(i).getFillColor());
							temp.setPosition(selected.get(i).getPosition());

						} catch (InstantiationException | IllegalAccessException e2) {
							e2.printStackTrace();
						}
						drawEngine.updateShape(selected.get(i), temp);

					}
					mp.revalidate();

					revalidate();
					repaint();
					rbtns.clear();
					selected.clear();
					resize = false;
				}

				// mp.revalidate();
			} else if (move) {

				SetSelectedShapes();
				int width = Integer.valueOf(JOptionPane.showInputDialog("x"));
				int height = Integer.valueOf(JOptionPane.showInputDialog("y"));

				for (int i = 0; i < selected.size(); i++) {
					Class<? extends Shape> x = selected.get(i).getClass();
					Shape temp = null;
					try {
						temp = (Shape) x.newInstance();
						temp.setProperties(new HashMap<>(selected.get(i).getProperties()));
						temp.setColor(selected.get(i).getColor());
						temp.setFillColor(selected.get(i).getFillColor());
						temp.setPosition(new Point(selected.get(i).getPosition().x + width,
								selected.get(i).getPosition().y + height));

					} catch (InstantiationException | IllegalAccessException e2) {
						e2.printStackTrace();
					}
					drawEngine.updateShape(selected.get(i), temp);

				}
				move = false;
				released = false;
				revalidate();
				repaint();
				rbtns.clear();
				selected.clear();

			} else if (copy) {

				SetSelectedShapes();
				int width = Integer.valueOf(JOptionPane.showInputDialog("x"));
				int height = Integer.valueOf(JOptionPane.showInputDialog("y"));

				for (int i = 0; i < selected.size(); i++) {
					Class<? extends Shape> x = selected.get(i).getClass();
					Shape temp = null;
					try {
						temp = (Shape) x.newInstance();
						temp.setProperties(new HashMap<>(selected.get(i).getProperties()));
						temp.setColor(selected.get(i).getColor());
						temp.setFillColor(selected.get(i).getFillColor());
						temp.setPosition(new Point(selected.get(i).getPosition().x + width,
								selected.get(i).getPosition().y + height));
						System.out.println(temp.getPosition());
						System.out.println(temp.getProperties());

					} catch (InstantiationException | IllegalAccessException e2) {
						e2.printStackTrace();
					}
					drawEngine.addShape(temp);

				}
				copy = false;
				released = false;
				revalidate();
				repaint();
				rbtns.clear();
				selected.clear();
			} else {
				if (drawn != null) {
					Class<? extends Shape> x = drawn.getClass();
					Shape temp = null;

					try {
						temp = (Shape) x.newInstance();
						temp.setProperties(drawn.getProperties());
						temp.setColor(bordercolor);
						temp.setFillColor(fillcolor);
						temp.setPosition(new Point(this.x, y));
						temp.draw(g2d);
						drawEngine.addShape(temp);
						drawEngine.refresh(g2d);
						mp.revalidate();
						revalidate();
						mp.repaint();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
					drawn = null;

				}
			}

			/*
			 * if(select==true) { Shape temp= drawEngine.findSelectedShape(x, y);
			 * if(temp!=null) { if(!selectedShape.contains(temp)) selectedShape.add(temp); }
			 * 
			 * }
			 */

		}

		public Dimension getPreferredSize() { // dimensions of paint canvas
			return new Dimension(900, 900);
		}
	}
}
