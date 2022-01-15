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
	private String[] columnas = { "PRODUCTO", "INDICACIONES", "ALTO cm", "ANCHO cm ", "ÁREA m2", "CANT.", "COSTO", "%",
			"PRECIO", "SUBTOTAL" };

	private List<PedidoDetalles> detalle = new ArrayList<>();

	public void setDetalle(List<PedidoDetalles> detalle) {
		this.detalle = detalle;
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
		return detalle.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		switch (c) {
		case 0:
			if (detalle.get(r).isProduccionFinalizada()) {
				return "\u2714 " + detalle.get(r).getProducto();
			} else {
				return "\u274C " + detalle.get(r).getProducto();
			}

		case 1:
			return detalle.get(r).getArchivo();
		case 2:
			return EventosUtil.separadorMiles(detalle.get(r).getMedidaAlto());
		case 3:
			return EventosUtil.separadorMiles(detalle.get(r).getMedidaAncho());
		case 4:
			return EventosUtil.separadorDecimales(detalle.get(r).getMedidaDetalle());
		case 5:
			return EventosUtil.separadorMiles((double) detalle.get(r).getCantidadDetalle());
		case 6:
			return EventosUtil.separadorMiles(detalle.get(r).getCosto());
		case 7:
			return EventosUtil.separadorDecimales(detalle.get(r).getPorcentajeSobreCosto());
		case 8:
			return EventosUtil.separadorMiles((double) detalle.get(r).getPrecioProducto());
		case 9:
			return EventosUtil.separadorMiles((double) detalle.get(r).getPrecioDetalle());
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		PedidoDetalles row = detalle.get(r);
		if (1 == c) {
			try {
				row.setArchivo(aValue.toString().toUpperCase());
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else if (2 == c) {
			try {
				String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "")
						.replace("\\scm", "").replace("\\sm", "").replace("\\s", "");
				row.setMedidaAlto(Double.parseDouble(nv));
				row.setMedidaDetalle(calcularArea(row.getMedidaAlto(), row.getMedidaAncho()));
				row.setPrecioDetalle((int) calcularSubtotal(row, row.getPrecioProducto()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return;
			}
		} else if (3 == c) {
			try {
				String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "")
						.replace("\\scm", "").replace("\\sm", "").replace("\\s", "");
				row.setMedidaAncho(Double.parseDouble(nv));
				row.setMedidaDetalle(calcularArea(row.getMedidaAlto(), row.getMedidaAncho()));
				row.setPrecioDetalle((int) calcularSubtotal(row, row.getPrecioProducto()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return;
			}
		} else if (4 == c) {
			try {
				String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "")
						.replace("\\scm", "").replace("\\sm", "").replace("\\s", "");
				row.setMedidaDetalle(Double.parseDouble(nv));
				row.setMedidaAncho(calcularAncho(row.getMedidaDetalle(), row.getMedidaAlto()));
				row.setPrecioDetalle((int) calcularSubtotal(row, row.getPrecioProducto()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return;
			}
		} else if (5 == c) {
			try {
				String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "")
						.replace("\\scm", "").replace("\\sm", "").replace("\\s", "");
				row.setCantidadDetalle(Integer.parseInt(nv));
				row.setPrecioDetalle((int) calcularSubtotal(row, row.getPrecioProducto()));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		} else if (8 == c) {
			try {
				String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "")
						.replace("\\scm", "").replace("\\sm", "").replace("\\s", "");
				row.setPrecioProducto(Integer.parseInt(nv));
				row.setPrecioDetalle((int) calcularSubtotal(row, row.getPrecioProducto()));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else if (9 == c) {
			try {
				String nv = aValue.toString().replace(",", "").replace(".", "").replace("cm", "").replace("m", "")
						.replace("\\scm", "").replace("\\sm", "").replace("\\s", "");
				row.setPrecioDetalle(Integer.parseInt(nv));
				row.setPrecioProducto((int) calcularPrecio(row, row.getPrecioDetalle()));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		fireTableDataChanged();
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

	private double calcularAncho(double area, double alto) {
		// TODO Auto-generated method stub

		double ancho = 0;

		try {
			ancho = (area * 10000) / alto;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ancho;
	}

	private double calcularPrecio(PedidoDetalles row, double subtotal) {
		double cantidad = 0;
		double area = 0;
		double precio = 0;

		try {
			area = row.getMedidaDetalle();
			cantidad = row.getCantidadDetalle();
			switch (row.getProducto().getTipoCobro()) {
			case "METRO CUADRADO":
				precio = subtotal / (area * cantidad);
				break;
			case "METRO LINEAL":
				precio = subtotal / (area * cantidad);
				break;
			case "UNIDAD":
				precio = subtotal / (cantidad);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return precio;
	}

	private double calcularSubtotal(PedidoDetalles row, double precioProducto) {
		double cantidad = 0;
		double area = 0;
		double subtotal = 0;

		try {
			area = row.getMedidaDetalle();
			cantidad = row.getCantidadDetalle();
			switch (row.getProducto().getTipoCobro()) {
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
		case 8:
			return true;
		case 9:
			return true;
		default:
			return false;
		}
	}
}