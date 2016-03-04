package tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import app.Config;
import app.FacturasHelper;
import models.Cabfactu;
import models.CabfactuId;
import models.Linfactu;
import models.LinfactuId;
import tablemodels.FacturaTableModel;

public class ExportInvoicePDF extends Thread implements FacturasHelper {
	
	int[] selectedRows;
	JTable table1;
	JLabel labelProgress;
	TableRowSorter<TableModel> sorter;
	String directory;
    HttpURLConnection connection = null;
    JProgressBar progressBar;
    FileOutputStream fileOS = null;
    InputStream input = null;

	
	
	public ExportInvoicePDF(int[] selectedRows, JTable table1, TableRowSorter<TableModel> sorter, String directory, JLabel labelProgress, JProgressBar progressBar) {
		super();
		this.selectedRows = selectedRows;
		this.table1 = table1;
		this.sorter = sorter;
		this.directory = directory;
		this.labelProgress = labelProgress;
		this.progressBar = progressBar;
	}
	
	@Override
	public void run() {
		
		
		try {
			String json = getJson(selectedRows, table1, sorter);
			System.out.println(json);
			String now = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
			File file = new File(directory+"/facturas-"+now+".zip");
			System.out.println(directory+"/facturas-"+now+".zip");
            fileOS  = new FileOutputStream(file);
            
			//HttpClient client = HttpClientBuilder.create().build();
			//HttpPost post = new HttpPost(new Config().getProperty("pdf_generator_api"));
			//List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			//urlParameters.add(new BasicNameValuePair("jsonData", json));
			//post.setEntity(new UrlEncodedFormEntity(urlParameters));
			//HttpResponse response = client.execute(post);
			
			
			labelProgress.setVisible(true);
            progressBar.setVisible(true);
            labelProgress.setText("Exportando fichero...");
            
			URL url = new URL(new Config(Thread.currentThread().getContextClassLoader()).getProperty("pdf_generator_api"));
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000); //Timeout 5s
            
            List nameValuePairs = new ArrayList(1);
            System.out.println(json);
            nameValuePairs.add(new BasicNameValuePair("jsonData", json));
           
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs);
            connection.setDoOutput(true);
            OutputStream postOut = connection.getOutputStream();
            entity.writeTo(postOut);
            postOut.flush();
			//BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());	
            
            int maxBytes = connection.getContentLength();
            progressBar.setMaximum(maxBytes);
            
            
            System.out.println(""+maxBytes);
            input = connection.getInputStream();

			int reaminingBytes = maxBytes;
			
			byte[] buffer = new byte[5 * 1024];
	        int numRead = -1;

			while((numRead = input.read(buffer))!= -1) {
				
				fileOS.write(buffer, 0, numRead);
				reaminingBytes -= numRead;
				progressBar.setValue(maxBytes-reaminingBytes);
			}
			
			labelProgress.setText("Fichero exportado en: "+directory);

			
			fileOS.flush();
			fileOS.close();
			input.close();
			
			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			labelProgress.setText("Error al exportar fichero. Compruebe conexión con las máquinas!");
			e.printStackTrace();
		}
		
		finally {
			
			try {
				if(fileOS != null) 
					fileOS.close();
				if(input != null)
					input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
}
