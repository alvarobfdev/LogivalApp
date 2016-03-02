package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {
	
	public static final String LAST_INVOICE_EXPORT_DIR = "last_invoice_export_dir";
	
	Properties prop = new Properties();
	InputStream input = null;
	OutputStream output = null;
	ClassLoader classLoader;
	
	public Config(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}
	
	public String getProperty(String key) {
	
		String str =  "";
		try {
			
			input = this.classLoader.getResourceAsStream("config.properties");
			prop.load(input);
			str = prop.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			close();
		}
		
		return str;
		
	}
	
	public void setProperty(String key, String value) {
		try {
			output = new FileOutputStream("config.properties");
			prop.setProperty(key, value);
			prop.store(output, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close();
		}
	}
	
	public String getUserProperty(String key) {
        
		String returnStr = "";
        try {
			String home = System.getProperty("user.home");
	        File propsFile = new File(home, "logivalapp.properties");
			input = new FileInputStream(propsFile);
			prop.load(input);
			return prop.getProperty(key);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
        	close();
        }
        
        return returnStr;
	}

	public void setUserProperty(String key, String value) {
		try {
			String home = System.getProperty("user.home");
	        File propsFile = new File(home, "logivalapp.properties");
			output = new FileOutputStream(propsFile);
			prop.setProperty(key, value);
			prop.store(output, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			close();
		}
	}
	
	
	public void close() {
		try {
			if(input!= null)
				input.close();
			if(output !=null)
				output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
