package com.github.nbena.librarymanager.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class Hash {
	
	public static String hash(String input) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA3-512", new BouncyCastleProvider());
		md.update(input.getBytes());
		byte[] digest = md.digest();
		return Hex.toHexString(digest);
	}
	
	public static boolean check(String hashed, String toHash) throws NoSuchAlgorithmException{
		String hashedSecond = Hash.hash(toHash);
		return hashed.equals(hashedSecond);
	}

}
