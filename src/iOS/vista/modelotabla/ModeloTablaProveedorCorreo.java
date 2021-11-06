package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.ProveedorCorreos;

public class ModeloTablaProveedorCorreo extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -660523335798356322L;

	private String[] columnas = {"CORREOS"};

	private List<ProveedorCorreos> correos = new ArrayList<>();

	public void setCorreo(List<ProveedorCorreos> correos) {
		this.correos = correos;
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
		return correos.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return correos.get(r).getCorreoElectronico();
		default:
			return null;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int r, int c) {
		ProveedorCorreos row = correos.get(r);	
		if (0 == c) {
			String email = (String) aValue;
			Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
			Matcher mather = pattern.matcher(email);
			if (mather.find() == true) {
				try {
					row.setCorreoElectronico((String) aValue);
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			} else {
				return;
			}	
			
			
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
