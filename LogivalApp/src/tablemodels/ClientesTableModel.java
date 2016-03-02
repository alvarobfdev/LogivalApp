package tablemodels;

import java.sql.Date;
import java.util.Arrays;
import java.util.Vector;
import models.Clientes;

import javax.swing.table.AbstractTableModel;

import models.Cabfactu;

public class ClientesTableModel extends AbstractTableModel {
	
	public static final int COL_ID = 0;
	public static final int COL_NOMBRE = 1;
	public static final int COL_NIF = 2;
	

	
	
    public String[] m_colNames = { "Cód. Cliente", "Nombre", "NIF"};
    public Class[] m_colTypes = { Integer.class, String.class, String.class};

    Vector m_macDataVector;

    public ClientesTableModel(Object[] objects) {
      super();
      m_macDataVector = new Vector(Arrays.asList(objects));
    }
    public int getColumnCount() {
      return m_colNames.length;
    }
    public int getRowCount() {
      return m_macDataVector.size();
    }
    

    public String getColumnName(int col) {
      return m_colNames[col];
    }

    public Class getColumnClass(int col) {
      return m_colTypes[col];
    }
    public Object getValueAt(int row, int col) {
    	
    	Clientes macData = (Clientes) (m_macDataVector.elementAt(row));
    	switch (col) {
        	case ClientesTableModel.COL_ID:
        		return macData.getId().getCodcli();
        	case ClientesTableModel.COL_NIF:
        		return macData.getCifdni();
        	case ClientesTableModel.COL_NOMBRE:
        		return macData.getNomcli();
        }

        return new String();
      	
    }
	public Vector getM_macDataVector() {
		return m_macDataVector;
	}
	public void setM_macDataVector(Vector m_macDataVector) {
		this.m_macDataVector = m_macDataVector;
	}
	
	@Override
    public boolean isCellEditable(int row, int column) {
      return false;
    }
	
	
	
}
