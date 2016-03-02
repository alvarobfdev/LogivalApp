package layouts;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

import app.Config;
import app.DataBaseHelper;
import app.MyJPanel;
import models.Cabfactu;
import models.CabfactuId;
import models.FacturasWeb;
import models.Linfactu;
import models.LinfactuId;
import tablemodels.FacturaTableModel;
import tasks.ExportInvoicePDF;
/*
 * Created by JFormDesigner on Thu Jan 28 12:55:00 CET 2016
 */
import tasks.RefreshClientWebInvoices;
import tasks.SendInvoiceMail;

/**
 * @author asd asd
 */
public class BuscarFactura extends JPanel implements MyJPanel {

	int selectedRows[] = null;
	JFileChooser chooser;
	TableRowSorter<TableModel> sorter;
	Config configFile;
	boolean selectionProg = false;

	public BuscarFactura(CardLayout cardLayout) {

		super(cardLayout);

		initComponents();

		listeners();

	}

	public void execute() {

		configFile = new Config(Thread.currentThread().getContextClassLoader());
		labelProgressExport.setVisible(false);
		progressBar1.setVisible(false);
		btnExportPDF.setEnabled(false);
		btn_actualizarWeb.setEnabled(false);
		btn_sent_email.setEnabled(false);

		int year = Calendar.getInstance().get(Calendar.YEAR);
		String query = "SELECT * FROM cabfactu WHERE ejefac=" + year;
		DataBaseHelper dbHelper = new DataBaseHelper();
		Cabfactu[] facturas = dbHelper.getObjectClass(query, Cabfactu[].class);
		CabfactuId[] facturasId = dbHelper.getObjectClass(query, CabfactuId[].class);

		DataBaseHelper dbHelperMySql = new DataBaseHelper(DataBaseHelper.MYSQL_DB);
		FacturasWeb[] facturasWeb = dbHelperMySql.getObjectClass("SELECT * FROM facturas_web where ejercicio=" + year,
				FacturasWeb[].class);

		try {
			dbHelper.setId(facturas, facturasId);

			setFacturasWeb(facturas, facturasWeb);

			table1.setModel(new FacturaTableModel(facturas));
			sorter = new TableRowSorter<>(table1.getModel());
			table1.setRowSorter(sorter);

			List<RowSorter.SortKey> sortKeys = new ArrayList<>();

			int columnIndexToSort = 1;
			sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));

			sorter.setSortKeys(sortKeys);
			table1.setRowSorter(sorter);
			sorter.sort();
			table1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setFacturasWeb(Cabfactu[] facturas, FacturasWeb[] facturasWeb) {
		for (Cabfactu factura : facturas) {
			for (FacturasWeb facturaWeb : facturasWeb) {
				if (factura.getId().getEjefac() == facturaWeb.getEjercicio()
						&& factura.getId().getNumfac() == facturaWeb.getNum_factura()) {
					factura.setWeb(true);
					if (facturaWeb.isReducida()) {
						factura.setReducida(true);
					}
				}
			}
		}
	}

	private void openDirectoryChoosen() {
		chooser = new JFileChooser();

		String lastDirProperty = configFile.getUserProperty(Config.LAST_INVOICE_EXPORT_DIR);

		chooser.setCurrentDirectory(new java.io.File("."));

		if (lastDirProperty != null) {
			chooser.setCurrentDirectory(new java.io.File(lastDirProperty));
		}

		chooser.setDialogTitle("Seleccione directorio");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);

		//
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

