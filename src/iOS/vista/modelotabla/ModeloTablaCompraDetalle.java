package iOS.vista.modelotabla;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import iOS.modelo.entidades.CompraDetalle;
import iOS.modelo.entidades.Marca;

public class ModeloTablaCompraDetalle extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -545738629065442036L;

	private String[] columnas = {"MATERIAL","MARCA","TIPO","ANCHO CM","ALTO CM","AREA M2","CANT.","COSTO","MIN."," MIN. %","MAY. > 5","MAY. > 10","MAY. > 50","MAY. > 100","MAY. > 200","MAY. > 300","MAY. %"};

	private List<CompraDetalle> detalle = new ArrayList<>();

	public void setDetalle(List<CompraDetalle> detalle) {
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
			return detalle.get(r).getMaterial().getDescripcion();
		case 1:
			return detalle.get(r).getMarca();
		case 2:
			return detalle.get(r).getMaterial().getTipoCobro();
		case 3:
			return detalle.get(r).getMedidaAncho();
		case 4:
			return detalle.get(r).getMedidaAlto();
		case 5:
			return detalle.get(r).getMedidaDetalle();
		case 6:
			return detalle.get(r).getCantidadDetalle();
		case 7:
			return detalle.get(r).getCostoMaterial();
		case 8:
			return detalle.get(r).getPrecioMinorista();
		case 9:
			return detalle.get(r).getPorcentajeMinorista();
		case 10:
			return detalle.get(r).getPrecioMayorista5();
		case 11:
			return detalle.get(r).getPrecioMayorista10();
		case 12:
			return detalle.get(r).getPrecioMayorista50();
		case 13:
			return detalle.get(r).getPrecioMayorista100();
		case 14:
			return detalle.get(r).getPrecioMayorista200();
		case 15:
			return detalle.get(r).getPrecioMayorista300();
		case 16:
			return detalle.get(r).getPorcentajeMayorista();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int r, int c) {
		if (aValue == null) {
			return;
		}

		CompraDetalle row = detalle.get(r);
		String nv = aValue.toString().replace(",", ".").replace(".0", "");
		DecimalFormat df = new DecimalFormat("#,##");
		double precioMinorista = detalle.get(r).getPrecioMinorista();
		double porcentajeMinorista = detalle.get(r).getPorcentajeMinorista();
		double precioMayorista = detalle.get(r).getPrecioMayorista5();
		double porcentajeMayorista = detalle.get(r).getPorcentajeMayorista();
		double costo = detalle.get(r).getCostoMaterial();

		//Al seleccionar marca
		if(1 == c) {
			//			String filtro = aValue.toString();
			//			marcas = daoMarca.recuperarPorFiltro(filtro);
			//			for (int i = 0; i < marcas.size(); i++) {
			//				System.out.println("MARCA:"+marcas.get(i).getDescripcion()+" POSICION "+i);
			//			}
			//			
			//			if (marcas == null || marcas.size() > 1) {
			//				System.out.println("ERROR AL CARGAR MARCA, MT");
			//				return;
			//			}
			//			marca = marcas.get(0);
			row.setMarca((Marca) aValue);
		}else if(3 == c) {
			if (Double.parseDouble(nv) <= 0) {
				return;
			}
			row.setMedidaAncho(Double.parseDouble(nv));
		}
		else if(4 == c) {
			if (Double.parseDouble(nv) <= 0) {
				return;
			}
			row.setMedidaAlto(Double.parseDouble(nv));
		}
		else if(5 == c) {
			if (Double.parseDouble(nv) <= 0) {
				return;
			}
			row.setMedidaDetalle(Double.parseDouble(nv));
		}
		else if(6 == c) {
			if (Integer.parseInt(nv) <= 0) {
				return;
			}
			row.setCantidadDetalle(Integer.parseInt(nv));
		}

		//Al insertar costo del material
		else if(7 == c) {
			if (Integer.parseInt(nv) <= 0) {
				return;
			}
			row.setCostoMaterial(Integer.parseInt(nv));
			costo = Integer.parseInt(nv);
			row.setPrecioMinorista((int) ((1+(porcentajeMinorista/100d))*costo));	

			int nume = 7;
			int[] list = new int[nume];
			double precioMi = ((1+(porcentajeMinorista/100d))*costo);
			double precioMa = ((1+(porcentajeMayorista/100d))*costo);
			double suma = precioMa;
			double calculo = (precioMi - precioMa)/7;
			Math.floor(Double.parseDouble(calculo+""));
			for (int i = 0; i < list.length; i++) {
				suma = calculo + suma;
				int y = (int) suma; 
				int x = y - y % 100;
				list[i] = x;
				System.out.println("VALOR "+list[i]+" EN POSICION"+i);
			}
			row.setPrecioMayorista5(list[6]);
			row.setPrecioMayorista10(list[5]);
			row.setPrecioMayorista50(list[4]);
			row.setPrecioMayorista100(list[3]);
			row.setPrecioMayorista200(list[2]);
			row.setPrecioMayorista300((int) precioMa);
		}

		//Al insertar precio minorista
		else if(8 == c) {
			if (Integer.parseInt(nv) <= 0) {
				return;
			}
			precioMinorista = Integer.parseInt(nv);	
			if (costo >= precioMinorista) {
				return;
			}
			row.setPrecioMinorista(Integer.parseInt(nv));
			row.setPorcentajeMinorista(((precioMinorista-costo)/costo)*100d);

			System.out.println(Integer.parseInt(nv));
			System.out.println("1: "+Double.parseDouble(df.format(((precioMinorista-costo)/costo)*100d)));
			System.out.println("2: "+df.format(((precioMinorista-costo)/costo)*100d));
			System.out.println("3: "+((precioMinorista-costo)/costo)*100d);
			System.out.println("4: "+(precioMinorista-costo)/costo);
			System.out.println("-----------------------");
			System.out.println(" ");


		}

		//Al insertar porcentaje minorista
		else if(9 == c) {
			if (Double.parseDouble(nv) <= 0) {
				return;
			}
			row.setPorcentajeMinorista(Double.parseDouble(nv));
			porcentajeMinorista = Double.parseDouble(nv);
			row.setPrecioMinorista((int) ((1+(porcentajeMinorista/100d))*costo));
		}
		else if(10 == c) {
			if (Integer.parseInt(nv) <= 0) {
				return;
			}
			row.setPrecioMayorista5(Integer.parseInt(nv));
		}
		else if(11 == c) {
			if (Integer.parseInt(nv) <= 0) {
				return;
			}
			row.setPrecioMayorista10(Integer.parseInt(nv));
		}
		else if(12 == c) {
			if (Integer.parseInt(nv) <= 0) {
				return;
			}
			row.setPrecioMayorista50(Integer.parseInt(nv));
		}
		else if(13 == c) {
			if (Integer.parseInt(nv) <= 0) {
				return;
			}
			row.setPrecioMayorista100(Integer.parseInt(nv));
		}
		else if(14 == c) {
			if (Integer.parseInt(nv) <= 0) {
				return;
			}
			row.setPrecioMayorista200(Integer.parseInt(nv));
		}
		else if(15 == c) {
			if (Integer.parseInt(nv) <= 0) {
				return;
			}			
			precioMayorista = Integer.parseInt(nv);	
			if (costo >= precioMayorista) {
				return;
			}
			row.setPrecioMayorista300(Integer.parseInt(nv));
			row.setPorcentajeMayorista(((precioMayorista-costo)/costo)*100d);
			porcentajeMayorista = ((precioMayorista-costo)/costo)*100d;

			int nume = 7;
			int[] list = new int[nume];
			double precioMi = ((1+(porcentajeMinorista/100d))*costo);
			double precioMa = ((1+(porcentajeMayorista/100d))*costo);
			double suma = precioMa;
			double calculo = (precioMi - precioMa)/7;
			for (int i = 0; i < list.length; i++) {
				suma = calculo + suma;
				int y = (int) suma; 
				int x = y - y % 100;
				list[i] = x;
				System.out.println("VALOR "+list[i]+" EN POSICION"+i);

			}
			System.out.println("----------------------------------");
			System.out.println("   ");
//			row.setPrecioMayorista5(list[6]);
//			row.setPrecioMayorista10(list[5]);
//			row.setPrecioMayorista50(list[4]);
//			row.setPrecioMayorista100(list[3]);
//			row.setPrecioMayorista200(list[2]);
			
			row.setPrecioMayorista5(list[5]);
			row.setPrecioMayorista10(list[4]);
			row.setPrecioMayorista50(list[3]);
			row.setPrecioMayorista100(list[2]);
			row.setPrecioMayorista200(list[1]);
//			row.setPrecioMayorista300((int) precioMa);
		}
		else if(16 == c) {
			if (Double.parseDouble(nv) <= 0) {
				return;
			}
			row.setPorcentajeMayorista(Double.parseDouble(nv));
			porcentajeMayorista = Double.parseDouble(nv);

			int nume = 7;
			int[] list = new int[nume];
			double precioMi = ((1+(porcentajeMinorista/100d))*costo);
			double precioMa = ((1+(porcentajeMayorista/100d))*costo);
			double suma = precioMa;
			double calculo = (precioMi - precioMa)/7;
			for (int i = 0; i < list.length; i++) {
				suma = calculo + suma;
				int y = (int) suma; 
				int x = y - y % 100;
				list[i] = x;
				System.out.println("VALOR "+list[i]+" EN POSICION"+i);
			}
			System.out.println("----------------------------------");
			System.out.println("   ");
//			row.setPrecioMayorista5(list[6]);
//			row.setPrecioMayorista10(list[5]);
//			row.setPrecioMayorista50(list[4]);
//			row.setPrecioMayorista100(list[3]);
//			row.setPrecioMayorista200(list[2]);
			
			row.setPrecioMayorista5(list[5]);
			row.setPrecioMayorista10(list[4]);
			row.setPrecioMayorista50(list[3]);
			row.setPrecioMayorista100(list[2]);
			row.setPrecioMayorista200(list[1]);
			row.setPrecioMayorista300((int) precioMa);
		}
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 1:
			return true;
		case 3:
			return true;
		case 4:
			return true;
		case 6:
			return true;
		case 7:
			return true;
		case 8:
			return true;
		case 9:
			return true;
		case 10:
			return true;
		case 11:
			return true;
		case 12:
			return true;
		case 13:
			return true;
		case 14:
			return true;
		case 15:
			return true;
		case 16:
			return true;
		default:
			return false;
		}
	}
}

