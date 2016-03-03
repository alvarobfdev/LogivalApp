package app;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.hibernate.Session;

import layouts.BuscarFactura;
import layouts.FormClientes;

import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class AppWindow {

	private final static int ITEM_BUSQUEDA_FACTURAS = 0;
	private final static int ITEM_LISTADO_RESUMEN_FACTURAS = 1;
	private final static int ITEM_CLIENTES = 0;

	
	private final static String[] PANELS_FACTURACION = {"BUSQUEDA_FACTURA", "LISTADO_FACTURAS"};
	private final static String[] PANELS_CLIENTES = {"CLIENTES"};
	
	Session session = null;


	
	private JFrame frame;
	private static AppWindow window = null;
	
	private Map<String, MyJPanel> panels;
 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new AppWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		    	if(session != null)
		    		session.close();
		        HibernateUtilities.getSessionFactory("hibernate-mysql.cfg.xml").close();
		    }
		});
		
		session = HibernateUtilities.getSessionFactory("hibernate-mysql.cfg.xml").openSession();
		
		panels = new HashMap<String, MyJPanel>();
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CardLayout cardLayout = new CardLayout();
		JPanel cardHolder = new JPanel(cardLayout);
		
		
		panels.put(PANELS_FACTURACION[ITEM_BUSQUEDA_FACTURAS], new BuscarFactura(cardLayout));
		panels.put(PANELS_FACTURACION[ITEM_LISTADO_RESUMEN_FACTURAS], new ResumenFactura(cardLayout));
		panels.put(PANELS_CLIENTES[ITEM_CLIENTES], new FormClientes(cardLayout, session));

		for (Map.Entry<String, MyJPanel> entry : panels.entrySet())
		{
		    cardHolder.add((JPanel)entry.getValue(), entry.getKey());
		}
	
		frame.add(cardHolder);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuFacturacion = new JMenu("Facturación");
		JMenu menuClientes = new JMenu("Clientes");
		
		menuBar.add(menuFacturacion);
		menuBar.add(menuClientes);
		
		List<JMenuItem> itemsFacturacion = new ArrayList<JMenuItem>();
		List<JMenuItem> itemsClientes = new ArrayList<JMenuItem>();

		
		itemsFacturacion.add(ITEM_BUSQUEDA_FACTURAS, new JMenuItem("Búsqueda facturas"));
		itemsFacturacion.add(ITEM_LISTADO_RESUMEN_FACTURAS, new JMenuItem("Listado-resumen"));
		
		itemsClientes.add(ITEM_CLIENTES, new JMenuItem("Listado"));
		
		
		panels.get(PANELS_FACTURACION[ITEM_BUSQUEDA_FACTURAS]).execute();
		cardLayout.show(cardHolder, PANELS_FACTURACION[ITEM_BUSQUEDA_FACTURAS]);
		

		for(int i = 0; i < itemsFacturacion.size(); i++) {
			menuFacturacion.add(itemsFacturacion.get(i));
		
			final int index = i;
			
			itemsFacturacion.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					panels.get(PANELS_FACTURACION[index]).execute();
					cardLayout.show(cardHolder, PANELS_FACTURACION[index]);
				}
			});
			
		}
		
		for(int i = 0; i < itemsClientes.size(); i++) {
			menuClientes.add(itemsClientes.get(i));
		
			final int index = i;
			
			itemsClientes.get(i).addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					panels.get(PANELS_CLIENTES[index]).execute();
					cardLayout.show(cardHolder, PANELS_CLIENTES[index]);
				}
			});			
		}
		
		
	}
	
	public static AppWindow getInstance() {
		
		if(window != null) {
			window = new AppWindow();
		}
		return window;
	}

}
