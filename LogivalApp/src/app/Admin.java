package app;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;

import models.Clientes;
import models.ClientesId;
import models.Acumovi;
import models.AcumoviId;
import models.Albaran;
import models.AlbaranId;
import models.Cabfactu;

public class Admin {

	public static void main(String[] args) {
		
		
		try {
			String query = "SELECT * FROM clientes where sislog='WEB'";
			DataBaseHelper dbHelper = new DataBaseHelper();
			Clientes[] clientes = dbHelper.getObjectClass(query, Clientes[].class);
			ClientesId[] clientesId = dbHelper.getObjectClass(query, ClientesId[].class);
			dbHelper.setId(clientes, clientesId);
			for(Clientes cliente : clientes) {
				System.out.println(cliente.getNomacc());
			}
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
