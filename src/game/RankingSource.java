package game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

// RankingSource 클래스는 account.txt를 통해 계정들을 관리하는 클래스이다.

public class RankingSource {
	private HashMap<String,Account> accountData = new HashMap<>();
	private String lineOfAccount;
	private StringTokenizer st;
	
	// 생성자: 해시함수 저장 함수. key는 nickname이다.
	public RankingSource() {
		try {
			Scanner scanner = new Scanner(new FileReader("ranking.txt"));
			while(scanner.hasNext()) {
				lineOfAccount = scanner.nextLine();
				st = new StringTokenizer(lineOfAccount,"/");
				
				String nickname = st.nextToken();
				int rank = Integer.parseInt(st.nextToken());
				int topScore = Integer.parseInt(st.nextToken());
				int playTime = Integer.parseInt(st.nextToken());
				String comment = st.nextToken();
				
				accountData.put(nickname,new Account(nickname,rank,topScore,playTime,comment));
			}
		} catch(FileNotFoundException e) {
			System.out.println("파일 없어요");
			System.exit(0);
		}
	}
	
	public void changeRanking(String nickname) {
		int newTopScore = accountData.get(nickname).getTopScore();
		
		Set<String> keys = accountData.keySet();
		Iterator<String> it = keys.iterator();
		
		accountData.get(nickname).setRank(accountData.size());
		
		while(it.hasNext()) {
			String key = it.next();
			int value = accountData.get(key).getTopScore();
			if(newTopScore>value) {
				// 두 등수가 같다면(원래 nickname의 등수가 더 높았다)
				if(accountData.get(key).getRank() == accountData.get(nickname).getRank())
					continue;
				accountData.get(key).setRank(accountData.get(key).getRank()+1);
				accountData.get(nickname).setRank(accountData.get(nickname).getRank()-1);
			}
			
		}
		renewRankingSource();
	}
	
	// 데이터베이스 갱신 함수. ranking.txt를 초기화한다.
	public void renewRankingSource() {
		try {
			FileWriter fout = new FileWriter("ranking.txt");
			
			Set<String> keys = accountData.keySet();
			Iterator<String> it = keys.iterator();
			while(it.hasNext()) {
				String key = it.next();
				Account account = accountData.get(key);
				fout.write(account.getNickname()+"/"+account.getRank()+"/"+account.getTopScore()+"/"+account.getPlayTime()+"/"+account.getComment()+"\n");
			}
			fout.close();
		} catch (IOException e) {
			System.out.println("파일 없어요");
			System.exit(0);
		}
	}
	
	// setter 함수
	public void setNickname(String wantToFindNickname, String nickname) {accountData.get(wantToFindNickname).setNickname(nickname);}
	public void setRank(String wantToFindNickname, int rank) {accountData.get(wantToFindNickname).setRank(rank);}
	public void setTopScore(String wantToFindNickname, int topScore) {accountData.get(wantToFindNickname).setTopScore(topScore);}
	public void setPlayTime(String wantToFindNickname, int playTime) {accountData.get(wantToFindNickname).setPlayTime(playTime);}
	public void setComment(String wantToFindNickname, String comment) {accountData.get(wantToFindNickname).setComment(comment);}
	
	// getter 함수
	public HashMap<String,Account> getAccountData() {return accountData;}
	public String getNickname(String wantToFindNickname) {return accountData.get(wantToFindNickname).getNickname();}
	public int getRank(String wantToFindNickname) {return accountData.get(wantToFindNickname).getRank();}
	public int getTopScore(String wantToFindNickname) {return accountData.get(wantToFindNickname).getTopScore();}
	public int getPlayTime(String wantToFindNickname) {return accountData.get(wantToFindNickname).getPlayTime();}
	public String getComment(String wantToFindNickname) {return accountData.get(wantToFindNickname).getComment();}
}
