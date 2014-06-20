package registration;

import java.security.MessageDigest;

public class HashGenerator {
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	private String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}

	/*
	 Given a string of hex byte values such as "24a26f", creates
	 a byte[] array of those values, one byte value -128..127
	 for each 2 chars.
	 (provided code)
	*/
	public static byte[] hexToArray(String hex) {
		byte[] result = new byte[hex.length()/2];
		for (int i=0; i<hex.length(); i+=2) {
			result[i/2] = (byte) Integer.parseInt(hex.substring(i, i+2), 16);
		}
		return result;
	}
	
	private byte[] generateHash(String password){
		byte[] ret = null;
		try {
			MessageDigest mess = MessageDigest.getInstance("SHA");
			mess.update(password.getBytes());
			ret = mess.digest();
		} catch (Exception e) {
			System.out.println("exception");
		}
		return ret;
	}
	
	public String HashedString(String password){
		return hexToString(generateHash(password));
	}

}
