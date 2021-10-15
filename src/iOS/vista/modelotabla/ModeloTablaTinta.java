package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.FechaUtil;
import iOS.modelo.entidades.Tinta;

public class ModeloTablaTinta extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7615729955805818086L;
	
	private String[] columnas = { "ESTADO", "COLOR", "REFERENCIA", "COLOCACION", "RETIRADA"};
	private List<Tinta> lista = new ArrayList<>();
	
	public void setLista(List<Tinta> lista) {
		this.lista = lista;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return lista.size();
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			if (lista.get(rowIndex).isEstado() == false) {
				return "INACTIVO";
			} else {
				return "EN USO";
			}
		case 1:
			return lista.get(rowIndex).getColor();
		case 2:
			return lista.get(rowIndex).getCodigoReferencia();
		case 3:
			return FechaUtil.convertirDateAString(lista.get(rowIndex).getFechaColocacion());
		case 4:
			return FechaUtil.convertirDateAString(lista.get(rowIndex).getFechaRetirada());
		default:
			break;
		}
		return null;
	}

}
