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

// GameFrame Ŭ������ ���� â�� �гε��� �����ϴ� Ŭ�����̴�.
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
	
	// ���� ���� �Լ�
	private void makeToolbar() {
		JToolBar bar = new JToolBar();
		getContentPane().add(bar, BorderLayout.NORTH);
		
		// ����&���� ��ư: ������ ����
		bPlay.addActionListener(new PlayActionListener());
		bar.add(bPlay);
		
		// �Ͻ����� ��ư: ������ �Ͻ�����
		bPause.addActionListener(new PauseActionListener());
		bar.add(bPause);
		
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
				
				// ScorePanel�� �ۼ��� �׸� ��ü ����(��׶��� ����)
				getScorePanel().getCenterPanel().repaint();
				
				backgroundClip.start();
				
				myButton.setText("Stop");
				if(bDiffculty.getText().equals("Easy"))
					gameGround.startRoundThread(1000);
				else
					gameGround.startRoundThread(500);
			}
			// Stop ��ư�� ������ Play�� �ȴ�.
			// Stop ��ư�� ��� �����带 �ѹ��� �����Ų��.
			else {
				// ����ó��: ���� �Ͻ�������� �Ͻ������� �����Ѵ�.
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
	
	// ȸ������ ��ư: PauseDialog�� �����Ѵ�.
	private class PauseActionListener implements ActionListener {
		JButton myButton;
		public void actionPerformed(ActionEvent e) {
			// ����ó��: �Ͻ������� ������ ���۵Ǿ߸� ����ȴ�.
			if(bPlay.getText().equals("Play"))
				return;
			
			myButton = (JButton)e.getSource();
			// Pause ��ư�� ������ Resume��ư�� �ǰ�
			// Pause ��ư�� ������ �Ͻ������Ѵ�.
			if(myButton.getText().equals("Pause")) {
				for(int i=0; i<gameGround.getWordThreadLength(); i++) {
					gameGround.getWordThread(i).pauseGame();
				}
				myButton.setText("Resume");
			}
			// Resume ��ư�� ������ ������ �簳 �ȴ�.
			else {
				for(int i=0; i<gameGround.getWordThreadLength(); i++) {
					gameGround.getWordThread(i).resumeGame();
				}
				myButton.setText("Pause");
				
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
