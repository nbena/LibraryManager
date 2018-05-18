/*  LibraryManager
    Copyright (C) 2018 nbena

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
    */

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
