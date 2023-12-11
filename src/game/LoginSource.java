package game;

import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

// LoginSource Ŭ������ account.txt�� ���� �������� �����ϴ� Ŭ�����̴�.

public class LoginSource {
	private HashMap<String, LoginAccount> loginAccount = new HashMap<>();
	private String lineOfAccount;
	private GameFrame gameFrame = null;
	
	// ������: �ؽ��Լ� ���� �Լ�. key�� id�̴�.
	public LoginSource(GameFrame gameFrame) {
		try {
			this.gameFrame = gameFrame;
			Scanner scanner = new Scanner(new FileReader("account.txt"));
			while(scanner.hasNext()) {
				String id = scanner.next().trim();
				String password = scanner.next().trim();
				String nickname = scanner.next().trim(); 

				loginAccount.put(id, new LoginAccount(id, password, nickname));
			}
		} catch(FileNotFoundException e) {
			System.out.println("���� �����");
			System.exit(0);
		}
	}
	
	// �α��� �Լ�: id�� password�� �޾� ���� ���� ���θ� Ȯ���Ѵ�.
	public String login(Component parent, String id, String password) {
		if(loginAccount.containsKey(id)&&loginAccount.get(id).getPassword().equals(password)) {
			JOptionPane.showMessageDialog(parent, "�α��� �Ǿ����ϴ�!");
			return loginAccount.get(id).getNickname();
		}
		// ����ó��: id Ȥ�� password �� �ϳ��� ��ġ���� �ʴ´ٸ� �������� ���
		JOptionPane.showMessageDialog(parent, "���̵� ã�� �� �����ϴ�");
		return "";
	}
	
	public void create(Component parent, String id, String password, String nickname) {
		try {
			// ����ó��: id�� �̹� �����Ѵٸ� �������� ���
			if(loginAccount.containsKey(id)) {
				JOptionPane.showMessageDialog(parent, "�̹� �����ϴ� id�Դϴ�");
				return;
			}
			// account.txt�� ����
			FileWriter fout = new FileWriter("account.txt", true);
			fout.write(id+" "+password+" "+nickname+"\n");
			fout.close();
			
			// nickname�� ��� rank.txt�� ����(RankingSource.renewRankingSource())
			HashMap<String, Account> accountData = gameFrame.getRankingSource().getAccountData();
			accountData.put(nickname, new Account(nickname,0,0,0,"ȯ���մϴ�."));
			gameFrame.getRankingSource().renewRankingSource();
			
		} catch (IOException e) {
			System.out.println("���� �����");
			System.exit(0);
		}
	}
}
