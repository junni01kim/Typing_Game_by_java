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

// GameFrame 클래스는 게임 창의 패널들을 관리하는 클래스이다.

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
	
	// setter함수
	public void setLoginSource(LoginSource loginSource) { this.loginSource = loginSource; }
	
	//getter함수 (가독성을 위해 모든 패널들 빼냄)
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

	//생성자
	public GameFrame() {
		// Frame의 속성설정
		setTitle("게임");
		setSize(800, 600);
		makeMenu();
		makeToolbar();
		getContentPane().add(gamePanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	// 메뉴바 생성 함수(필요 X)
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
	
	// 툴바 생성 함수
	private void makeToolbar() {
		JToolBar bar = new JToolBar();
		getContentPane().add(bar, BorderLayout.NORTH);
		
		// 시작&종료 버튼: 게임이 시작
		bPlay.addActionListener(new PlayActionListener());
		bar.add(bPlay);
		
		// 로그인 버튼: 계정을 연결
		JButton bSignin = new JButton("Sign in");
		bSignin.addActionListener(new SignInActionListener());
		bar.add(bSignin);
				
		// 회원가입 버튼: 계정을 생성
		JButton bCreate = new JButton("Create");
		bCreate.addActionListener(new CreateActionListener());
		bar.add(bCreate);
		
		bDiffculty.addActionListener(new DiffcultyActionListener());
		bar.add(bDiffculty);
		
		bar.setFloatable(false);
	}

	// 게임 시작버튼
	private class PlayActionListener implements ActionListener {
		JButton myButton;
		public void actionPerformed(ActionEvent e) {
			myButton = (JButton)e.getSource();
			// Play 버튼이 눌리면 Stop버튼이 되고
			// Play 버튼은 게임을 시작한다.
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
			// Stop 버튼을 누르면 Play가 된다.
			// Stop 버튼은 모든 스레드를 한번에 종료시킨다.
			else {
				myButton.setText("Play");
				gameGround.getRoundThread().delete();
			}
		}
	}
	
	// 로그인 버튼: LoginDialog를 생성한다.
	private class SignInActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LoginDialog loginDialog = new LoginDialog(gameFrame);
		}
	}
	
	
	// 회원가입 버튼: CreateDialog를 생성한다.
	private class CreateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CreateDialog createDialog = new CreateDialog(gameFrame);
		}
	}
	
	// 게임 시작버튼
	private class DiffcultyActionListener implements ActionListener {
		JButton myButton;
		public void actionPerformed(ActionEvent e) {
			myButton = (JButton)e.getSource();
			// Play 버튼이 눌리면 Stop버튼이 되고
			// Play 버튼은 게임을 시작한다.
			if(myButton.getText().equals("Easy")) {
				myButton.setText("Hard");
			}
			// Stop 버튼을 누르면 Play가 된다.
			// Stop 버튼은 모든 스레드를 한번에 종료시킨다.
			else {
				myButton.setText("Easy");
				
			}
		}
	}
}
