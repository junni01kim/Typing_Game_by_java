package game;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// CreateDialog Ŭ������ ������ �����ϴ� �۾����� �̿�Ǵ� Dialog�̴�.

public class CreateDialog extends JDialog{
	private JTextField idTextField = null;
	private JTextField passwordTextField = null;
	private JTextField nicknameTextField = null;
	private GameFrame gameFrame = null;
	
	// �ڵ� �������� ���� ������. (�������� �ʿ� X)
	private LoginSource loginSource = null;
	private ScorePanel scorePanel = null;
	
	// ������
	public CreateDialog(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.loginSource = gameFrame.getLoginSource();
		this.scorePanel = gameFrame.getScorePanel();
		
		// Dialog�� �Ӽ� ����
		setTitle("Create Account");
		setSize(250,180);
		setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		
		add(new JLabel("     ���̵� "));
		idTextField = new JTextField(10);
		add(idTextField);
		
		add(new JLabel(" ��й�ȣ "));
		passwordTextField = new JTextField(10);
		add(passwordTextField);
		
		add(new JLabel("     �г��� "));
		nicknameTextField = new JTextField(10);
		add(nicknameTextField);
		
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new createActionListener());
		add(createButton);
		
		setVisible(true);
    }
	
	// ��ư�� ������ ������ �����ȴ�.
	private class createActionListener implements ActionListener {
		// ��ư�� ���� �� id, password, nickname�� ������ �޾� LoginSource�� �����Ѵ�.
		public void actionPerformed(ActionEvent e) {
			loginSource.create(gameFrame, idTextField.getText().trim(), passwordTextField.getText().trim(), nicknameTextField.getText().trim());
			gameFrame.setLoginSource(new LoginSource(gameFrame));
			JOptionPane.showMessageDialog(gameFrame, "������ �����Ǿ����ϴ�!");
		}
	}
}