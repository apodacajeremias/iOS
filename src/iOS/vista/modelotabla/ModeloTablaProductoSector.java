package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.Producto;

public class ModeloTablaProductoSector extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1864475015388508652L;
	private String[] columnas = { "PRODUCTO","COSTO","PRECIO" };
	private List<Producto> lista = new ArrayList<>();

	public void setProductos(List<Producto> lista) {
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
		return columnas[column];
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return lista.get(r);
		case 1:
			return lista.get(r).getCosto();
		case 2:
			return lista.get(r).getPrecioMaximo();
		default:
			break;
		}
		return null;
	}

}
