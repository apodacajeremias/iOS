package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.PedidoDetalles;

public class ModeloTablaPedidoDetalle extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -545738629065442036L;
	private String[] columnas = {"PRODUCTO","INDICACIONES","ALTO","ANCHO","ÁREA","CANT.","PRECIO","SUBTOTAL"};

	private List<PedidoDetalles> detalle = new ArrayList<>();

	public void setDetalle(List<PedidoDetalles> detalle) {
		this.detalle = detalle;
		fireTableDataChanged();
	}

	@Override
	public String getColumnName(int c) {

		return columnas[c];
	}

	public int getColumnCount() {

		return columnas.length;
	}
	@Override
	public int getRowCount() {
		return detalle.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return detalle.get(r).getProducto();
		case 1:
			return detalle.get(r).getArchivo();
		case 2:
			return EventosUtil.separadorMiles(detalle.get(r).getMedidaAlto())+" cm";
		case 3:
			return EventosUtil.separadorMiles(detalle.get(r).getMedidaAncho())+" cm";
		case 4:
			return EventosUtil.separadorDecimales(detalle.get(r).getMedidaDetalle())+" m2";
		case 5:
			return EventosUtil.separadorMiles((double) detalle.get(r).getCantidadDetalle());
		case 6:
			return EventosUtil.separadorMiles((double) detalle.get(r).getPrecioProducto());
		case 7:
			return EventosUtil.separadorMiles((double) detalle.get(r).getPrecioDetalle());
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		PedidoDetalles row = detalle.get(r);		
		if(1 == c) {
			try {
				row.setArchivo((String) aValue.toString().toUpperCase());
			} catch (Exception e) {
				return;
			}
		}
		else if(2 == c) {
			try {
				String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "").replace("\\scm","").replace("\\sm","").replace("\\s","");
				row.setMedidaAlto(Double.parseDouble((String) nv));
			} catch (NumberFormatException e) {
				return;
			}
		}
		else if(3 == c) {

			try {
				String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "").replace("\\scm","").replace("\\sm","").replace("\\s","");
				row.setMedidaAncho(Double.parseDouble((String) nv));
			} catch (NumberFormatException e) {
				return;
			}
		}
		else if(4 == c) {
			try {
				String nv = aValue.toString().replace(",", ".").replace("cm", "").replace("m", "").replace("\\scm","").replace("\\sm","").replace("\\s","");
				row.setMedidaDetalle(Double.parseDouble((String) nv));
			} catch (NumberFormatException e) {
				return;
			}
		}
		else if(5 == c) {
			try {
				String nv = aValue.toString().replace(".", "").replace(",", "").replace("\\s","");
				row.setCantidadDetalle(Integer.parseInt((String) nv));
			} catch (NumberFormatException e) {
				return;
			}
		}
		else if(6 == c) {
			try {
				String nv = aValue.toString().replace(".", "").replace("\\s","");
				row.setPrecioProducto(Integer.parseInt((String) nv));
			} catch (NumberFormatException e) {
				return;
			}
		}
		else if(7 == c) {
			try {
				String nv = aValue.toString().replace(".", "").replace("\\s","");
				row.setPrecioDetalle(Integer.parseInt((String) nv));
			} catch (NumberFormatException e) {
				return;
			}
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 1:
			return true;
		case 2:
			return true;
		case 3:
			return true;
		case 5:
			return true;
		case 6:
			return true;
		case 7:
			return true;
		default:
			return false;
		}
	}
}