			// System.out.println(""+chooser.getSelectedFile());
			configFile.setUserProperty(Config.LAST_INVOICE_EXPORT_DIR, "" + chooser.getSelectedFile());
			exportToPDF("" + chooser.getSelectedFile());
		} else {
			System.out.println("No Selection ");
		}
	}

	private void refreshClientWeb() {
		new RefreshClientWebInvoices(selectedRows, table1, sorter, labelProgressExport, progressBar1).start();

	}

	private void exportToPDF(String directory) {
		new ExportInvoicePDF(selectedRows, table1, sorter, directory, labelProgressExport, progressBar1).start();
	}
	
	private void sentInvoiceToClient() {
		if(checkInWeb()) 
			new SendInvoiceMail(selectedRows, table1, sorter, labelProgressExport, progressBar1).start();
		else {
			JFrame frame = new JFrame("JOptionPane showMessageDialog component example");
			JOptionPane.showMessageDialog(frame,
				    "Para enviar factura al cliente tiene que estar cargada en la web.");
		}
			
	}
	
	private boolean checkInWeb() {
		FacturaTableModel modelo = (FacturaTableModel) table1.getModel();

		for(int selectRow : selectedRows) {
			Cabfactu factura = (Cabfactu) modelo.getM_macDataVector().get(sorter.convertRowIndexToModel(selectRow));
			if(!factura.isWeb()) {
				return false;
			}
		}
		
		return true;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Ãlvaro BaÃ±o
		btnExportPDF = new JButton();
		btn_actualizarWeb = new JButton();
		btn_sent_email = new JButton();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		cb_all_web = new JCheckBox();
		labelProgressExport = new JLabel();
		progressBar1 = new JProgressBar();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

		setLayout(new FormLayout(
			"35dlu, $lcgap, 37dlu, $lcgap, 25dlu, $lcgap, 87dlu, $lcgap, 25dlu, $lcgap, 115dlu, $lcgap, 86dlu",
			"6*(default, $lgap), default"));

		//---- btnExportPDF ----
		btnExportPDF.setText("Exportar a PDF");
		add(btnExportPDF, CC.xywh(1, 3, 4, 1));

		//---- btn_actualizarWeb ----
		btn_actualizarWeb.setText("Actualizar en web");
		add(btn_actualizarWeb, CC.xy(7, 3));

		//---- btn_sent_email ----
		btn_sent_email.setText("Enviar factura a cliente (email)");
		add(btn_sent_email, CC.xy(11, 3));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(table1);
		}
		add(scrollPane1, CC.xywh(1, 7, 13, 1));

		//---- cb_all_web ----
		cb_all_web.setText("Seleccionar todos para web");
		add(cb_all_web, CC.xywh(1, 9, 7, 1));

		//---- labelProgressExport ----
		labelProgressExport.setText("text");
		add(labelProgressExport, CC.xywh(1, 11, 13, 1, CC.CENTER, CC.CENTER));
		add(progressBar1, CC.xywh(1, 13, 13, 1, CC.CENTER, CC.CENTER));
		// //GEN-END:initComponents
	}

	private void listeners() {
		table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				selectedRows = table1.getSelectedRows();

				if (selectedRows.length > 0) {
					btnExportPDF.setEnabled(true);
					btn_actualizarWeb.setEnabled(true);
					btn_sent_email.setEnabled(true);
				} else {
					btnExportPDF.setEnabled(false);
					btn_actualizarWeb.setEnabled(false);
					btn_sent_email.setEnabled(false);

				}
			}
		});

		cb_all_web.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cb_all_web.isSelected()) {
					for (int i = 0; i < table1.getRowCount(); i++) {
						table1.setValueAt(true, i, FacturaTableModel.COL_WEB);
					}
				} else {
					for (int i = 0; i < table1.getRowCount(); i++) {
						table1.setValueAt(false, i, FacturaTableModel.COL_WEB);
					}
				}

			}
		});

		btnExportPDF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openDirectoryChoosen();
			}
		});

		btn_actualizarWeb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refreshClientWeb();
			}
		});
		
		btn_sent_email.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sentInvoiceToClient();
				
			}
		});
		
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Ãlvaro BaÃ±o
	private JButton btnExportPDF;
	private JButton btn_actualizarWeb;
	private JButton btn_sent_email;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JCheckBox cb_all_web;
	private JLabel labelProgressExport;
	private JProgressBar progressBar1;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
