package game;

// Account 클래스는 닉네임을 기준으로 플레이어 정보를 기록한다.
public class Account {
	private String nickname;
	private int rank;
	private int topScore;
	private int playTime;
	private String comment;
	
	// 생성자
	public Account(String nickname, int rank, int topScore, int playTime, String comment) {
		this.nickname = nickname;
		this.rank = rank;
		this.topScore = topScore;
		this.playTime = playTime;
		this.comment = comment;
	}
	
	// setter 함수
	public void setNickname(String nickname) {this.nickname = nickname;}
	public void setRank(int rank) {this.rank = rank;}
	public void setTopScore(int topScore) {this.topScore = topScore;}
	public void setPlayTime(int playTime) {this.playTime = playTime;}
	public void setComment(String comment) {this.comment = comment;}
	
	// getter 함수
	public String getNickname() {return nickname;}
	public int getRank() {return rank;}
	public int getTopScore() {return topScore;}
	public int getPlayTime() {return playTime;}
	public String getComment() {return comment;}
}
