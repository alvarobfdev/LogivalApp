package models;

import java.util.Date;

public class FacturasWeb {
	private int id;
	private Date fecha;
	private int ejercicio;
	private int num_factura;
	private int cliente_id;
	private float total_factura;
	private String file_pdf;
	private boolean reducida;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(int ejercicio) {
		this.ejercicio = ejercicio;
	}
	public int getNum_factura() {
		return num_factura;
	}
	public void setNum_factura(int num_factura) {
		this.num_factura = num_factura;
	}
	public int getCliente_id() {
		return cliente_id;
	}
	public void setCliente_id(int cliente_id) {
		this.cliente_id = cliente_id;
	}
	public float getTotal_factura() {
		return total_factura;
	}
	public void setTotal_factura(float total_factura) {
		this.total_factura = total_factura;
	}
	public String getFile_pdf() {
		return file_pdf;
	}
	public void setFile_pdf(String file_pdf) {
		this.file_pdf = file_pdf;
	}
	public boolean isReducida() {
		return reducida;
	}
	public void setReducida(boolean reducida) {
		this.reducida = reducida;
	}
	
	
	
	
}
