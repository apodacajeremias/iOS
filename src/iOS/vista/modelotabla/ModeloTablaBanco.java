package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.EntidadBancaria;

public class ModeloTablaBanco extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;
	
	private String[] columnas = { "BANCO" };
	private List<EntidadBancaria> banco = new ArrayList<>();
	
	public void setBanco(List<EntidadBancaria> banco) {
		this.banco = banco;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return banco.size();
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
			return banco.get(r).getNombreBanco();
		default:
			break;
		}
		return null;
	}

}
