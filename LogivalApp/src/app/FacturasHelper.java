package app;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.google.gson.Gson;

import models.Cabfactu;
import models.CabfactuId;
import models.Linfactu;
import models.LinfactuId;
import tablemodels.FacturaTableModel;

public interface FacturasHelper {
	
	public default void addLinesToInvoice(List<Cabfactu> facturas) {
		for(Cabfactu factura : facturas) {
			CabfactuId id = factura.getId();
			String whereLines = "(cenfac='"+id.getCenfac()+"' "
					+ "and codemp='"+id.getCodemp()+"' "
					+ "and serfac='"+id.getSerfac()+"' "
					+ "and ejefac='"+id.getEjefac()+"' "
					+ "and numfac='"+id.getNumfac()+"')";
			
			String query = "SELECT *, import as import_ FROM linfactu where "+whereLines;
			DataBaseHelper dbHelper = new DataBaseHelper();
			Linfactu[] lineas = dbHelper.getObjectClass(query, Linfactu[].class);
			LinfactuId[] lineasId = dbHelper.getObjectClass(query, LinfactuId[].class);
			try {
				dbHelper.setId(lineas, lineasId);
				factura.setLines(Arrays.asList(lineas));
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public default String getJson(int[] selectedRows, JTable table1, TableRowSorter<TableModel> sorter) {
		List<Cabfactu> facturas = new ArrayList<Cabfactu>();
		
		if(selectedRows != null) {
			FacturaTableModel modelo = (FacturaTableModel) table1.getModel();
			
			for(int selectRow : selectedRows) {
				Cabfactu factura = (Cabfactu) modelo.getM_macDataVector().get(sorter.convertRowIndexToModel(selectRow));
				facturas.add(factura);
			}
			
			addLinesToInvoice(facturas);
		}
		return new Gson().toJson(facturas.toArray());
	}
	
}
