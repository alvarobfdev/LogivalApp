/*
 * Created by JFormDesigner on Tue Feb 23 12:06:13 CET 2016
 */

package layouts;

import java.awt.*;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

import app.DataBaseHelper;
import app.MyJPanel;
import tablemodels.ClientesTableModel;
import models.Clientes;
import models.ClientesId;

/**
 * @author Alvaro Banyo
 */
public class FormClientes extends JPanel implements MyJPanel {

	DataBaseHelper dbHelper;
	ArrayList<JTextField> filterFields;
	int selectedRows[] = null;
	Clientes[] clientes;
	Session session;
	Clientes selectedCliente = null;

	public FormClientes(CardLayout cardLayout, Session session) {

		super(cardLayout);
		initComponents();
		fillComponents();
		searchFieldsEvents();
		tableEvents();
		formEvents();
		this.session = session;

	}

	public void execute() {
		this.session = session;
		try {
			dbHelper = new DataBaseHelper();
			String query = "SELECT * from clientes";
			clientes = dbHelper.getObjectClass(query, Clientes[].class);
			ClientesId[] clientesId = dbHelper.getObjectClass(query, ClientesId[].class);
			dbHelper.setId(clientes, clientesId);
			table1.setModel(new ClientesTableModel(clientes));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Ãlvaro BaÃ±o
		panel4 = new JPanel();
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		tf_numcli_search = new JTextField();
		tf_nomcli_search = new JTextField();
		tf_nif_search = new JTextField();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
		panelDatosCliente = new JPanel();
		label5 = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		tf_codCliente = new JTextField();
		tf_nomCli = new JTextField();
		tf_emailCli = new JTextField();
		btn_save_client = new JButton();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

		setLayout(new FormLayout(
			"default, $lcgap, 388dlu, $lcgap, 14dlu",
			"3*(default, $lgap), 100dlu, 3*($lgap, default)"));

		//======== panel4 ========
		{
			panel4.setLayout(new FormLayout(
				"62dlu, $lcgap, 135dlu, $lcgap, 60dlu",
				"2*(default, $lgap), default"));

			//---- label1 ----
			label1.setText("C\u00f3d. Cliente");
			panel4.add(label1, CC.xy(1, 1));

			//---- label2 ----
			label2.setText("Nombre");
			panel4.add(label2, CC.xy(3, 1));

			//---- label3 ----
			label3.setText("NIF");
			panel4.add(label3, CC.xy(5, 1));
			panel4.add(tf_numcli_search, CC.xy(1, 3));
			panel4.add(tf_nomcli_search, CC.xy(3, 3));
			panel4.add(tf_nif_search, CC.xy(5, 3));
		}
		add(panel4, CC.xy(3, 3));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(table1);
		}
		add(scrollPane1, CC.xywh(3, 7, 2, 1));

		//======== panelDatosCliente ========
		{
			panelDatosCliente.setLayout(new FormLayout(
				"4*(80dlu, $lcgap), 63dlu",
				"9*(default, $lgap), default"));

			//---- label5 ----
			label5.setText("C\u00f3d. Cliente");
			panelDatosCliente.add(label5, CC.xy(1, 1));

			//---- label6 ----
			label6.setText("Nombre");
			panelDatosCliente.add(label6, CC.xy(3, 1));

			//---- label7 ----
			label7.setText("Email");
			panelDatosCliente.add(label7, CC.xy(7, 1));
			panelDatosCliente.add(tf_codCliente, CC.xy(1, 3));
			panelDatosCliente.add(tf_nomCli, CC.xywh(3, 3, 3, 1));
			panelDatosCliente.add(tf_emailCli, CC.xywh(7, 3, 3, 1));

			//---- btn_save_client ----
			btn_save_client.setText("Guardar");
			panelDatosCliente.add(btn_save_client, CC.xy(1, 7));
		}
		add(panelDatosCliente, CC.xy(3, 11));
		// //GEN-END:initComponents
	}

	private void searchFieldsEvents() {

		for (JTextField field : filterFields) {
			field.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent e) {
					changeSearchTextsEvent();

				}

				@Override
				public void insertUpdate(DocumentEvent e) {
					changeSearchTextsEvent();

				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					changeSearchTextsEvent();
				}
			});
		}

	}

	private void changeSearchTextsEvent() {
		String numcli = tf_numcli_search.getText();
		String nomcli = tf_nomcli_search.getText();
		String nif = tf_nif_search.getText();
		filterTable(numcli, nomcli, nif);
	}

	private void filterTable(String numcli, String nomcli, String nif) {

		System.out.println(numcli + ":" + nomcli + ":" + nif);
		numcli = numcli.toUpperCase();
		nomcli = nomcli.toUpperCase();
		nif = nif.toUpperCase();

		try {

			String query = "SELECT * FROM clientes where" + " nomcli like \"%" + nomcli + "%\"" + " and cifdni like \"%"
					+ nif + "%\"";

			if (numcli.length() > 0) {
				query += " and codcli = " + numcli;
			}

			clientes = dbHelper.getObjectClass(query, Clientes[].class);
			ClientesId[] clientesId = dbHelper.getObjectClass(query, ClientesId[].class);
			dbHelper.setId(clientes, clientesId);
			table1.setModel(new ClientesTableModel(clientes));

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void fillComponents() {
		filterFields = new ArrayList<>();
		filterFields.add(tf_nif_search);
		filterFields.add(tf_nomcli_search);
		filterFields.add(tf_numcli_search);
		panel4.setBorder(BorderFactory.createTitledBorder("Filtros"));
		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void tableEvents() {
		table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (table1.getSelectedRow() != -1) {
					showClient(table1.getSelectedRow());
				}
			}
		});
	}

	private void showClient(int position) {
		Clientes cliente = clientes[position];
		tf_codCliente.setText("" + cliente.getId().getCodcli());
		tf_nomCli.setText(cliente.getNomcli());
		Clientes clienteDB = getClienteDb(cliente);
		tf_emailCli.setText(clienteDB.getEmail());
		selectedCliente = clienteDB;
		
	}

	private Clientes getClienteDb(Clientes cliente) {
		
		session.beginTransaction();
		Clientes clienteDB = session.get(Clientes.class, cliente.getId());
		return clienteDB;

	}
	
	private void formEvents() {
		btn_save_client.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedCliente != null) {
					try {
						Transaction transaction = session.beginTransaction();
						selectedCliente.setNomcli(tf_nomCli.getText());
						selectedCliente.getId().setCodcli(Short.parseShort(tf_codCliente.getText()));
						selectedCliente.setEmail(tf_emailCli.getText());
						session.saveOrUpdate(selectedCliente);
						transaction.commit();
						JFrame frame = new JFrame("JOptionPane showMessageDialog component example");
						JOptionPane.showMessageDialog(frame,
							    "Cliente actualizado con éxito");
					}
					catch(Exception exc) {
						JFrame frame = new JFrame("JOptionPane showMessageDialog component example");
						JOptionPane.showMessageDialog(frame,
							    "Error: "+exc.getMessage());
					}
				}
				
			}
		});
		
		
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Ãlvaro BaÃ±o
	private JPanel panel4;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JTextField tf_numcli_search;
	private JTextField tf_nomcli_search;
	private JTextField tf_nif_search;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JPanel panelDatosCliente;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JTextField tf_codCliente;
	private JTextField tf_nomCli;
	private JTextField tf_emailCli;
	private JButton btn_save_client;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
