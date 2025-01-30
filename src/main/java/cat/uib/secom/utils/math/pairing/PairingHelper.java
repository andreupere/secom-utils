package cat.uib.secom.utils.math.pairing;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;


import java.math.BigInteger;

import cat.uib.secom.utils.strings.StringUtils;


/**
 * Helper class to use simple arithmetic operation over pairings using ElementWrapper object
 * */
public class PairingHelper {

	/**
	 * @deprecated use {@link #random(Field)}
	 * 
	 * Random value from a field.
	 * 
	 * @param field must be a @see Field from pairing.getG1(), pairing.getG2() or pairing.getZr()
	 * */
	public static Element randomValue(Field<?> field) {
		Element rnd = field.newRandomElement().getImmutable();
		return rnd;
	}
	
	
	/**
	 * Random value from a field
	 * */
	public static ElementWrapper random(Field<?> field) {
		Element rnd = field.newRandomElement().getImmutable();
		return new ElementWrapper( rnd );
	}
	
	
	/**
	 * @deprecated use {@link #powZn(ElementWrapper, ElementWrapper)}
	 * 
	 * Exponentiation, it is: result = base^exp
	 * 
	 * @param base as the base
	 * @param exp as the exponent
	 * 
	 * */
	public static Element pow(Element base, Element exp) {
		Element result = base.powZn(exp);
		return result;
	}
	
	/**
	 * Exponentiation, it is: result = base^exp
	 * 
	 * @param base as the base
	 * @param exp as the exponent
	 * 
	 * @return ElementWrapper as the result of the operation
	 * */
	public static ElementWrapper powZn(ElementWrapper base, ElementWrapper exp) {
		Element result = base.getElement().powZn(exp.getElement());
		return new ElementWrapper( result );
	}
	
	
	/**
	 * @deprecated use {@link #random(Field)}
	 * 
	 * Shnorr's ZKP proof: step 1 (challenge)
	 * 
	 * @see #randomValue(Field)
	 * 
	 * @param field is a @see Field object obtained from pairing.getG1(), pairing.getG2() or pairing.getZr()
	 * */
	public static Element schnorrZKPchallenge(Field<?> field) {
		return randomValue(field);
	}
	
	/*public static ElementWrapper schnorrZKPchallenge(Field<?> field) {
		return randomValue(field);
	}*/
	
	/**
	 * @deprecated use {@link #schnorrZKPline(ElementWrapper, ElementWrapper, ElementWrapper)} instead
	 * Shnorr's ZKP proof: step 2
	 * 
	 * result = a + bc
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * 
	 * */
	public static Element schnorrZKPline(Element a, Element b, Element c) {
		Element result = a.add(b.mulZn(c));
		return result.getImmutable();
	}
	
	/**
	 * Shnorr's ZKP proof: step 2
	 * 
	 * result = a + bc
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * */
	public static ElementWrapper schnorrZKPline(ElementWrapper a, ElementWrapper b, ElementWrapper c) {
		Element result = a.getElement().add(b.getElement().mulZn(c.getElement()));
		return new ElementWrapper( result.getImmutable() );
	}
	
	/**
	 * @deprecated use {@link #schnorrZKPverify(ElementWrapper, ElementWrapper, ElementWrapper, ElementWrapper, ElementWrapper)} instead
	 * Shnorr's ZKP proof: step 3
	 * 
	 * verify the hide element
	 * 
	 * base^omega = s · (y)^c
	 * 
	 * @param base
	 * @param omega
	 * @param s
	 * @param y
	 * @param c
	 * */
	public static boolean schnorrZKPverify(Element base, Element omega, Element s, Element y, Element c) {
		boolean result = false;
		
		// compute the left hand
		Element left = base.powZn(omega);
		// compute the right hand
		Element right = s.mul( (y.powZn(c)) ); 
		// compare this two elements
		if (left.isEqual(right)) 
			result = true;
		
		return result;
	}
	
	
	/**
	 * * Shnorr's ZKP proof: step 3
	 * 
	 * verify the hide element
	 * 
	 * base^omega = s · (y)^c
	 * 
	 * @param base
	 * @param omega
	 * @param s
	 * @param y
	 * @param c
	 * 
	 * */
	public static boolean schnorrZKPverify(ElementWrapper base, ElementWrapper omega, ElementWrapper s, ElementWrapper y, ElementWrapper c) {
		boolean result = false;
		
		// compute the left hand
		Element left = base.getElement().powZn(omega.getElement());
		// compute the right hand
		Element right = s.getElement().mul( (y.getElement().powZn(c.getElement())) ); 
		// compare this two elements
		if (left.isEqual(right)) 
			result = true;
		
		return result;
	}
	
	/**
	 * @deprecated use {@link #toBigInteger(ElementWrapper)}
	 * Element to BigInteger
	 * 
	 * @param e input Element
	 * */
	public static BigInteger toBigInteger(Element e) {
		byte[] b = e.toBytes();
		return new BigInteger(b);
	}
	
	/**
	 * ElementWrapper to BigInteger
	 * 
	 * @param e input Element
	 * */
	public static BigInteger toBigInteger(ElementWrapper e) {
		byte[] b = e.toByteArray();
		return new BigInteger(b);
	}
	
	/**
	 * @deprecated use {@link #toElementWrapper(String, Pairing, String)}
	 * 
	 * Converts a byte array to a Element
	 * 
	 * @param e the byte[] input
	 * @param pairing the @see Pairing element
	 * @param group must be G1, G2 or Zr
	 * */
	public static Element fromByteArray(byte[] e, Pairing pairing, String group) throws Exception {
		Element helper = null;
		if (group.equals("G1"))
			helper = pairing.getG1().newOneElement();
		else if (group.equals("G2"))
			helper = pairing.getG2().newOneElement();
		else if (group.equals("Zr"))
			helper = pairing.getZr().newOneElement();
		else
			throw new Exception("group must be G1, G2 or Zr");
		
		helper.setFromBytes(e);
		return helper.getImmutable();
	}
	
	
	/**
	 * It converts byte[] to an ElementWrapper
	 * 
	 * @param e the byte[] input
	 * @param pairing the @see Pairing element
	 * @param group must be G1, G2 or Zr
	 * 
	 * @return ElementWrapper
	 * 
	 * */
	public static ElementWrapper toElementWrapper(byte[] e, Pairing pairing, String group) throws Exception {
		Element helper = null;
		if (group.equals("G1"))
			helper = pairing.getG1().newOneElement();
		else if (group.equals("G2"))
			helper = pairing.getG2().newOneElement();
		else if (group.equals("Zr"))
			helper = pairing.getZr().newOneElement();
		else
			throw new Exception("group must be G1, G2 or Zr");
		
		helper.setFromBytes(e);
		return new ElementWrapper( helper.getImmutable() );
	}
	
	
	/**
	 * It converts byte[] to an ElementWrapper
	 * 
	 * @param e the byte[] input
	 * @param pairing the @see Pairing element
	 * @param group must be G1, G2 or Zr
	 * 
	 * @return ElementWrapper
	 * 
	 * */
	public static ElementWrapper toElementWrapper(String e, Pairing pairing, String group) throws Exception {
		byte[] eb = StringUtils.hexStringToByteArray(e);
		return toElementWrapper(eb, pairing, group);
	}
	
	
	
	
}
