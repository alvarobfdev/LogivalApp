package app;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import models.Clientes;
import models.ClientesId;
import models.Acumovi;
import models.AcumoviId;
import models.Albaran;
import models.AlbaranId;
import models.Cabfactu;

public class Admin {

	public static void main(String[] args) {
		
		Session session = HibernateUtilities.getSessionFactory("hibernate-hosting-logival.cfg.xml").openSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			String query = "SELECT * FROM clientes where sislog='WEB'";
			DataBaseHelper dbHelper = new DataBaseHelper();
			Clientes[] clientes = dbHelper.getObjectClass(query, Clientes[].class);
			ClientesId[] clientesId = dbHelper.getObjectClass(query, ClientesId[].class);
			dbHelper.setId(clientes, clientesId);
			for(Clientes cliente : clientes) {
				hostingmodels.Clientes clienteHosting = new hostingmodels.Clientes();
				clienteHosting.setCodcli(cliente.getId().getCodcli());
				clienteHosting.setCpocli(cliente.getCodpos());
				clienteHosting.setDomcli(cliente.getDomcli());
				clienteHosting.setNomcli(cliente.getNomcli());
				clienteHosting.setPobcli(cliente.getPobcli());
				session.save(clienteHosting);
			}
			
			transaction.commit();
			session.close();
			HibernateUtilities.getSessionFactory("hibernate-hosting-logival.cfg.xml").close();
			
			
			/*
			Connection connectionCtsql;
			try {
				connectionCtsql = CtsqlHandler.getConnection();
				Statement statementCtsql = connectionCtsql.createStatement();
				statementCtsql.executeUpdate("UPDATE acumovi set stkini=stkini-1 where codart='911144041' and ejerci = 2015");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}

}
