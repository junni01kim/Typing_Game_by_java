package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

// ScorePanel 클래스는 게임의 점수를 관리한다. 몇 번 문제를 맞췄는지 표시해준다.

public class ScorePanel extends JPanel {
	private int score = 0;
	private GameFrame gameFrame = null;
	private JLabel nickname = new JLabel("");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private JLabel label[] = new JLabel[20];

	// ScorePanel 안에서 배치관리자가 달라야 함
	private NorthPanel northPanel = new NorthPanel(); 
	private CenterPanel centerPanel = new CenterPanel();
	
	// scorePanel에 들어가야 할 이미지가 BorderLayout이기에 paintComponent에서 분할하여 그리기
	class NorthPanel extends JPanel {
		private ImageIcon icon = new ImageIcon("ScorePanel.jpg");
		private Image img = icon.getImage();
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
	
	// scorePanel에 들어가야 할 이미지가 BorderLayout이기에 paintComponent에서 분할하여 그리기
	public class CenterPanel extends JPanel {
		private ImageIcon icon = new ImageIcon("ScorePanel.jpg");
		private Image img = icon.getImage();

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, northPanel.getHeight(), 272, 300, this);
		}
		
		// 정답일 때 동그라미 표시
		public void printCircle(Graphics g, int index) {
			g.setColor(Color.RED);
			if(index <= 9)
				g.drawOval(22, 81+22*index, 13, 13);
			else
				g.drawOval(153, 81+22*(index-10), 13, 13);
			g.setColor(Color.BLACK);
		}
		
		// 틀렸을 때 빗금 표시
		public void printSlash(Graphics g, int index) {
			g.setColor(Color.RED);
			if(index <= 9)
				g.drawLine(22, 81+22*index, 22+13, 81+22*index+13);
			else
				g.drawLine(153, 81+22*(index-10), 153+13, 81+22*(index-10)+13);
			g.setColor(Color.BLACK);
		}
	}
	
	// getter 함수
	public int getScore() {return score;}
	public int getLabelLength() {return label.length;}
	public JLabel getLabel(int index) {return label[index];}
	public String getNickname() {return nickname.getText();}
	public JPanel getCenterPanel() {return centerPanel;}
	
	// 생성자
	public ScorePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		
		// Panel의 속성설정
		setLayout(new BorderLayout());

		northPanel.setLayout(new FlowLayout());
		northPanel.add(nickname);
		northPanel.add(new JLabel("점수"));
		northPanel.add(scoreLabel);
		
		centerPanel.setLayout(null);
		for(int i=0;i<label.length;i++) {
			label[i] = new JLabel("");
			label[i].setSize(100,20);
			if(i <= 9) {
				label[i].setLocation(40, 46+22*i);
			}
			else {
				label[i].setLocation(175, 46+22*(i-10));
			}
			centerPanel.add(label[i]);
		}
		
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}
	
	// 게임중에는 score을 가린다.
	public void hideScoreLabel() {
		scoreLabel.setText("시험 중...");
	}
	
	// 게임이 끝나면 score을 공개한다.
	public void openScoreLabel() {
		scoreLabel.setText(Integer.toString(score));
	}
	
	// 로그인을 성공한다면 점수 옆에 닉네임을 표시한다. 
	public void loginSuccess(String name) {
		nickname.setText(name);
	}
	
	// GameGround에서 단어를 맞추면 scorePanel에 해당 단어가 출력된다.
	public void printTextScorePanel(String text, int index, boolean success) {
		if(success) {
			// 맞춘 경우는 텍스트가 검정색으로 출력된다.
			centerPanel.printCircle(getGraphics(), index);
			label[index].setForeground(Color.BLACK);
			label[index].setText(text);
		}
		else {
			// 틀린 경우는 텍스트가 빨간색으로 출력된다.
			centerPanel.printSlash(getGraphics(), index);
			label[index].setForeground(Color.RED);
			label[index].setText(text);
		}
	}
	
	// 점수 증가함수. 단어를 맞추면 점수가 증가한다.
	public void increase() {
		score += 5;
	}
}
