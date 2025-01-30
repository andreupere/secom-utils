package cat.uib.secom.utils.strings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class PerformanceUtils {

	private StringBuffer perfResults = null;
	
	
	public PerformanceUtils(){
		perfResults = new StringBuffer();
	}
	
	public void addComments(String initialComments) {
		perfResults.append("#");
		perfResults.append(initialComments);
		perfResults.append("\r\n");
	}
	
	public void addHeader(String... header) {
		perfResults.append("#");
		for (String h : header) {
			perfResults.append(h);
			perfResults.append("\t");
		}
		perfResults.append("\r\n");
	}
	
	public void addResult(String protocol, Integer iteration, Double... result) {
		perfResults.append(protocol);
		perfResults.append("\t");
		perfResults.append(iteration);
		perfResults.append("\t");
		for (Double s : result) {
			perfResults.append(s);
			perfResults.append("\t");
		}
		perfResults.append("\r\n");
	}
	
	public void addResult(String protocol, Integer iteration, String... result) {
		perfResults.append(protocol);
		perfResults.append("\t");
		perfResults.append(iteration);
		perfResults.append("\t");
		for (String s : result) {
			perfResults.append(s);
			perfResults.append("\t");
		}
		perfResults.append("\r\n");
	}
	
	public void addResult(String protocol, Integer iteration, Long... result) {
		perfResults.append(protocol);
		perfResults.append("\t");
		perfResults.append(iteration);
		perfResults.append("\t");
		for (Long s : result) {
			perfResults.append(s);
			perfResults.append("\t");
		}
		perfResults.append("\r\n");
	}
	
	
	
	public String getResults() {
		return this.perfResults.toString();
	}
	
	
	public void storeInFile(String path, boolean append) throws IOException { 
		BufferedWriter writer = new BufferedWriter(new FileWriter(path, append));
		writer.write(this.perfResults.toString());
		writer.close();
		this.perfResults = new StringBuffer(); //remove data in-memory already stored in file
	}
	
	
}
