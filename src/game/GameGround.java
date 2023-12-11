package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameGround extends JPanel{
	private ImageIcon icon = new ImageIcon("GamePanel.jpg");
	private Image img = icon.getImage();
	private GameFrame gameFrame = null;
	private ScorePanel scorePanel = null;
	private TextSource textSource = null;
	
	// 현재 단어들이 몇개 남았는지 알려주는 변수
	private int deadLabelCount;

	// 코드 가독성을 위한 변수들. (실질적인 필요 X)
	private GameGround gameGround = null;
	private RankingSource rankingSource = null;
	private JLabel [] label = new JLabel[20];
	private JTextField textInput = new JTextField(20);
	private WordThread th [] = new WordThread[label.length];
	private RoundThread roundThread = null;
	
	
	// getter 함수
	public int getLabelLength() {return label.length;}
	public RoundThread getRoundThread() {return roundThread;}
	public int getWordThreadLength() {return th.length;}
	public WordThread getWordThread(int index) {return th[index];}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	// 생성자
	public GameGround(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		
		this.gameGround = this;
		this.scorePanel = gameFrame.getScorePanel();
		this.textSource = gameFrame.getTextSource();
		this.rankingSource = gameFrame.getRankingSource();
		
		setLayout(null);
		
		// Panel의 속성설정
		textInput.setSize(450, 20);
		textInput.setLocation(30, 430);
		add(textInput);
		textInput.addActionListener(new TypingActionListener());		
	}
	
	// 게임 전체를 관리하는 스레드 구동 함수
	public void startRoundThread(int spawnSpeed) {
		roundThread = new RoundThread(spawnSpeed);
		roundThread.start();
	}
	
	// 게임 종료시 실행되야 할 코드 구동 함수
	public void endGame() {
		int score = scorePanel.getScore();
		String nickname = scorePanel.getNickname();
		gameFrame.getBPlay().setText("Play");
		if(!rankingSource.getAccountData().containsKey(nickname)) {
			JOptionPane.showMessageDialog(gameFrame, "계정이 없으면 랭킹 등록이 불가능합니다");
			return;
		}
		if(score > rankingSource.getTopScore(nickname)) {
			rankingSource.setTopScore(nickname, score);
			rankingSource.changeRanking(nickname);
		}
		rankingSource.setPlayTime(nickname, rankingSource.getPlayTime(nickname)+1);
		
		rankingSource.renewRankingSource();
	}
	
	// 모든 단어 스레드를 제거하는 함수
	public void deleteWordThread(int length) {
		for(int i=0;i<length;i++) {
			if(th[i]!=null) 
				th[i].delete();
		}
		scorePanel.openScoreLabel();
	}
	
	// 정답 체크 함수
	private class TypingActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField tf = (JTextField)e.getSource();
			String text = tf.getText();
			
			for(int i=0;i<label.length;i++) {
				if(text.equals(label[i].getText())) {
					scorePanel.printTextScorePanel(label[i].getText(), i, true);
					scorePanel.increase();
					th[i].delete();
					break;
				}
			}
			textInput.setText("");
		}
	}
	
	// 게임 전체를 관리하는 스레드
	public class RoundThread extends Thread {
		private int wordThreadStagingCounter = label.length;
		private int spawnSpeed;
		private boolean flag = false;
		private boolean pauseFlag = false;
		
		public void delete() {
			flag = true;
		}
		
		synchronized private void checkWait() {
			if(pauseFlag == true) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		// 생성자
		public RoundThread(int spawnSpeed) {
			this.spawnSpeed = spawnSpeed;
			deadLabelCount = 0;
			
			// 게임 중에는 점수가 비공개 처리 된다.
			scorePanel.hideScoreLabel();
			
			// 각 단어의 Label 초기화 및 스레드 구동
			for(int i=0;i<label.length;i++) {
				String word = textSource.next();
				label[i] = new JLabel(word);
				label[i].setForeground(Color.WHITE);
				label[i].setSize(100,20);

				label[i].setLocation((int)(5+Math.random()*400),10);
				th[i] = new WordThread(i);
			}
		}
		
		@Override
		public void run() {
			// 스레드의 개수는 Label의 개수를 따라간다.
			try {
				for(int i=0;i<th.length;i++) {
					if(flag == true) {
						deleteWordThread(i);
						return;
					}
					gameGround.add(label[i]);
					th[i].start();
					
					// 단어는 1초에서 3초 사이에 하나씩 떨어진다.
					sleep((int)(spawnSpeed+Math.random()*2*spawnSpeed));
					
					// 게임진행이 절반이 되면 미니게임이 시작된다.
					if(i==th.length/2) {
						new MiniGame();
					}
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	// 각 단어의 Label에 적용되는 스레드
	public class WordThread extends Thread {
		int index;
		// 스레드 제거를 위한 flag
		private boolean deleteFlag = false;
		private boolean pauseFlag = false;
		
		// 생성자
		public WordThread(int index) {
			this.index = index;
		}
		
		// 스레드 제거 함수
		public void delete() {
			deadLabelCount++;
			deleteFlag = true;
		}

		// 스레드 일시정지
		public void pauseGame() {
			pauseFlag = true;
		}
		
		// 스레드 재개
		synchronized public void resumeGame() {
			pauseFlag = false;
			this.notify();
		}
		
		// 스레드 일시정지 체크함수
		synchronized private void checkWait() {
			if(pauseFlag == true) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		@Override
		public void run() {
			while(true) {
				checkWait();
				if(!pauseFlag) {
					try {
						// Label은 y좌표 380까지 내려오면 삭제된다. (테마가 시험 문제라 감점은 없습니다.)
						if(label[index].getY()>380) {
							scorePanel.printTextScorePanel(label[index].getText(), index, false);
							delete();
						}
						
						label[index].setLocation(label[index].getX(), label[index].getY()+10);
						
						// 삭제되는 경우 실행되는 코드
						if(deleteFlag == true) {
							Container c = label[index].getParent();
							c.remove(label[index]);
							c.repaint();
							
							// 마지막 스레드가 삭제될 때만 진행
							if(deadLabelCount == 20) {
								// 비공개된 점수 공개
								scorePanel.openScoreLabel();
								endGame();
							}
							return;
						}
						
						// 1초에 한번씩 드롭
						sleep(1000);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		}
	}
}