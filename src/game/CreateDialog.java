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

// CreateDialog 클래스는 계정을 생성하는 작업에서 이용되는 Dialog이다.

public class CreateDialog extends JDialog{
	private JTextField idTextField = null;
	private JTextField passwordTextField = null;
	private JTextField nicknameTextField = null;
	private GameFrame gameFrame = null;
	
	// 코드 가독성을 위한 변수들. (실질적인 필요 X)
	private LoginSource loginSource = null;
	private ScorePanel scorePanel = null;
	
	// 생성자
	public CreateDialog(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		this.loginSource = gameFrame.getLoginSource();
		this.scorePanel = gameFrame.getScorePanel();
		
		// Dialog의 속성 설정
		setTitle("Create Account");
		setSize(250,180);
		setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		
		add(new JLabel("     아이디 "));
		idTextField = new JTextField(10);
		add(idTextField);
		
		add(new JLabel(" 비밀번호 "));
		passwordTextField = new JTextField(10);
		add(passwordTextField);
		
		add(new JLabel("     닉네임 "));
		nicknameTextField = new JTextField(10);
		add(nicknameTextField);
		
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new createActionListener());
		add(createButton);
		
		setVisible(true);
    }
	
	// 버튼을 누르면 계정이 생성된다.
	private class createActionListener implements ActionListener {
		// 버튼을 누를 시 id, password, nickname의 정보를 받아 LoginSource로 전송한다.
		public void actionPerformed(ActionEvent e) {
			loginSource.create(gameFrame, idTextField.getText().trim(), passwordTextField.getText().trim(), nicknameTextField.getText().trim());
			gameFrame.setLoginSource(new LoginSource(gameFrame));
			JOptionPane.showMessageDialog(gameFrame, "계정이 생성되었습니다!");
		}
	}
}