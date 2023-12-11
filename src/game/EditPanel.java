package game;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

// EditPanel 클래스는 플레이어의 정보를 전달한다. 해당 패널에서는 플레이어 검색, 자신의 코멘트 설정이 가능하다.

public class EditPanel extends JPanel {
	private GameFrame gameFrame = null;
	
	// 코드 가독성을 위한 변수들. (실질적인 필요 X)
	private RankingSource rankingSource = null;
	
	// ActionListener에서 이용하기 위한 내부 변수 값
	private JTextField wordInput = new JTextField(8);
	private Label nicknameLabel = new Label("                                                            ");
	private Label rankLabel = new Label("                                                            ");
	private Label topScoreLabel = new Label("                                                            ");
	private Label playTimeLabel = new Label("                                                            ");
	private Label commentLabel = new Label("                                                            ");
	
	// 생성자
	public EditPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		rankingSource = gameFrame.getRankingSource();
		//this.rankingSource = rankingSource;
		
		// Panel의 속성설정
		setLayout(new FlowLayout(FlowLayout.CENTER));
		//setBackground(Color.CYAN);
		add(wordInput);
		
		JButton findNicknameButton = new JButton("Find");
		findNicknameButton.addActionListener(new findNicknameActionListener());
		add(findNicknameButton);
		
		JButton commentRenewButton = new JButton("Comment");
		commentRenewButton.addActionListener(new commentRenewActionListener());
		add(commentRenewButton);
		
		add(nicknameLabel);
		add(rankLabel);
		add(topScoreLabel);
		add(playTimeLabel);
		add(commentLabel);
	}
	
	// EditPanel 새로고침 함수
	public void changeAssignmentInformation(String wantToFindNickname) {
		nicknameLabel.setText(rankingSource.getNickname(wantToFindNickname)+" 의 성적표");
		rankLabel.setText("랭킹: "+rankingSource.getRank(wantToFindNickname)+"등");
		topScoreLabel.setText("최고점수: "+rankingSource.getTopScore(wantToFindNickname)+"점");
		playTimeLabel.setText("플레이 타임: "+rankingSource.getPlayTime(wantToFindNickname)+"회");
		commentLabel.setText("코멘트: "+rankingSource.getComment(wantToFindNickname));
	}
	
	// 원하는 닉네임의 정보 출력
	private class findNicknameActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String wantToFindNickname = wordInput.getText();
			// 예외처리: 정보 검색을 한 닉네임이 데이터베이스에 존재하지 않는 경우
			if(!rankingSource.getAccountData().containsKey(wantToFindNickname)) {
				JOptionPane.showMessageDialog(gameFrame, "올바른 닉네임을 입력해주세요");
				return;
			}
			changeAssignmentInformation(wantToFindNickname);
		}
	}
	
	// 자신의 코멘트를 정보창에 저장
	private class commentRenewActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String nickname = gameFrame.getScorePanel().getNickname();
			// 예외처리: 코멘트를 저장하려는데 저장할 계정이 없는 경우
			if(!rankingSource.getAccountData().containsKey(nickname)) {
				JOptionPane.showMessageDialog(gameFrame, "로그인을 해주세요");
				return;
			}
			rankingSource.setComment(nickname, wordInput.getText());
			changeAssignmentInformation(nickname);
			rankingSource.renewRankingSource();
		}
	}
}
