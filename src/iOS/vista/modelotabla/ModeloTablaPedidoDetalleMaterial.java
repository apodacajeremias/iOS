
package iOS.vista.modelotabla;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.PedidoDetalleMaterial;

public class ModeloTablaPedidoDetalleMaterial extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -545738629065442036L;
	private String[] columnas = { "MATERIAL", "ALTO cm", "ANCHO cm ", "ÁREA m2", "CANT.", "COSTO", "SUBTOTAL" };

	private List<PedidoDetalleMaterial> materiales = new ArrayList<>();

	public void setMateriales(List<PedidoDetalleMaterial> materiales) {
		this.materiales = materiales;
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
		return materiales.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			return materiales.get(r).getMaterial();
		case 1:
			return EventosUtil.separadorMiles(materiales.get(r).getMedidaAlto());
		case 2:
			return EventosUtil.separadorMiles(materiales.get(r).getMedidaAncho());
		case 3:
			return EventosUtil.separadorDecimales(materiales.get(r).getMedidaDetalle());
		case 4:
			return EventosUtil.separadorMiles(materiales.get(r).getCantidadDetalle());
		case 5:
			return EventosUtil.separadorMiles(materiales.get(r).getCosto());
		case 6:
			return EventosUtil.separadorMiles(materiales.get(r).getSubtotal());
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		PedidoDetalleMaterial row = materiales.get(r);

		if (1 == c) {
			String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "")
					.replace("\\scm", "").replace("\\sm", "").replace("\\s", "");
			row.setMedidaAlto(Double.parseDouble(nv));
			row.setMedidaDetalle(calcularArea(row.getMedidaAlto(), row.getMedidaAncho()));
			row.setSubtotal((int) calcularSubtotal(row, row.getCosto()));
		} else if (2 == c) {
			String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "")
					.replace("\\scm", "").replace("\\sm", "").replace("\\s", "");
			row.setMedidaAncho(Double.parseDouble(nv));
			row.setMedidaDetalle(calcularArea(row.getMedidaAlto(), row.getMedidaAncho()));
			row.setSubtotal((int) calcularSubtotal(row, row.getCosto()));
		} else if (4 == c) {
			String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "")
					.replace("\\scm", "").replace("\\sm", "").replace("\\s", "");
			row.setCantidadDetalle(Integer.parseInt(nv));
			row.setSubtotal((int) calcularSubtotal(row, row.getCosto()));
		}
		fireTableDataChanged();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 1:
			return true;
		case 2:
			return true;
		default:
			return false;
		}
	}

	private double calcularSubtotal(PedidoDetalleMaterial row, double precioProducto) {
		double cantidad = 0;
		double area = 0;
		double subtotal = 0;

		try {
			area = row.getMedidaDetalle();
			cantidad = row.getCantidadDetalle();
			switch (row.getMaterial().getMaterial().getTipoCobro()) {
			case "METRO CUADRADO":
				subtotal = (precioProducto * area * cantidad);
				break;
			case "METRO LINEAL":
				subtotal = (precioProducto * area * cantidad);
				break;
			case "UNIDAD":
				subtotal = (precioProducto * cantidad);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return subtotal;

	}

	private double calcularArea(double alto, double ancho) {
		// TODO Auto-generated method stub
		double area = 0;
		try {
			area = (alto * ancho) / 10000;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return area;
	}
}