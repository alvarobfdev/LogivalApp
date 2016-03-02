package tablemodels;

import java.sql.Date;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;

import models.Cabfactu;

public class FacturaTableModel extends AbstractTableModel {
	
	public static final int COL_EJERCICIO = 0;
	public static final int COL_NUM_FACTURA = 1;
	public static final int COL_CLIENTE = 2;
	public static final int COL_TOTAL = 3;
	public static final int COL_FECHA_FAC = 4;
	public static final int COL_REDUCIDA = 5;
	public static final int COL_WEB = 6;
	public static final int COL_MAIL_SENT = 7;



	
	
    public String[] m_colNames = { "Ejercicio", "Núm. Factura", "Cliente", "Total", "Fecha Fac.", "Reducida", "Web", "Envío mail" };
    public Class[] m_colTypes = { Integer.class, Integer.class, String.class, Float.class, Date.class, Boolean.class, Boolean.class, Boolean.class};

    Vector m_macDataVector;

    public FacturaTableModel(Object[] objects) {
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
    	
    	Cabfactu macData = (Cabfactu) (m_macDataVector.elementAt(row));
    	switch (col) {
        case FacturaTableModel.COL_EJERCICIO:
          return macData.getId().getEjefac();
        case FacturaTableModel.COL_NUM_FACTURA:
          return macData.getId().getNumfac();
        case FacturaTableModel.COL_CLIENTE:
          return macData.getNomcli();
        case FacturaTableModel.COL_TOTAL:
          return macData.getTotfac();
        case FacturaTableModel.COL_FECHA_FAC:
          return macData.getFecfac();
        case FacturaTableModel.COL_REDUCIDA:
        	return macData.isReducida();
        case FacturaTableModel.COL_WEB:
        	return macData.isWeb();
        case FacturaTableModel.COL_MAIL_SENT:
        	return macData.isEmail_sent();
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
	  
      return column == FacturaTableModel.COL_REDUCIDA || column == FacturaTableModel.COL_WEB;
    }
	
	@Override
    public void setValueAt(Object aValue, int row, int column) {
      if (aValue instanceof Boolean && column == FacturaTableModel.COL_REDUCIDA) {
        Cabfactu rowData = (Cabfactu)getM_macDataVector().get(row);
        rowData.setReducida((boolean)aValue);
        fireTableCellUpdated(row, column);
      }    
      
      if (aValue instanceof Boolean && column == FacturaTableModel.COL_WEB) {
          Cabfactu rowData = (Cabfactu)getM_macDataVector().get(row);
          rowData.setWeb((boolean)aValue);
          fireTableCellUpdated(row, column);
      }
      
      if (aValue instanceof Boolean && column == FacturaTableModel.COL_MAIL_SENT) {
          Cabfactu rowData = (Cabfactu)getM_macDataVector().get(row);
          rowData.setEmail_sent((boolean)aValue);
          fireTableCellUpdated(row, column);
      }    
      
    }
	
	public void changeIndex(int index1, int index2) {
		
		Cabfactu factura1 = (Cabfactu) this.getM_macDataVector().get(index1);
		Cabfactu factura2 = (Cabfactu) this.getM_macDataVector().get(index2);
		
		this.getM_macDataVector().set(index2, factura1);
		this.getM_macDataVector().set(index1, factura2);


	}
	
	
    
    
    
  }