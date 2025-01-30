package cat.uib.secom.utils.log.android;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class AndroidLog {

	protected String path;
	protected String fileName;
	protected File whereToWrite;
	protected FileWriter fw;
	protected BufferedWriter bw;
	
	public AndroidLog(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;
	}
	
	public void init() throws IOException {
		File dir = new File(path);
		if (!dir.exists()) 
			dir.mkdirs();
		
		whereToWrite = new File(dir, fileName);
		fw = new FileWriter(whereToWrite, true);
		bw = new BufferedWriter(fw);	 		

	}
	
	public void writeHeader(String header) throws IllegalArgumentException, IOException {
		if ( !check() )
			throw new IllegalArgumentException("init() method must be called just after call constructor...");
		bw.write("#" + header + "\n"); 
	}
	
	public void writeComments(String comments) throws IllegalArgumentException, IOException {
		if ( !check() )
			throw new IllegalArgumentException("init() method must be called just after call constructor...");
		bw.write("#" + comments + "\n");
	}
	
	
	public void write(String line) throws IllegalArgumentException, IOException {
		if ( !check() )
			throw new IllegalArgumentException("init() method must be called just after call constructor...");
		bw.write(line + "\n");
	}
	
	
	public void writeAll(TestFullResults list) throws IllegalArgumentException, IOException {
		if ( !check() ) 
			throw new IllegalArgumentException("init() method must be called just after call constructor...");
		Iterator<TestEntryResult> it = list.iterator();
		if (it.hasNext()) {
			TestEntryResult o = it.next();
			bw.write( o.toString() );
		}
	}
	
	public void close() throws IOException {
		bw.flush();
		bw.close();
		fw.close(); 
	}
	
	
	protected boolean check() {
		if ( this.whereToWrite == null || fw == null || bw == null )
			return false;
		return true;
	}
	
	
	
	
	
}
