package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.Produccion;

public class ModeloTablaProduccionReporte extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1864475015388508652L;
	private String[] columnas = { "FECHA", "ENCARGADO", "SECTOR", "PROCESO", "PRODUCTO", "CANT.", "TIPO"};
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
			if (producciones.get(r).getPedido().getPedidoCarteleria() == true) {
				return producciones.get(r).getPedidoDetalle().getProducto().getDescripcion();
			}
			if (producciones.get(r).getPedido().getPedidoCostura() == true) {
				return producciones.get(r).getPedidoDetalleConfeccion().getProducto().getDescripcion();
			}
		case 5:
			if (producciones.get(r).getPedido().getPedidoCarteleria() == true) {
				return producciones.get(r).getPedidoDetalle().getCantidadDetalle();
			}
			if (producciones.get(r).getPedido().getPedidoCostura() == true) {
				return producciones.get(r).getPedidoDetalleConfeccion().getCantidadDetalle();
			}
		case 6:
			if (producciones.get(r).getPedido().getPedidoCarteleria() == true) {
				return "CARTELERIA";
			}
			if (producciones.get(r).getPedido().getPedidoCostura() == true) {
				return "CONFECCION";
			}
		case 7:
			
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
