package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

// ScorePanel Ŭ������ ������ ������ �����Ѵ�. �� �� ������ ������� ǥ�����ش�.

public class ScorePanel extends JPanel {
	private int score = 0;
	private GameFrame gameFrame = null;
	private JLabel nickname = new JLabel("");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private JLabel label[] = new JLabel[20];

	// ScorePanel �ȿ��� ��ġ�����ڰ� �޶�� ��
	private NorthPanel northPanel = new NorthPanel(); 
	private CenterPanel centerPanel = new CenterPanel();
	
	// scorePanel�� ���� �� �̹����� BorderLayout�̱⿡ paintComponent���� �����Ͽ� �׸���
	class NorthPanel extends JPanel {
		private ImageIcon icon = new ImageIcon("ScorePanel.jpg");
		private Image img = icon.getImage();
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
	
	// scorePanel�� ���� �� �̹����� BorderLayout�̱⿡ paintComponent���� �����Ͽ� �׸���
	public class CenterPanel extends JPanel {
		private ImageIcon icon = new ImageIcon("ScorePanel.jpg");
		private Image img = icon.getImage();

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, northPanel.getHeight(), 272, 300, this);
		}
		
		// ������ �� ���׶�� ǥ��
		public void printCircle(Graphics g, int index) {
			g.setColor(Color.RED);
			if(index <= 9)
				g.drawOval(22, 81+22*index, 13, 13);
			else
				g.drawOval(153, 81+22*(index-10), 13, 13);
			g.setColor(Color.BLACK);
		}
		
		// Ʋ���� �� ���� ǥ��
		public void printSlash(Graphics g, int index) {
			g.setColor(Color.RED);
			if(index <= 9)
				g.drawLine(22, 81+22*index, 22+13, 81+22*index+13);
			else
				g.drawLine(153, 81+22*(index-10), 153+13, 81+22*(index-10)+13);
			g.setColor(Color.BLACK);
		}
	}
	
	// getter �Լ�
	public int getScore() {return score;}
	public int getLabelLength() {return label.length;}
	public JLabel getLabel(int index) {return label[index];}
	public String getNickname() {return nickname.getText();}
	public JPanel getCenterPanel() {return centerPanel;}
	
	// ������
	public ScorePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		
		// Panel�� �Ӽ�����
		setLayout(new BorderLayout());

		northPanel.setLayout(new FlowLayout());
		northPanel.add(nickname);
		northPanel.add(new JLabel("����"));
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
	
	// �����߿��� score�� ������.
	public void hideScoreLabel() {
		scoreLabel.setText("���� ��...");
	}
	
	// ������ ������ score�� �����Ѵ�.
	public void openScoreLabel() {
		scoreLabel.setText(Integer.toString(score));
	}
	
	// �α����� �����Ѵٸ� ���� ���� �г����� ǥ���Ѵ�. 
	public void loginSuccess(String name) {
		nickname.setText(name);
	}
	
	// GameGround���� �ܾ ���߸� scorePanel�� �ش� �ܾ ��µȴ�.
	public void printTextScorePanel(String text, int index, boolean success) {
		if(success) {
			// ���� ���� �ؽ�Ʈ�� ���������� ��µȴ�.
			centerPanel.printCircle(getGraphics(), index);
			label[index].setForeground(Color.BLACK);
			label[index].setText(text);
		}
		else {
			// Ʋ�� ���� �ؽ�Ʈ�� ���������� ��µȴ�.
			centerPanel.printSlash(getGraphics(), index);
			label[index].setForeground(Color.RED);
			label[index].setText(text);
		}
	}
	
	// ���� �����Լ�. �ܾ ���߸� ������ �����Ѵ�.
	public void increase() {
		score += 5;
	}
}
