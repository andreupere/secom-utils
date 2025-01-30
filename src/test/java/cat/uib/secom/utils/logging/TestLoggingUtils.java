package cat.uib.secom.utils.logging;

import junit.framework.Assert;

import org.junit.Test;

public class TestLoggingUtils {

	@Test
	public void testLogging() {
		LoggingUtils log = LoggingUtils.getInstance(true);
		Assert.assertTrue( log.getActive() );
		
		log.printConsoleSimple(this.getClass(), "prova1");
		log.printConsole(this.getClass(), "prova2");
		
		log.setActive(false);
		Assert.assertFalse(log.getActive());
		
		log.printConsoleSimple(this.getClass(), "prova3");
		log.printConsole(this.getClass(), "prova4"); 
	}
}
