package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
	private JButton bDiffculty = new JButton("Easy");
	private JButton bPause = new JButton("Pause");
	private HashMap<String,Account> accountData = new HashMap<>();
	private LoginSource loginSource = new LoginSource(this);
	private RankingSource rankingSource = new RankingSource();
	private ScorePanel scorePanel = new ScorePanel(this);
	private TextSource textSource = new TextSource();
	private GameGround gameGround = new GameGround(this);
	private GamePanel gamePanel = new GamePanel(this);
	private Clip backgroundClip;
	
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
		makeToolbar();
		getContentPane().add(gamePanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		loadAudio("BackgroundSound.wav");
	}
	
	private void loadAudio(String pathName) {
		try {
			backgroundClip = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			backgroundClip.open(audioStream);
		}
		catch(LineUnavailableException e) {e.printStackTrace();}
		catch(UnsupportedAudioFileException e) {e.printStackTrace();}
		catch(IOException e) {e.printStackTrace();}
	}
	
	// 툴바 생성 함수
	private void makeToolbar() {
		JToolBar bar = new JToolBar();
		getContentPane().add(bar, BorderLayout.NORTH);
		
		// 시작&종료 버튼: 게임이 시작
		bPlay.addActionListener(new PlayActionListener());
		bar.add(bPlay);
		
		// 일시정지 버튼: 게임이 일시정지
		bPause.addActionListener(new PauseActionListener());
		bar.add(bPause);
		
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
				
				// ScorePanel에 작성된 그림 전체 삭제(백그라운드 제외)
				getScorePanel().getCenterPanel().repaint();
				
				backgroundClip.start();
				
				myButton.setText("Stop");
				if(bDiffculty.getText().equals("Easy"))
					gameGround.startRoundThread(1000);
				else
					gameGround.startRoundThread(500);
			}
			// Stop 버튼을 누르면 Play가 된다.
			// Stop 버튼은 모든 스레드를 한번에 종료시킨다.
			else {
				// 예외처리: 만약 일시정지라면 일시정지를 제거한다.
				if(bPause.getText().equals("Resume")) {
					for(int i=0; i<gameGround.getWordThreadLength(); i++) {
						gameGround.getWordThread(i).resumeGame();
					}
					bPause.setText("Pause");
				}
				
				myButton.setText("Play");
				gameGround.getRoundThread().delete();
				backgroundClip.stop();
			}
		}
	}
	
	// 회원가입 버튼: PauseDialog를 생성한다.
	private class PauseActionListener implements ActionListener {
		JButton myButton;
		public void actionPerformed(ActionEvent e) {
			// 예외처리: 일시정지는 게임이 시작되야만 실행된다.
			if(bPlay.getText().equals("Play"))
				return;
			
			myButton = (JButton)e.getSource();
			// Pause 버튼이 눌리면 Resume버튼이 되고
			// Pause 버튼은 게임을 일시정지한다.
			if(myButton.getText().equals("Pause")) {
				for(int i=0; i<gameGround.getWordThreadLength(); i++) {
					gameGround.getWordThread(i).pauseGame();
				}
				myButton.setText("Resume");
			}
			// Resume 버튼을 누르면 게임이 재개 된다.
			else {
				for(int i=0; i<gameGround.getWordThreadLength(); i++) {
					gameGround.getWordThread(i).resumeGame();
				}
				myButton.setText("Pause");
				
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
