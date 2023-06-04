package sistemaexperto;

import java.util.logging.Level;
import java.util.logging.Logger;

//Clase que permtite crear los hechos, independientemente de su tipo
public class HechoFactory {

	static IHecho Hecho(IHecho h, MotorInferencia m) {
		try {
			IHecho nuevoHecho;
			Class clase = h.getClass();
			if (clase.equals(Class.forName("sistemaexperto.HechoEntero"))) {
				nuevoHecho = CrearHechoEntero(h, m);
			}
			else {
				nuevoHecho = CrearHechoBooleano(h, m);
			}
			return nuevoHecho;
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(HechoFactory.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	// Crea un hecho entero
	static IHecho CrearHechoEntero(IHecho f, MotorInferencia m) {
		int valor = m.PedirValorEntero(f.Pregunta());
		return new HechoEntero(f.Nombre(), valor, null, 0);
	}
	
	// Crea un hecho booleano
	static IHecho CrearHechoBooleano(IHecho f, MotorInferencia m) {
		boolean valorB = m.PedirValorBooleano(f.Pregunta());
		return new HechoBooleano(f.Nombre(), valorB, null, 0);
	}
	// CONTINUAMOS 
	static IHecho Hecho(String hechoStr) {
		hechoStr = hechoStr.trim();
		if (hechoStr.contains("=")) {
			// Existe el simbolo "=", se trata de un hecho entero
			hechoStr = hechoStr.replaceFirst("^" + "\\(", "");
			String[] nombreValorPregunta = hechoStr.split("[=()]");
			if (nombreValorPregunta.length >= 2) {
				String pregunta = null;
				if (nombreValorPregunta.length == 3) {
					pregunta = nombreValorPregunta[2].trim();
				}
				return new HechoEntero(nombreValorPregunta[0].trim(), Integer.parseInt(nombreValorPregunta[1].trim()), pregunta, 0);
			}
		}
		else {
			// Es un hecho booleano nombre[(pregunta)] o !nombre[(pregunta)]
			boolean valor = true;
			if (hechoStr.startsWith("!")) {
				valor = false;
				hechoStr = hechoStr.substring(1).trim();
			}
			// Split, tras eliminar el primer delimitador si es necesario : "("
			hechoStr = hechoStr.replaceFirst("^" + "\\(", "");
			String[] nombrePregunta = hechoStr.split("[()]");
			String pregunta = null;
			if (nombrePregunta.length == 2) {
				pregunta = nombrePregunta[1].trim();
			}
			return new HechoBooleano(nombrePregunta[0].trim(), valor, pregunta, 0);
		}
		return null;
	}

}
