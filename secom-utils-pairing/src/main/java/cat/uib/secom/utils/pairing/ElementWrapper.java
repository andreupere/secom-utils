package cat.uib.secom.utils.pairing;

import java.math.BigInteger;

import cat.uib.secom.utils.strings.StringUtils;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

/**
 * This a wrapper class over the jPBC Element key interface. It defines convenience transformation methods.
 *
 * @author Andreu Pere
 * */
public class ElementWrapper {

	private Element _element;
	
	/**
	 * It constructs an ElementWrapper from an Element object
	 * 
	 * @param e as the Element to be wrapped
	 * 
	 * */
	public ElementWrapper(Element e) {
		_element = e;
		_element = _element.getImmutable();
	}
	
	/**
	 * It constructs an ElementWrapper from a HEX string representation of an ElementWrapper
	 * 
	 * @param hexString hexadecimal representation of ElementWrapper
	 * @param group group where ElementWrapper belongs (G1, G2 or Zr)
	 * @param pairing pairing object obtained previously and injected here
	 * 
	 * */
	public ElementWrapper(String hexString, String group, Pairing pairing) throws Exception {
		ElementWrapper ew = PairingHelper.toElementWrapper(hexString, pairing, group);
		_element = ew.getElement();
		_element = _element.getImmutable();
	}
	
	
	/**
	 * It transforms an element to a Hex String representation
	 * */
	public String toHexString() {
		return StringUtils.readHexString( _element.toBytes() );
	}
	
	/**
	 * It transforms an element to a BigInteger representation.
	 * */
	public BigInteger toBigInteger() {
		return new BigInteger( _element.toBytes() );
	}
	
	/**
	 * It returns the wrapped element into
	 * 
	 * @return Element
	 * */
	public Element getElement() {
		return _element;
	}
	
	/**
	 * It returns the element as an immutable instance
	 * 
	 * @return immutable element
	 * */
	public Element getImmutableElement() {
		return _element.getImmutable();
	}
	
	
	/**
	 * It converts the underlying element to a byte[] representation
	 * */
	public byte[] toByteArray() {
		return _element.toBytes();
	}
	

	public String toString() {
		return this.toHexString();
	}
	
}
