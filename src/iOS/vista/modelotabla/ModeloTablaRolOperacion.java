package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.RolOperacion;

public class ModeloTablaRolOperacion extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;
	
	private String[] columnas = { "OPERACION y MODULO" };
	private List<RolOperacion> rolesOperaciones = new ArrayList<>();
	
	public void setOperaciones(List<RolOperacion> rolesOperaciones) {
		this.rolesOperaciones = rolesOperaciones;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rolesOperaciones.size();
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
			return rolesOperaciones.get(r).getOperacion();
		default:
			break;
		}
		return null;
	}

}

