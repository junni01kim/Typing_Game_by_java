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

// EditPanel Ŭ������ �÷��̾��� ������ �����Ѵ�. �ش� �гο����� �÷��̾� �˻�, �ڽ��� �ڸ�Ʈ ������ �����ϴ�.

public class EditPanel extends JPanel {
	private GameFrame gameFrame = null;
	
	// �ڵ� �������� ���� ������. (�������� �ʿ� X)
	private RankingSource rankingSource = null;
	
	// ActionListener���� �̿��ϱ� ���� ���� ���� ��
	private JTextField wordInput = new JTextField(8);
	private Label nicknameLabel = new Label("                                                            ");
	private Label rankLabel = new Label("                                                            ");
	private Label topScoreLabel = new Label("                                                            ");
	private Label playTimeLabel = new Label("                                                            ");
	private Label commentLabel = new Label("                                                            ");
	
	// ������
	public EditPanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		rankingSource = gameFrame.getRankingSource();
		//this.rankingSource = rankingSource;
		
		// Panel�� �Ӽ�����
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
	
	// EditPanel ���ΰ�ħ �Լ�
	public void changeAssignmentInformation(String wantToFindNickname) {
		nicknameLabel.setText(rankingSource.getNickname(wantToFindNickname)+" �� ����ǥ");
		rankLabel.setText("��ŷ: "+rankingSource.getRank(wantToFindNickname)+"��");
		topScoreLabel.setText("�ְ�����: "+rankingSource.getTopScore(wantToFindNickname)+"��");
		playTimeLabel.setText("�÷��� Ÿ��: "+rankingSource.getPlayTime(wantToFindNickname)+"ȸ");
		commentLabel.setText("�ڸ�Ʈ: "+rankingSource.getComment(wantToFindNickname));
	}
	
	// ���ϴ� �г����� ���� ���
	private class findNicknameActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String wantToFindNickname = wordInput.getText();
			// ����ó��: ���� �˻��� �� �г����� �����ͺ��̽��� �������� �ʴ� ���
			if(!rankingSource.getAccountData().containsKey(wantToFindNickname)) {
				JOptionPane.showMessageDialog(gameFrame, "�ùٸ� �г����� �Է����ּ���");
				return;
			}
			changeAssignmentInformation(wantToFindNickname);
		}
	}
	
	// �ڽ��� �ڸ�Ʈ�� ����â�� ����
	private class commentRenewActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String nickname = gameFrame.getScorePanel().getNickname();
			// ����ó��: �ڸ�Ʈ�� �����Ϸ��µ� ������ ������ ���� ���
			if(!rankingSource.getAccountData().containsKey(nickname)) {
				JOptionPane.showMessageDialog(gameFrame, "�α����� ���ּ���");
				return;
			}
			rankingSource.setComment(nickname, wordInput.getText());
			changeAssignmentInformation(nickname);
			rankingSource.renewRankingSource();
		}
	}
}
