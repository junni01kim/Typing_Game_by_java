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
	
	// ���� �ܾ���� � ���Ҵ��� �˷��ִ� ����
	private int deadLabelCount;

	// �ڵ� �������� ���� ������. (�������� �ʿ� X)
	private GameGround gameGround = null;
	private RankingSource rankingSource = null;
	private JLabel [] label = new JLabel[20];
	private JTextField textInput = new JTextField(20);
	private WordThread th [] = new WordThread[label.length];
	private RoundThread roundThread = null;
	
	
	// getter �Լ�
	public int getLabelLength() {return label.length;}
	public RoundThread getRoundThread() {return roundThread;}
	public int getWordThreadLength() {return th.length;}
	public WordThread getWordThread(int index) {return th[index];}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	// ������
	public GameGround(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		
		this.gameGround = this;
		this.scorePanel = gameFrame.getScorePanel();
		this.textSource = gameFrame.getTextSource();
		this.rankingSource = gameFrame.getRankingSource();
		
		setLayout(null);
		
		// Panel�� �Ӽ�����
		textInput.setSize(450, 20);
		textInput.setLocation(30, 430);
		add(textInput);
		textInput.addActionListener(new TypingActionListener());		
	}
	
	// ���� ��ü�� �����ϴ� ������ ���� �Լ�
	public void startRoundThread(int spawnSpeed) {
		roundThread = new RoundThread(spawnSpeed);
		roundThread.start();
	}
	
	// ���� ����� ����Ǿ� �� �ڵ� ���� �Լ�
	public void endGame() {
		int score = scorePanel.getScore();
		String nickname = scorePanel.getNickname();
		gameFrame.getBPlay().setText("Play");
		if(!rankingSource.getAccountData().containsKey(nickname)) {
			JOptionPane.showMessageDialog(gameFrame, "������ ������ ��ŷ ����� �Ұ����մϴ�");
			return;
		}
		if(score > rankingSource.getTopScore(nickname)) {
			rankingSource.setTopScore(nickname, score);
			rankingSource.changeRanking(nickname);
		}
		rankingSource.setPlayTime(nickname, rankingSource.getPlayTime(nickname)+1);
		
		rankingSource.renewRankingSource();
	}
	
	// ��� �ܾ� �����带 �����ϴ� �Լ�
	public void deleteWordThread(int length) {
		for(int i=0;i<length;i++) {
			if(th[i]!=null) 
				th[i].delete();
		}
		scorePanel.openScoreLabel();
	}
	
	// ���� üũ �Լ�
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
	
	// ���� ��ü�� �����ϴ� ������
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
		
		// ������
		public RoundThread(int spawnSpeed) {
			this.spawnSpeed = spawnSpeed;
			deadLabelCount = 0;
			
			// ���� �߿��� ������ ����� ó�� �ȴ�.
			scorePanel.hideScoreLabel();
			
			// �� �ܾ��� Label �ʱ�ȭ �� ������ ����
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
			// �������� ������ Label�� ������ ���󰣴�.
			try {
				for(int i=0;i<th.length;i++) {
					if(flag == true) {
						deleteWordThread(i);
						return;
					}
					gameGround.add(label[i]);
					th[i].start();
					
					// �ܾ�� 1�ʿ��� 3�� ���̿� �ϳ��� ��������.
					sleep((int)(spawnSpeed+Math.random()*2*spawnSpeed));
					
					// ���������� ������ �Ǹ� �̴ϰ����� ���۵ȴ�.
					if(i==th.length/2) {
						new MiniGame();
					}
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	// �� �ܾ��� Label�� ����Ǵ� ������
	public class WordThread extends Thread {
		int index;
		// ������ ���Ÿ� ���� flag
		private boolean deleteFlag = false;
		private boolean pauseFlag = false;
		
		// ������
		public WordThread(int index) {
			this.index = index;
		}
		
		// ������ ���� �Լ�
		public void delete() {
			deadLabelCount++;
			deleteFlag = true;
		}

		// ������ �Ͻ�����
		public void pauseGame() {
			pauseFlag = true;
		}
		
		// ������ �簳
		synchronized public void resumeGame() {
			pauseFlag = false;
			this.notify();
		}
		
		// ������ �Ͻ����� üũ�Լ�
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
						// Label�� y��ǥ 380���� �������� �����ȴ�. (�׸��� ���� ������ ������ �����ϴ�.)
						if(label[index].getY()>380) {
							scorePanel.printTextScorePanel(label[index].getText(), index, false);
							delete();
						}
						
						label[index].setLocation(label[index].getX(), label[index].getY()+10);
						
						// �����Ǵ� ��� ����Ǵ� �ڵ�
						if(deleteFlag == true) {
							Container c = label[index].getParent();
							c.remove(label[index]);
							c.repaint();
							
							// ������ �����尡 ������ ���� ����
							if(deadLabelCount == 20) {
								// ������� ���� ����
								scorePanel.openScoreLabel();
								endGame();
							}
							return;
						}
						
						// 1�ʿ� �ѹ��� ���
						sleep(1000);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		}
	}
}