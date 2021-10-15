package iOS.controlador.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

public class FechaUtil {

	private static Date fecha;
	private static SimpleDateFormat formato;
	final private static SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy");
	@SuppressWarnings("unused")
	final private static DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
	private static DateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm");
	private static MaskFormatter mascara;
	private static MaskFormatter mascaraH;

	public static MaskFormatter getMascara() {
		if (mascara == null) {

			try {
				mascara = new MaskFormatter("##/##/####");
				mascara.setPlaceholderCharacter('_');
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		return mascara;
	}

	public static MaskFormatter getMascaraH() {
		if (mascaraH == null) {

			try {
				mascaraH = new MaskFormatter("##:##:##");
				mascaraH.setPlaceholderCharacter('_');
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		return mascaraH;
	}

	public static Date stringADate(String fecString) {

		formato = new SimpleDateFormat("dd/MM/yyyy" + "HH:mm");
		formato.setLenient(false);
		try {
			fecha = formato.parse(fecString);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Formato de fecha invalido", "Aviso", 1);

		}
		return fecha;

	}

	public static Date stringADate1(String fecString) {
		formato = new SimpleDateFormat("dd/MM/yyyy");
		formato.setLenient(false);
		try {
			fecha = formato.parse(fecString);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Formato de fecha invalido", "Aviso", 1);

		}
		return fecha;

	}

	public static String dateAString(Date fecUtil) {
		formato = new SimpleDateFormat("dd/MM/yyyy");
		return formato.format(fecUtil);
	}

	public static String dateAString1(Date fecUtil) {
		formato = new SimpleDateFormat("HH:mm");
		return formato.format(fecUtil);
	}

	public static double diferenciaFecha1(Date desde, Date hasta) {

		double dif = (double) (hasta.getTime() - desde.getTime()) / (double) (1000 * 60 * 60);

		return dif;
	}

	public static Date convertirStringADate(String s) {
		FORMATO_FECHA.setLenient(false);
		try {
			return FORMATO_FECHA.parse(s);

		} catch (ParseException e) {
			return null;
		}
	}

	public static String convertirDateAString(Date d) {

		if (d == null)
			return null;

		return FORMATO_FECHA.format(d);

	}

	public static boolean fechaPosteriorAActual(String s) {
		Date hoy = new Date();
		return hoy.before(convertirStringADate(s));

	}

	public static boolean fechaAnteriorAActual(String s) {
		Date hoy = new Date();
		return hoy.after(convertirStringADate(s));

	}

	public static int diferenciaFecha(Date desde, Date hasta) {
		int dif = (int) Math.rint((hasta.getTime() - desde.getTime()) / (1000 * 60 * 60 * 24));
		int difa = dif / 365;
		System.out.println(dif);

		return difa;
	}

	public static String convertirHoraAString(Date fecha) {
		return HOUR_FORMAT.format(fecha);
	}

	SimpleDateFormat Formato = new SimpleDateFormat("dd-MM-yyyy");

	public String getFechaDC(JDateChooser jd) {
		if (jd.getDate() != null) {
			return Formato.format(jd.getDate());
		} else {
			return null;
		}
	}

	public Date StringADateDC(String fecha) {
		SimpleDateFormat formato_del_Texto = new SimpleDateFormat("dd-MM-yyyy");
		Date fechaE = null;
		try {
			fechaE = formato_del_Texto.parse(fecha);
			return fechaE;
		} catch (ParseException ex) {
			return null;
		}
	}

	public boolean fechaPosteriorDC(Date d) {
		Date hoy = new Date();
		return hoy.before(hoy);
	}

}
