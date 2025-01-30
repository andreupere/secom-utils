package cat.uib.secom.utils.xml;

import org.simpleframework.xml.Element;

public class Message1 extends XMLMessage {
	
	@Element
	private String text;
	
	protected Message1() {
		super();
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}