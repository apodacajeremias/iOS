package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.Rol;

public class ModeloTablaRol extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;
	
	private String[] columnas = { "MODULO" };
	private List<Rol> rol = new ArrayList<>();
	
	public void setRoles(List<Rol> rol) {
		this.rol = rol;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rol.size();
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
			return rol.get(r)+ " PERMISOS: "+rol.get(r).getRolesOperaciones().size();
		default:
			break;
		}
		return null;
	}

}

