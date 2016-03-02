package models;
// Generated 23-feb-2016 11:32:03 by Hibernate Tools 3.4.0.CR1

/**
 * ArtblocId generated by hbm2java
 */
public class ArtblocId implements java.io.Serializable {

	private String codigo;
	private String descri;
	private int numlin;
	private String oldcod;
	private Short codcli;

	public ArtblocId() {
	}

	public ArtblocId(String codigo, String descri, int numlin) {
		this.codigo = codigo;
		this.descri = descri;
		this.numlin = numlin;
	}

	public ArtblocId(String codigo, String descri, int numlin, String oldcod, Short codcli) {
		this.codigo = codigo;
		this.descri = descri;
		this.numlin = numlin;
		this.oldcod = oldcod;
		this.codcli = codcli;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescri() {
		return this.descri;
	}

	public void setDescri(String descri) {
		this.descri = descri;
	}

	public int getNumlin() {
		return this.numlin;
	}

	public void setNumlin(int numlin) {
		this.numlin = numlin;
	}

	public String getOldcod() {
		return this.oldcod;
	}

	public void setOldcod(String oldcod) {
		this.oldcod = oldcod;
	}

	public Short getCodcli() {
		return this.codcli;
	}

	public void setCodcli(Short codcli) {
		this.codcli = codcli;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ArtblocId))
			return false;
		ArtblocId castOther = (ArtblocId) other;

		return ((this.getCodigo() == castOther.getCodigo()) || (this.getCodigo() != null
				&& castOther.getCodigo() != null && this.getCodigo().equals(castOther.getCodigo())))
				&& ((this.getDescri() == castOther.getDescri()) || (this.getDescri() != null
						&& castOther.getDescri() != null && this.getDescri().equals(castOther.getDescri())))
				&& (this.getNumlin() == castOther.getNumlin())
				&& ((this.getOldcod() == castOther.getOldcod()) || (this.getOldcod() != null
						&& castOther.getOldcod() != null && this.getOldcod().equals(castOther.getOldcod())))
				&& ((this.getCodcli() == castOther.getCodcli()) || (this.getCodcli() != null
						&& castOther.getCodcli() != null && this.getCodcli().equals(castOther.getCodcli())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCodigo() == null ? 0 : this.getCodigo().hashCode());
		result = 37 * result + (getDescri() == null ? 0 : this.getDescri().hashCode());
		result = 37 * result + this.getNumlin();
		result = 37 * result + (getOldcod() == null ? 0 : this.getOldcod().hashCode());
		result = 37 * result + (getCodcli() == null ? 0 : this.getCodcli().hashCode());
		return result;
	}

}