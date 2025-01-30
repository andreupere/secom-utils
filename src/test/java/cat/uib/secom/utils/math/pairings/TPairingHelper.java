package cat.uib.secom.utils.math.pairings;

import org.junit.Assert;
import org.junit.Test;

import cat.uib.secom.utils.math.pairing.PairingHelper;



import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.CurveParams;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import junit.framework.TestCase;

public class TPairingHelper {
	
	private CurveParams curveParameters;
	private Pairing pairing;
	private String curveFileName = "d840347-175-161.param";
	
	public TPairingHelper()   {
		curveParameters = new CurveParams();
		curveParameters.load( getClass().getResourceAsStream("/" + curveFileName) );
		pairing = PairingFactory.getPairing(curveParameters);
	}
	
	@Test
	public void testInitialization() {
		pairing.getG1().newRandomElement();
		pairing.getG2().newRandomElement();
	}
	
	@Test
	public void testPairingRandomG1Value() {
		Element e = PairingHelper.randomValue(pairing.getG1());
		System.out.println(e);
	}
	
	public void testPairingRandomZrValue() {
		Element e = PairingHelper.randomValue(pairing.getZr());
		System.out.println(e);
	}
	
	public void testPairingPow() {
		Element base = pairing.getG1().newRandomElement();
		Element exp = pairing.getZr().newRandomElement();
		Element pow = PairingHelper.pow(base, exp);
		System.out.println(pow);
	}
	
	public void testZKP() {
		Element g1 = pairing.getG1().newRandomElement();
		
		Element xu = PairingHelper.randomValue(pairing.getZr());
		
		Element yu = PairingHelper.pow(g1, xu);
		
		Element r0 = PairingHelper.randomValue(pairing.getZr());
		
		Element s0 = PairingHelper.pow(g1, r0);
		
		Element c0 = PairingHelper.schnorrZKPchallenge(pairing.getZr());
		
		Element omega0 = PairingHelper.schnorrZKPline(r0, c0, xu);
		
		boolean honestVerification = PairingHelper.schnorrZKPverify(g1, omega0, s0, yu, c0);
		
		System.out.println("Honest Verification: " + honestVerification);
		Assert.assertTrue(honestVerification);
	}

	public void testToByteFromByte() {
		Element e = pairing.getG1().newRandomElement();
		byte[] eb = e.toBytes();
		try {
			Element a = PairingHelper.fromByteArray(eb, pairing, "G1");
			Assert.assertEquals(e, a);
		} catch (Exception e1) {
			e1.printStackTrace();
			Assert.fail();
		}
	}
	
	
	
	
}
