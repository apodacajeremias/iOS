package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.ProveedorContactos;

public class ModeloTablaProveedorContacto extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -660523335798356322L;

	private String[] columnas = {"CONTACTOS"};

	private List<ProveedorContactos> numeros = new ArrayList<>();

	public void setContacto(List<ProveedorContactos> numeros) {
		this.numeros = numeros;
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
		return numeros.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return numeros.get(r).getNumeroTelefono();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		ProveedorContactos row = numeros.get(r);
		String numero = (String) aValue;
		if (0 == c) {
			if (numero.length() > 10) {
				return;
			}
			if (numero.isEmpty() || numero.equals("") ) {
				return;
			}
			row.setNumeroTelefono((String) aValue);
		}
		
		super.setValueAt(aValue, r, c);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return true;

		default:
			return false;
		}
	}
}
