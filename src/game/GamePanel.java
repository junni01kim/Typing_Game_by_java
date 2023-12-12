package game;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

// GamePanel Ŭ������ �г� ���ο� ���� �� �гε��� �����Ѵ�.

public class GamePanel extends JPanel {
	private GameFrame gameFrame = null;
	private EditPanel editPanel = null;
	private JSplitPane hPane = new JSplitPane();
	private JSplitPane vPane = new JSplitPane();
	
	// �ڵ� �������� ���� ������. (�������� �ʿ� X)
	private ScorePanel scorePanel = null;
	private GameGround gameGround = null;
		
	// getter �Լ�
	public EditPanel getEditPanel() {return editPanel;}
	
	// ������
	public GamePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.scorePanel = gameFrame.getScorePanel();
		this.gameGround = gameFrame.getGameGround();
		editPanel = new EditPanel(gameFrame);
		
		// Panel�� �Ӽ�����
		setBackground(Color.YELLOW);
		setLayout(new BorderLayout());
	
		splitPanel();
	}
	
	private void splitPanel() {
		// ���� �г� ���� �� �Ӽ� ����
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(500);
		add(hPane);
		
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setDividerLocation(300);
		vPane.setDividerSize(0);
		
		vPane.setTopComponent(scorePanel); 
		vPane.setBottomComponent(editPanel);
		hPane.setRightComponent(vPane);
		hPane.setLeftComponent(gameGround);
		hPane.setDividerSize(0);
	}
	
}