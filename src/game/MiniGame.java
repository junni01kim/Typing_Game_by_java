package game;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;

// MiniGame Ŭ������ ���� �� �߻��ϴ� �̴ϰ��� Dialog�� �����ϴ� Ŭ�����̴�.

public class MiniGame extends JDialog {
	private MiniGame miniGame = this;
	private int counter = 20;
	private JLabel counterLabel = new JLabel("          "+Integer.toString(counter)+"          ");; 
	
	// ������
	public MiniGame() {
		// Panel�� �Ӽ�����
		setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		setTitle("��� ��Ȳ!");
		setSize(250,150);
		
		add(new JLabel("������ �߸� ������."));
		add(new JLabel("����Ű�� ���� ���ڸ� ������!"));
		add(counterLabel);
		add(new JLabel("����Ű(��,��)�� ��Ÿ���ּ���!"));
		
		addKeyListener(new ArrowKeyListener());
		setVisible(true);
	}
	
	// ��ȣ�ۿ� �̺�Ʈ. ����Ű�� �����ư��� ��Ÿ�Ѵ�. counter�� �����Ѵ�.
	private class ArrowKeyListener extends KeyAdapter {
		// �¿� Ű ������ ���� ����
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
