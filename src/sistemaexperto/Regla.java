package sistemaexperto;

import java.util.ArrayList;
import java.util.StringJoiner;

//Representa una regla del sistema experto
public class Regla {

	// Lista de las premisas (parte izquierda)
	protected ArrayList<IHecho> premisas;
	public ArrayList<IHecho> getPremisas() {
		return premisas;
	}
	
	public void setPremisas(ArrayList<IHecho> _premisas) {
		premisas = _premisas;
	}
	
	// Conclusión de la regla (parte derecha)
	protected IHecho conclusion;
	public IHecho getConclusion() {
		return conclusion;
	}
	
	public void setConclusion(IHecho _conclusion) {
		conclusion = _conclusion;
	}
	
	// Nombre de la regla
	protected String nombre;
	public String getNombre() {
		return nombre;
	}
	
	// Contructor
	public Regla(String _nombre, ArrayList<IHecho> _premisas, IHecho _conclusion) {
		nombre = _nombre;
		premisas = _premisas;
		conclusion = _conclusion;
	}
	
	// Métodos que muestran la regla
	@Override
	public String toString() {
		String cadena = nombre + " : IF (";
		
		StringJoiner sj = new StringJoiner(" AND ");
		for (IHecho hecho: premisas) {
			sj.add(hecho.toString());
		}
		
		cadena += sj.toString() + ") THEN " + conclusion.toString();
		return cadena;
	}
}
