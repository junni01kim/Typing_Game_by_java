package game;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

// GamePanel 클래스는 패널 내부에 분할 될 패널들을 관리한다.

public class GamePanel extends JPanel {
	private GameFrame gameFrame = null;
	private EditPanel editPanel = null;
	private JSplitPane hPane = new JSplitPane();
	private JSplitPane vPane = new JSplitPane();
	
	// 코드 가독성을 위한 변수들. (실질적인 필요 X)
	private ScorePanel scorePanel = null;
	private GameGround gameGround = null;
		
	// getter 함수
	public EditPanel getEditPanel() {return editPanel;}
	
	// 생성자
	public GamePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.scorePanel = gameFrame.getScorePanel();
		this.gameGround = gameFrame.getGameGround();
		editPanel = new EditPanel(gameFrame);
		
		// Panel의 속성설정
		setBackground(Color.YELLOW);
		setLayout(new BorderLayout());
	
		splitPanel();
	}
	
	private void splitPanel() {
		// 분할 패널 관리 및 속성 설정
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