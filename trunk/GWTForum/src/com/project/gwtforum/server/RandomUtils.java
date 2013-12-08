package com.project.gwtforum.server;

import java.util.Random;

import com.project.gwtforum.shared.Constants;

public class RandomUtils {

	public static String getRandomString(int lenght) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		
		for (int i = 0; i < lenght; i++) {
			char c = Constants.CHARS[random.nextInt(Constants.CHARS.length)];
			sb.append(c);
		}
		
		return sb.toString();
	}
}
