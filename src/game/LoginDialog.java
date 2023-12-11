package game;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

//LoginDialog 클래스는 로그인하는 작업에서 이용되는 Dialog이다.

public class LoginDialog extends JDialog{
	private JTextField idTextField = null;
	private JTextField passwordTextField = null;
	private GameFrame gameFrame = null;
	
	// 코드 가독성을 위한 변수들. (실질적인 필요 X)
	private LoginSource loginSource = null;
	private ScorePanel scorePanel = null;
	
	// 생성자
	public LoginDialog(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.loginSource = gameFrame.getLoginSource();
		this.scorePanel = gameFrame.getScorePanel();
		
		// Dialog의 속성 설정
		setTitle("Sign In");
		setSize(250,150);
		
		setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		
		add(new JLabel("     아이디 "));
		idTextField = new JTextField(10);
		add(idTextField);
		
		add(new JLabel(" 비밀번호 "));
		passwordTextField = new JTextField(10);
		add(passwordTextField);
		
		JButton signinButton = new JButton("Sign In");
		signinButton.addActionListener(new getNicknameActionListener());
		add(signinButton);
		
		setVisible(true);
    }
	
	// 버튼을 누르면 로그인이 된다.
	private class getNicknameActionListener implements ActionListener {
		// 버튼을 누를 시 id, password의 정보를 받아 nickname을 얻는다.
		public void actionPerformed(ActionEvent e) {
			String nickname = loginSource.login(gameFrame, idTextField.getText().trim(), passwordTextField.getText().trim());
			scorePanel.loginSuccess(nickname);
		}
	}
}