package iOS.controlador.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class EscribirFichero {
	public static void main(String[] args) throws IOException, URISyntaxException {
		File directorio = new File("/sistema/backup_imagenes");
		if (!directorio.exists()) {
			if (directorio.mkdirs()) {
				System.out.println("Directorio creado");
			} else {
				System.out.println("Error al crear directorio");
			}
		}
		System.out.println(directorio.getPath());
	}
}