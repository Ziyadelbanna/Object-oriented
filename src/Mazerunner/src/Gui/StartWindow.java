package Gui;

import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

import models.mazeTimer;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.format.TextStyle;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.SystemColor;
import java.awt.GridLayout;

public class StartWindow {

	public static JFrame frame;
	private JPanel panel_main;
	private JPanel panelCheckBoard;
	private JPanel panelScoreTitle;
	private JPanel panelScore;
	private JPanel panelTimeTitle;
	private JPanel panelTime;
	private JPanel panelBulletsTitle;
	private JPanel panelBullets;
	private JPanel panelLifeTitle;
	private JPanel panelLife;
	private JPanel panelCanvas;
	private ImageIcon img;
	private JLabel lblPhoto;
	private JLabel lblLogo;
	private JLabel lblScoreTitle;
	private JLabel lblScore;
	private JLabel lblTimeTitle;
	public static JLabel lblTime;
	private JLabel lblBulletsTitle;
	private JLabel lblBullets;
	private JLabel lblLifeTitle;
	private JLabel lblLife;
	private JButton btnStartNewGame;
	private JButton btnInstructions;
	private Canvas canvas;

	private int x = 150;
	private int y = 100;

	ArrayDraw d = new ArrayDraw();

	ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnStartNewGame) {
				String firstDim = JOptionPane.showInputDialog(frame, "Please Enter The" + "Heigth :");
				String secondDim = JOptionPane.showInputDialog(frame, "Please Enter The" + "Width :");
				d.setDimension(firstDim, secondDim);
				panel_main.removeAll();
				panel_main.add(panelCanvas);
				panel_main.add(panelCheckBoard);
				panel_main.setBackground(Color.black);
				panel_main.validate();
				panel_main.repaint();

			}
			if (e.getSource() == btnInstructions) {
				JOptionPane.showMessageDialog(null, "Welcome to our Programme"
						+ "---------------------------------------------------------------- \n"
						+ "1- Press Start game\n" 
						+ "2- Play in the maze collect gifts, bombs, bullets\n"
						+ "3- Becarefulll from the monsters !\n"
						+ "4- Try to get to the gate as soon as possible to get highest score\n" + "5- Have fun!!!\n"
						+ "---------------------------------------------------------------- \n"
						+ "---------------------------------------------------------------- \n", "Instructions",
						JOptionPane.INFORMATION_MESSAGE);
			}
		};
	};

	KeyListener action = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			// Thread firingThread = new Thread();
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				d.up();
				lblBullets.setText(d.getbullet());
				lblLife.setText(d.getLife());
				lblScore.setText(d.getScore());
				d.lose();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				d.down();
				lblBullets.setText(d.getbullet());
				lblLife.setText(d.getLife());
				lblScore.setText(d.getScore());
				d.lose();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				d.right();
				lblBullets.setText(d.getbullet());
				lblLife.setText(d.getLife());
				lblScore.setText(d.getScore());
				d.lose();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				d.left();
				lblBullets.setText(d.getbullet());
				lblLife.setText(d.getLife());
				lblScore.setText(d.getScore());
				d.lose();
			}

			Thread firingThread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					// System.out.println("Here");

					if (e.getKeyChar() == 'd') {
						if (!lblBullets.getText().equals("0")) {
							d.fireR();
							lblBullets.setText(d.getbullet());
							lblLife.setText(d.getLife());
							lblScore.setText(d.getScore());
							d.lose();
						}
					}
					if (e.getKeyChar() == 'a') {
						if (!lblBullets.getText().equals("0")) {
							d.fireL();
							lblBullets.setText(d.getbullet());
							lblLife.setText(d.getLife());
							lblScore.setText(d.getScore());
							d.lose();
						}
					}
					if (e.getKeyChar() == 'w') {
						if (!lblBullets.getText().equals("0")) {
							d.fireU();
							lblBullets.setText(d.getbullet());
							lblLife.setText(d.getLife());
							lblScore.setText(d.getScore());
							d.lose();
						}
					}
					if (e.getKeyChar() == 'x') {
						if (!lblBullets.getText().equals("0")) {
							d.fireD();
							lblBullets.setText(d.getbullet());
							lblLife.setText(d.getLife());
							lblScore.setText(d.getScore());
							d.lose();
						}
					}
				}

				@Override
				public String toString() {
					return "Fire Thread";
				}
			});

			firingThread.start();

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * Launch the application.
	 */
	public void mainGui() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartWindow window = new StartWindow();
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
	public StartWindow() {
		initialize();
		// d.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Pack Man");
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(-6, -6, 1380, 800);
		frame.addKeyListener(action);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panel_main = new JPanel();
		panel_main.setBackground(Color.WHITE);
		// panel_main.setBackground(Color.BLACK);
		panel_main.setBounds(0, 0, 1364, 749);
		panel_main.addKeyListener(action);
		frame.getContentPane().add(panel_main);
		panel_main.setLayout(null);

		// --------------------------------Start First
		// Window------------------------------//

		lblPhoto = new JLabel("");
		img = new ImageIcon("res/index.png");
		lblPhoto.setIcon(img);
		lblPhoto.setBounds(532, 200, 224, 227);
		panel_main.add(lblPhoto);

		lblLogo = new JLabel("");
		img = new ImageIcon("res/logo.png");
		lblLogo.setIcon(img);
		lblLogo.setBounds(487, 48, 374, 128);
		panel_main.add(lblLogo);

		btnStartNewGame = new JButton("Start Game");
		btnStartNewGame.setForeground(Color.RED);
		btnStartNewGame.setFont(new Font("Verdana", Font.BOLD, 23));
		btnStartNewGame.setBounds(290, 559, 287, 41);
		btnStartNewGame.setContentAreaFilled(false);
		btnStartNewGame.setFocusPainted(false);
		btnStartNewGame.setBorderPainted(false);
		btnStartNewGame.addActionListener(actionListener);
		panel_main.add(btnStartNewGame);

		btnInstructions = new JButton("Instructions");
		btnInstructions.setForeground(Color.RED);
		btnInstructions.setFont(new Font("Verdana", Font.BOLD, 23));
		btnInstructions.setBounds(765, 559, 279, 41);
		btnInstructions.setContentAreaFilled(false);
		btnInstructions.setFocusPainted(false);
		btnInstructions.setBorderPainted(false);
		btnInstructions.addActionListener(actionListener);
		panel_main.add(btnInstructions);

		// ---------------------------------End First
		// Window--------------------------------//

		// --------------------------------Start Second
		// Window------------------------------//

		panelCheckBoard = new JPanel();
		panelCheckBoard.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.RED));
		panelCheckBoard.setBackground(Color.ORANGE);
		panelCheckBoard.setBounds(1167, 105, 197, 494);
		// panel_main.add(panelCheckBoard);
		panelCheckBoard.setLayout(null);

		panelScore = new JPanel();
		panelScore.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		panelScore.setBackground(Color.RED);
		panelScore.setBounds(46, 22, 105, 44);
		panelCheckBoard.add(panelScore);
		panelScore.setLayout(null);

		lblScoreTitle = new JLabel("Score");
		lblScoreTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblScoreTitle.setToolTipText("Score");
		lblScoreTitle.setFont(new Font("Tekton Pro Ext", Font.BOLD, 18));
		lblScoreTitle.setBounds(0, 0, 105, 44);
		panelScore.add(lblScoreTitle);

		panelScoreTitle = new JPanel();
		panelScoreTitle.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, Color.RED, Color.RED, Color.RED));
		panelScoreTitle.setBackground(Color.GREEN);
		panelScoreTitle.setBounds(28, 52, 142, 62);
		panelCheckBoard.add(panelScoreTitle);
		panelScoreTitle.setLayout(null);

		lblScore = new JLabel("0");
		lblScore.setToolTipText("Score");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setFont(new Font("Tekton Pro Ext", Font.PLAIN, 12));
		lblScore.setBounds(10, 25, 122, 26);
		panelScoreTitle.add(lblScore);

		panelTimeTitle = new JPanel();
		panelTimeTitle.setLayout(null);
		panelTimeTitle
				.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		panelTimeTitle.setBackground(Color.RED);
		panelTimeTitle.setBounds(46, 138, 105, 44);
		panelCheckBoard.add(panelTimeTitle);

		lblTimeTitle = new JLabel("Time");
		lblTimeTitle.setToolTipText("Time");
		lblTimeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeTitle.setFont(new Font("Tekton Pro Ext", Font.BOLD, 18));
		lblTimeTitle.setBounds(0, 0, 105, 44);
		panelTimeTitle.add(lblTimeTitle);

		panelTime = new JPanel();
		panelTime.setLayout(null);
		panelTime.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, Color.RED, Color.RED, Color.RED));
		panelTime.setBackground(Color.GREEN);
		panelTime.setBounds(28, 168, 142, 62);
		panelCheckBoard.add(panelTime);

		lblTime = new JLabel();
		lblTime.setFont(new Font("Tekton Pro Ext", Font.PLAIN, 12));
		lblTime.setToolTipText("Time");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("Tekton Pro Ext", Font.PLAIN, 12));
		lblTime.setBounds(10, 25, 122, 26);
		panelTime.add(lblTime);

		panelBulletsTitle = new JPanel();
		panelBulletsTitle.setLayout(null);
		panelBulletsTitle
				.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		panelBulletsTitle.setBackground(Color.RED);
		panelBulletsTitle.setBounds(46, 255, 105, 44);
		panelCheckBoard.add(panelBulletsTitle);

		lblBulletsTitle = new JLabel("Bullets");
		lblBulletsTitle.setToolTipText("Bullets");
		lblBulletsTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblBulletsTitle.setFont(new Font("Tekton Pro Ext", Font.BOLD, 18));
		lblBulletsTitle.setBounds(0, 0, 105, 44);
		panelBulletsTitle.add(lblBulletsTitle);

		panelBullets = new JPanel();
		panelBullets.setLayout(null);
		panelBullets.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, Color.RED, Color.RED, Color.RED));
		panelBullets.setBackground(Color.GREEN);
		panelBullets.setBounds(28, 285, 142, 62);
		panelCheckBoard.add(panelBullets);

		lblBullets = new JLabel("6");
		lblBullets.setToolTipText("Score");
		lblBullets.setHorizontalAlignment(SwingConstants.CENTER);
		lblBullets.setFont(new Font("Tekton Pro Ext", Font.PLAIN, 12));
		lblBullets.setBounds(10, 25, 122, 26);
		panelBullets.add(lblBullets);

		panelLifeTitle = new JPanel();
		panelLifeTitle.setLayout(null);
		panelLifeTitle
				.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		panelLifeTitle.setBackground(Color.RED);
		panelLifeTitle.setBounds(46, 374, 105, 44);
		panelCheckBoard.add(panelLifeTitle);

		lblLifeTitle = new JLabel("Life");
		lblLifeTitle.setToolTipText("Score");
		lblLifeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblLifeTitle.setFont(new Font("Tekton Pro Ext", Font.BOLD, 18));
		lblLifeTitle.setBounds(0, 0, 105, 44);
		panelLifeTitle.add(lblLifeTitle);

		panelLife = new JPanel();
		panelLife.setLayout(null);
		panelLife.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, Color.RED, Color.RED, Color.RED));
		panelLife.setBackground(Color.GREEN);
		panelLife.setBounds(28, 404, 142, 62);
		panelCheckBoard.add(panelLife);

		lblLife = new JLabel("6");
		lblLife.setToolTipText("Score");
		lblLife.setHorizontalAlignment(SwingConstants.CENTER);
		lblLife.setFont(new Font("Tekton Pro Ext", Font.PLAIN, 12));
		lblLife.setBounds(10, 25, 122, 26);
		panelLife.add(lblLife);

		d.drawing();
		panelCanvas = d;
		panelCanvas.setBackground(SystemColor.desktop);
		panelCanvas.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panelCanvas.setBounds(10, 0, 1147, 749);

		// ---------------------------------End Second
		// Window-------------------------------//

	}
}
