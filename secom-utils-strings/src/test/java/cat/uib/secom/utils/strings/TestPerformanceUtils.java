package cat.uib.secom.utils.strings;

import java.io.IOException;



public class TestPerformanceUtils {

	
	public void test() {
		PerformanceUtils pu = new PerformanceUtils();
		pu.addComments("comment1");
		pu.addComments("comment2");
		pu.addHeader("proto", "it", "t1", "t2", "t3");
		pu.addResult("proto1", new Integer(1), new Long("2"), new Long("2"), new Long("-4"));
		pu.addResult("proto12", new Integer(2), new Double(1.2), new Double(15.1), new Double(41.11));
		pu.addResult("proto2", new Integer(3), new Double(26.2), new Double(12.1), new Double(445.11));
		System.out.println( pu.getResults() );
		try {
			pu.storeInFile("/home/apaspai/development/transitory_testing/results.txt", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
