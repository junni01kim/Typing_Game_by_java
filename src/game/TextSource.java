package game;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

//TextSource Ŭ������ word.txt�� ���� �������� �����ϴ� Ŭ�����̴�.

public class TextSource {
	private Vector<String> wordVector = new Vector<String>(30000);
	
	// ������
	public TextSource() {
		try {
			Scanner scanner = new Scanner(new FileReader("words.txt"));
			while(scanner.hasNext()) {
				String word = scanner.nextLine();
				wordVector.add(word);
			}		
		} catch (FileNotFoundException e) {
			System.out.println("���� �����");
			System.exit(0);
		}	
	}
	public String next() {
		int n = wordVector.size();
		int index = (int)(Math.random()*n);
		return wordVector.get(index);
	}
}
