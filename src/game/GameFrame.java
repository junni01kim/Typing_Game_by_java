package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import game.GameGround.RoundThread;

// GameFrame Ŭ������ ���� â�� �гε��� �����ϴ� Ŭ�����̴�.

public class GameFrame extends JFrame {
	private GameFrame gameFrame = this;
	private JButton bPlay = new JButton("Play");
	JButton bDiffculty = new JButton("Easy");
	private HashMap<String,Account> accountData = new HashMap<>();
	private LoginSource loginSource = new LoginSource(this);
	private RankingSource rankingSource = new RankingSource();
	private ScorePanel scorePanel = new ScorePanel(this);
	private TextSource textSource = new TextSource();
	private GameGround gameGround = new GameGround(this);
	private GamePanel gamePanel = new GamePanel(this);
	
	// setter�Լ�
	public void setLoginSource(LoginSource loginSource) { this.loginSource = loginSource; }
	
	//getter�Լ� (�������� ���� ��� �гε� ����)
	public GameFrame getGameFrame() {return gameFrame;}
	public GamePanel getGamePanel() {return gamePanel;}
	public ScorePanel getScorePanel() {return scorePanel;}
	public TextSource getTextSource() {return textSource;}
	public GameGround getGameGround() {return gameGround;}
	public LoginSource getLoginSource() {return loginSource;}
	public HashMap<String,Account> getAccountData() {return accountData;}
	public RankingSource getRankingSource() {return rankingSource;}
	
	// getter for GameGround.endGame()
	public JButton getBPlay() {return bPlay;}

	//������
	public GameFrame() {
		// Frame�� �Ӽ�����
		setTitle("����");
		setSize(800, 600);
		makeMenu();
		makeToolbar();
		getContentPane().add(gamePanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	// �޴��� ���� �Լ�(�ʿ� X)
	private void makeMenu() {
		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem("Open"));
		fileMenu.add(new JMenuItem("Save"));
		fileMenu.add(new JMenuItem("Save As"));
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitItem);
		mb.add(fileMenu);
		
		JMenu editMenu = new JMenu("Edit");
		editMenu.add(new JMenuItem("insert"));
		editMenu.add(new JMenuItem("replace"));
		mb.add(editMenu);
	}
	
	// ���� ���� �Լ�
	private void makeToolbar() {
		JToolBar bar = new JToolBar();
		getContentPane().add(bar, BorderLayout.NORTH);
		
		// ����&���� ��ư: ������ ����
		bPlay.addActionListener(new PlayActionListener());
		bar.add(bPlay);
		
		// �α��� ��ư: ������ ����
		JButton bSignin = new JButton("Sign in");
		bSignin.addActionListener(new SignInActionListener());
		bar.add(bSignin);
				
		// ȸ������ ��ư: ������ ����
		JButton bCreate = new JButton("Create");
		bCreate.addActionListener(new CreateActionListener());
		bar.add(bCreate);
		
		bDiffculty.addActionListener(new DiffcultyActionListener());
		bar.add(bDiffculty);
		
		bar.setFloatable(false);
	}

	// ���� ���۹�ư
	private class PlayActionListener implements ActionListener {
		JButton myButton;
		public void actionPerformed(ActionEvent e) {
			myButton = (JButton)e.getSource();
			// Play ��ư�� ������ Stop��ư�� �ǰ�
			// Play ��ư�� ������ �����Ѵ�.
			if(myButton.getText().equals("Play")) {
				for(int i=0;i<getScorePanel().getLabelLength();i++) {
					getScorePanel().getLabel(i).setText("");
				}
				myButton.setText("Stop");
				if(bDiffculty.getText().equals("Easy"))
					gameGround.startRoundThread(1000);
				else
					gameGround.startRoundThread(500);
			}
			// Stop ��ư�� ������ Play�� �ȴ�.
			// Stop ��ư�� ��� �����带 �ѹ��� �����Ų��.
			else {
				myButton.setText("Play");
				gameGround.getRoundThread().delete();
			}
		}
	}
	
	// �α��� ��ư: LoginDialog�� �����Ѵ�.
	private class SignInActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LoginDialog loginDialog = new LoginDialog(gameFrame);
		}
	}
	
	
	// ȸ������ ��ư: CreateDialog�� �����Ѵ�.
	private class CreateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CreateDialog createDialog = new CreateDialog(gameFrame);
		}
	}
	
	// ���� ���۹�ư
	private class DiffcultyActionListener implements ActionListener {
		JButton myButton;
		public void actionPerformed(ActionEvent e) {
			myButton = (JButton)e.getSource();
			// Play ��ư�� ������ Stop��ư�� �ǰ�
			// Play ��ư�� ������ �����Ѵ�.
			if(myButton.getText().equals("Easy")) {
				myButton.setText("Hard");
			}
			// Stop ��ư�� ������ Play�� �ȴ�.
			// Stop ��ư�� ��� �����带 �ѹ��� �����Ų��.
			else {
				myButton.setText("Easy");
				
			}
		}
	}
}
