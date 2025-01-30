package cat.uib.secom.security;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

public class RandomGeneratorUtils {

	
	/**
	 * Compute a random Integer from 0 to max
	 * 
	 * @param max as the maximum number of the range
	 * @return the random Integer
	 * */
	public static Integer generate(int max) {
		Random rnd = new Random();
		return rnd.nextInt(max);
	}
	
	/**
	 * Compute a random BigInteger from Zn
	 * 
	 * @param N
	 * @return the random BigInteger
	 */
	public static BigInteger getRandomModuleN(BigInteger N) {
		Random rnd = new Random();
		rnd.setSeed(new Date().getTime());
		
		BigInteger randomN = new BigInteger(N.bitLength(), rnd); 
		
		return randomN;
	}
	
	/**
	 * Compute a random BigInteger as long as numberBits
	 * 
	 * @param numerBits size of random BigInteger
	 * @return the random BigInteger
	 * */
	public static BigInteger getRandom(Integer numberBits) {
		Random rnd = new Random();
		rnd.setSeed(new Date().getTime());
		BigInteger random = new BigInteger(numberBits, rnd);
		
		return random;
	}
}
