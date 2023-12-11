package game;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;

// MiniGame 클래스는 게임 중 발생하는 미니게임 Dialog를 관리하는 클래스이다.

public class MiniGame extends JDialog {
	private MiniGame miniGame = this;
	private int counter = 20;
	private JLabel counterLabel = new JLabel("          "+Integer.toString(counter)+"          ");; 
	
	// 생성자
	public MiniGame() {
		// Panel의 속성설정
		setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		setTitle("긴급 상황!");
		setSize(250,150);
		
		add(new JLabel("문제를 잘못 적었다."));
		add(new JLabel("방향키를 흔들어 글자를 지우자!"));
		add(counterLabel);
		add(new JLabel("뱡향키(←,→)를 연타해주세요!"));
		
		addKeyListener(new ArrowKeyListener());
		setVisible(true);
	}
	
	// 상호작용 이벤트. 방향키를 번갈아가며 연타한다. counter가 감소한다.
	private class ArrowKeyListener extends KeyAdapter {
		// 좌우 키 구분을 위한 변수
		private boolean toggle = false;
		@Override
		public void keyPressed(KeyEvent e) {
			if(counter<=0) {
				Container contentPane = (Container)e.getSource();
				contentPane.setVisible(false);
			}
			
			if(e.getKeyCode()==KeyEvent.VK_LEFT&&toggle==false) {
				counter--;
				toggle = true;
				counterLabel.setText("          "+Integer.toString(counter)+"          ");
			}
			
			if(e.getKeyCode()==KeyEvent.VK_RIGHT&&toggle==true) {
				counter--;
				toggle = false;
				counterLabel.setText("          "+Integer.toString(counter)+"          ");
			}
		}
	}
}
