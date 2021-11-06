package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.Caja;

public class ModeloTablaCaja extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -545738629065442036L;

	private String[] columnas = {"*","FECHA","ENCARGADO","SALDO INICIAL"};

	private List<Caja> cajas = new ArrayList<>();

	public void setCajas(List<Caja> cajas) {
		this.cajas = cajas;
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int c) {

		return columnas[c];
	}

	@Override
	public int getColumnCount() {

		return columnas.length;
	}
	@Override
	public int getRowCount() {
		return cajas.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			if (cajas.get(r).isCajaCerrada()) {
				if (cajas.get(r).isEstado()) {
					return "CERRADA :: VIGENTE";
				} else {
					return "CERRADA :: NO VIGENTE";
				}
			} else {
				if (cajas.get(r).isEstado()) {
					return "ABIERTA :: VIGENTE";
				} else {
					return "ABIERTA :: NO VIGENTE";
				}
			}
		case 1:
			return EventosUtil.formatoFecha(cajas.get(r).getFechaRegistro());
		case 2:
			return cajas.get(r).getColaborador();
		case 3:
			return EventosUtil.separadorMiles(cajas.get(r).getSaldoInicialGS())+" Gs. "
		+EventosUtil.separadorDecimales(cajas.get(r).getSaldoInicialRS())+" Rs. "
			+EventosUtil.separadorDecimales(cajas.get(r).getSaldoInicialUS())+" Us. ";
		default:
			return null;
		}
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		default:
			return false;
		}
	}
}

