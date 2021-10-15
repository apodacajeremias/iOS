package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.Servicio;

public class ModeloTablaServicio extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -545738629065442036L;

	private String[] columnas = {"SERVICIO"};

	private List<Servicio> detalle = new ArrayList<>();

	public void setServicio(List<Servicio> servicio) {
		this.detalle = servicio;
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int c) {

		return columnas[c];
	}

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
			return detalle.get(r);			
		default:
			return null;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int r, int c) {
		if (aValue == null) {
			return;
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

