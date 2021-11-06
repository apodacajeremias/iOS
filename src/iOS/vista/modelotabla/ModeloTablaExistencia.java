package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.Existencia;

public class ModeloTablaExistencia extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -545738629065442036L;

	private String[] columnas = {"REFERENCIA"};

	private List<Existencia> detalle = new ArrayList<>();

	public void setExistencia(List<Existencia> existencia) {
		this.detalle = existencia;
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
		return detalle.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return detalle.get(r).getReferencia();
		default:
			return null;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int r, int c) {
		if (aValue == null) {
			return;
		}
		Existencia row = detalle.get(r);
		if (0 == c) {
			row.setReferencia((String) aValue);
			row.setEstadoUso(false);
		}
		
		super.setValueAt(aValue, r, c);
	}
	@Override
	public boolean isCellEditable(int r, int c) {
		switch (c) {
		case 0:
			return false;
		default:
			return false;
		}
	}
}

