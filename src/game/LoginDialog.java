package game;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

//LoginDialog Ŭ������ �α����ϴ� �۾����� �̿�Ǵ� Dialog�̴�.

public class LoginDialog extends JDialog{
	private JTextField idTextField = null;
	private JTextField passwordTextField = null;
	private GameFrame gameFrame = null;
	
	// �ڵ� �������� ���� ������. (�������� �ʿ� X)
	private LoginSource loginSource = null;
	private ScorePanel scorePanel = null;
	
	// ������
	public LoginDialog(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.loginSource = gameFrame.getLoginSource();
		this.scorePanel = gameFrame.getScorePanel();
		
		// Dialog�� �Ӽ� ����
		setTitle("Sign In");
		setSize(250,150);
		
		setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		
		add(new JLabel("     ���̵� "));
		idTextField = new JTextField(10);
		add(idTextField);
		
		add(new JLabel(" ��й�ȣ "));
		passwordTextField = new JTextField(10);
		add(passwordTextField);
		
		JButton signinButton = new JButton("Sign In");
		signinButton.addActionListener(new getNicknameActionListener());
		add(signinButton);
		
		setVisible(true);
    }
	
	// ��ư�� ������ �α����� �ȴ�.
	private class getNicknameActionListener implements ActionListener {
		// ��ư�� ���� �� id, password�� ������ �޾� nickname�� ��´�.
		public void actionPerformed(ActionEvent e) {
			String nickname = loginSource.login(gameFrame, idTextField.getText().trim(), passwordTextField.getText().trim());
			scorePanel.loginSuccess(nickname);
		}
	}
}