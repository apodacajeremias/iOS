package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.InformacionPago;

public class ModeloTablaProveedorCuenta extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1695967491795350454L;

	private String[] columnas = {"NOMBRE CUENTA","NUMERO CUENTA","BANCO"};

	private List<InformacionPago> informacion = new ArrayList<>();

	public void setInformacion(List<InformacionPago> informacion) {
		this.informacion = informacion;
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
		return informacion.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return informacion.get(r).getNombreCuenta();
		case 1:
			return informacion.get(r).getNumeroCuenta();
		case 2:
			return informacion.get(r).getEntidadBancaria().getNombreBanco();
		default:
			return null;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int r, int c) {
		InformacionPago row = informacion.get(r);
		
		if (0 == c) {
			row.setNombreCuenta(((String) aValue).toUpperCase());
		}
		if (1 == c) {
			row.setNumeroCuenta((String) aValue);
		}		
		super.setValueAt(aValue, r, c);
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return true;
		case 1:
			return true;

		default:
			return false;
		}
	}

}
