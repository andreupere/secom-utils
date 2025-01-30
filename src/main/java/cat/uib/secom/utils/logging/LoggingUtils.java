package cat.uib.secom.utils.logging;

public class LoggingUtils {

	private static LoggingUtils _log;
	
	private static boolean _active;
	
	private LoggingUtils() {}
	
	public static LoggingUtils getInstance(boolean active) {
		if (_log == null)
			_log = new LoggingUtils();
		_active = active;
		return _log; 
	}
	
	public void printConsoleSimple(Class<?> cl, String message) {
		if (_active) {
			System.out.println("\n" + cl);
			System.out.println(message);
			System.out.println("\n");
		}
		
	}
	
	public void printConsole(Class<?> cl, String message) {
		if (_active) {
			System.out.println("\n------------------------------------------------------------");
			System.out.println(cl);
			System.out.println(message);
			System.out.println("------------------------------------------------------------\n");
		}
	}
	
	public void setActive(boolean active) {
		_active = active;
	}
	
	public boolean getActive() {
		return _active;
	}
	
}
