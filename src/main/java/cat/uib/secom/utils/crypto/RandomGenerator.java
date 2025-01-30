package cat.uib.secom.utils.crypto;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

public class RandomGenerator {

	public static Integer generate(int max) {
		Random rnd = new Random();
		return rnd.nextInt(max);
	}
	
	/**
	 * Calcula un nombre aleatori del cos Z(N)
	 */
	public static BigInteger getRandomModuleN(BigInteger N) {
		Random rnd = new Random();
		rnd.setSeed(new Date().getTime());
		
		BigInteger randomN = new BigInteger(N.bitLength(), rnd); 
		
		return randomN;
	}
	
	
	public static BigInteger getRandom(Integer bits) {
		Random rnd = new Random();
		rnd.setSeed(new Date().getTime());
		BigInteger random = new BigInteger(bits, rnd);
		
		return random;
	}
}
