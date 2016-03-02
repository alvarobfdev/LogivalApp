package models;
// Generated 23-feb-2016 11:32:03 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Viajes generated by hbm2java
 */
public class Viajes implements java.io.Serializable {

	private ViajesId id;
	private Date fecvia;
	private short numveh;
	private Short codtra;
	private Date fecliq;
	private Date fecenv;

	public Viajes() {
	}

	public Viajes(ViajesId id, Date fecvia, short numveh) {
		this.id = id;
		this.fecvia = fecvia;
		this.numveh = numveh;
	}

	public Viajes(ViajesId id, Date fecvia, short numveh, Short codtra, Date fecliq, Date fecenv) {
		this.id = id;
		this.fecvia = fecvia;
		this.numveh = numveh;
		this.codtra = codtra;
		this.fecliq = fecliq;
		this.fecenv = fecenv;
	}

	public ViajesId getId() {
		return this.id;
	}

	public void setId(ViajesId id) {
		this.id = id;
	}

	public Date getFecvia() {
		return this.fecvia;
	}

	public void setFecvia(Date fecvia) {
		this.fecvia = fecvia;
	}

	public short getNumveh() {
		return this.numveh;
	}

	public void setNumveh(short numveh) {
		this.numveh = numveh;
	}

	public Short getCodtra() {
		return this.codtra;
	}

	public void setCodtra(Short codtra) {
		this.codtra = codtra;
	}

	public Date getFecliq() {
		return this.fecliq;
	}

	public void setFecliq(Date fecliq) {
		this.fecliq = fecliq;
	}

	public Date getFecenv() {
		return this.fecenv;
	}

	public void setFecenv(Date fecenv) {
		this.fecenv = fecenv;
	}

}