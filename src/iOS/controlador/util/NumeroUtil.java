package iOS.controlador.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumeroUtil {

	private static NumberFormat nf = NumberFormat.getInstance(Locale.US);
	
	DecimalFormat formatea = new DecimalFormat("###,###.##");

	public static String deIntAString(Integer i) {
		return nf.format(i);
	}

	public static Integer deStringAInt(String string) {
		try {
			return nf.parse(string).intValue();
		} catch (Exception e) {
			return null;
		}
	}

	public static String deDoubleAString(Double d) {
		return nf.format(d);
	}

	public static Double deStringADouble(String string) {
		try {
			return nf.parse(string).doubleValue();
		} catch (Exception e) {
			return null;
		}
	}

	public static Integer deDoubleAInteger(double d) {
		return (int) d;
	}
}
