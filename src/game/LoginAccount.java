package game;

// LoginAccount Ŭ������ �÷��̾��� ���������� ����

public class LoginAccount {
	private String nickname;
	private String id;
	private String password;
	
	// setter �Լ�
	public void setNickname(String nickname) {this.nickname = nickname;}
	public void setId(String id) {this.id = id;}
	public void setPassword(String password) {this.password = password;}
	
	// getter �Լ�
	public String getNickname() {return nickname;}
	public String getId() {return id;}
	public String getPassword() {return password;}
	
	// ������
	public LoginAccount(String id, String password, String nickname) {
		this.id = id;
		this.password = password;
		this.nickname = nickname;
	}
}
