package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.CajaMovimiento;

public class ModeloTablaCajaMovimiento extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -545738629065442036L;

	private String[] columnas = {"*","GS","RS","US","*","OBS."};

	private List<CajaMovimiento> movimientos = new ArrayList<>();

	public void setMovimiento(List<CajaMovimiento> movimientos) {
		this.movimientos = movimientos;
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
		return movimientos.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			if (movimientos.get(r).isEsRetiro() == false) {
				if (movimientos.get(r).isEsAnulado() == true) {
					return "INGRESO ANULADO";
				}
				return EventosUtil.formatoFecha(movimientos.get(r).getFechaRegistro())+" INGRESO "+movimientos.get(r).getTipoValor().toUpperCase();
			} else {
				if (movimientos.get(r).isEsAnulado() == true) {
					return "RETIRO ANULADO";
				}
				return EventosUtil.formatoFecha(movimientos.get(r).getFechaRegistro())+" RETIRO "+movimientos.get(r).getTipoValor().toUpperCase();
			}
		case 1:
			return EventosUtil.separadorMiles(movimientos.get(r).getValorGS());
		case 2:
			return EventosUtil.separadorDecimales(movimientos.get(r).getValorRS());
		case 3:
			return EventosUtil.separadorDecimales(movimientos.get(r).getValorUS());
		case 4:
			if (movimientos.get(r).getCliente() == null) {
				return movimientos.get(r).getColaborador();
			}
			
			if (movimientos.get(r).getColaborador() == null) {
				return movimientos.get(r).getCliente();
			}
		case 5:
			return movimientos.get(r).getObservacion().toUpperCase();
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

