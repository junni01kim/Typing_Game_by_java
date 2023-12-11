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

// LoginSource 클래스는 account.txt를 통해 계정들을 관리하는 클래스이다.

public class LoginSource {
	private HashMap<String, LoginAccount> loginAccount = new HashMap<>();
	private String lineOfAccount;
	private GameFrame gameFrame = null;
	
	// 생성자: 해시함수 저장 함수. key는 id이다.
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
			System.out.println("파일 없어요");
			System.exit(0);
		}
	}
	
	// 로그인 함수: id와 password를 받아 꼐정 존재 여부를 확인한다.
	public String login(Component parent, String id, String password) {
		if(loginAccount.containsKey(id)&&loginAccount.get(id).getPassword().equals(password)) {
			JOptionPane.showMessageDialog(parent, "로그인 되었습니다!");
			return loginAccount.get(id).getNickname();
		}
		// 예외처리: id 혹은 password 중 하나가 일치하지 않는다면 오류문구 출력
		JOptionPane.showMessageDialog(parent, "아이디를 찾을 수 없습니다");
		return "";
	}
	
	public void create(Component parent, String id, String password, String nickname) {
		try {
			// 예외처리: id가 이미 존재한다면 오류문구 출력
			if(loginAccount.containsKey(id)) {
				JOptionPane.showMessageDialog(parent, "이미 존재하는 id입니다");
				return;
			}
			// account.txt에 갱신
			FileWriter fout = new FileWriter("account.txt", true);
			fout.write(id+" "+password+" "+nickname+"\n");
			fout.close();
			
			// nickname을 얻어 rank.txt를 갱신(RankingSource.renewRankingSource())
			HashMap<String, Account> accountData = gameFrame.getRankingSource().getAccountData();
			accountData.put(nickname, new Account(nickname,0,0,0,"환영합니다."));
			gameFrame.getRankingSource().renewRankingSource();
			
		} catch (IOException e) {
			System.out.println("파일 없어요");
			System.exit(0);
		}
	}
}
