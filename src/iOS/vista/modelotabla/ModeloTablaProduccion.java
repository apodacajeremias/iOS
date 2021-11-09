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
	private String[] columnas = {  "ENCARGADO", "FECHA", "PROCESO", "TIPO TRABAJO", "MAQUINA", "DESPERDICIO",
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
			return producciones.get(r).getColaborador();
		case 1:
			return EventosUtil.formatoFecha(producciones.get(r).getFechaRegistro());
		case 2:
			return producciones.get(r).getProceso();
		case 3:
			return producciones.get(r).getMaquina();
		case 4:
			return producciones.get(r).getTipoTrabajo();
		case 5:
			return producciones.get(r).isDesperdicio();
		case 6:
			return producciones.get(r).getCantidadDesperdicio();
		default:
			break;
		}
		return null;
	}

}
