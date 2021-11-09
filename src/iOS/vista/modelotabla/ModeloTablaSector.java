package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.Sector;

public class ModeloTablaSector extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;
	
	private String[] columnas = { "BANCO" };
	private List<Sector> sectores = new ArrayList<>();
	
	public void setSectores(List<Sector> sectores) {
		this.sectores = sectores;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return sectores.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnas.length;
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return columnas[column];
	}
	
	
	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return sectores.get(r);
		default:
			break;
		}
		return null;
	}

}
