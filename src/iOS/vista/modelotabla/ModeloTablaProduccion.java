package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.Produccion;

public class ModeloTablaProduccion extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1864475015388508652L;
	private String[] columnas = { "FECHA", "ENCARGADO", "SECTOR", "PROCESO", "COMENTARIOS", "MAQUINA", "DESPERDICIO",
			"CANT. DESPERDICIO" };
	private List<Produccion> producciones = new ArrayList<>();

	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return producciones.size();
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
			return EventosUtil.formatoFecha(producciones.get(r).getFechaRegistro());
		case 1:
			return producciones.get(r).getColaborador();
		case 2:
			return producciones.get(r).getSector();
		case 3:
			return producciones.get(r).getProceso();
		case 4:
			return producciones.get(r).getComentario();
		case 5:
			return producciones.get(r).getMaquina();
		case 6:
			if (producciones.get(r).isDesperdicio()) {
				return "\u2714";
			} else {
				return "\u274C";
			}
		case 7:
			return producciones.get(r).getCantidadDesperdicio();
		default:
			break;
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		Produccion row = producciones.get(r);
		if (0 == c) {

		} else if (1 == c) {

		} else if (2 == c) {

		} else if (3 == c) {

		} else if (4 == c) {
			row.setComentario((String) aValue.toString().toUpperCase());
		} else if (5 == c) {

		} else if (6 == c) {

		} else if (7 == c) {

		} else if (8 == c) {

		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 4:
			return true;
		default:
			return false;
		}
	}

}
