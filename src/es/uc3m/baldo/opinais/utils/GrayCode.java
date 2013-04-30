package es.uc3m.baldo.opinais.utils;

/**
 * 
 * @author Alejandro Baldominos
 *
 */
public class GrayCode {
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	public static long encodeGray (long n){
		return n ^ (n >>> 1);
	}
 
	/**
	 * 
	 * @param n
	 * @return
	 */
	public static long decodeGray (long n) {
		long p = n;
		while ((n >>>= 1) != 0)
			p ^= n;
		return p;
	}
}
