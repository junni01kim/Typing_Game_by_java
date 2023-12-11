package game;

// LoginAccount 클래스는 플레이어의 개인정보를 관리

public class LoginAccount {
	private String nickname;
	private String id;
	private String password;
	
	// setter 함수
	public void setNickname(String nickname) {this.nickname = nickname;}
	public void setId(String id) {this.id = id;}
	public void setPassword(String password) {this.password = password;}
	
	// getter 함수
	public String getNickname() {return nickname;}
	public String getId() {return id;}
	public String getPassword() {return password;}
	
	// 생성자
	public LoginAccount(String id, String password, String nickname) {
		this.id = id;
		this.password = password;
		this.nickname = nickname;
	}
}
