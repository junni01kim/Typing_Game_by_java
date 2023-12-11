package game;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

//TextSource 클래스는 word.txt를 통해 계정들을 관리하는 클래스이다.

public class TextSource {
	private Vector<String> wordVector = new Vector<String>(30000);
	
	// 생성자
	public TextSource() {
		try {
			Scanner scanner = new Scanner(new FileReader("words.txt"));
			while(scanner.hasNext()) {
				String word = scanner.nextLine();
				wordVector.add(word);
			}		
		} catch (FileNotFoundException e) {
			System.out.println("파일 없어요");
			System.exit(0);
		}	
	}
	public String next() {
		int n = wordVector.size();
		int index = (int)(Math.random()*n);
		return wordVector.get(index);
	}
}
