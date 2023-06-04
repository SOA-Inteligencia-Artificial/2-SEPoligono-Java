package sistemaexperto;

//Clase para los hechos enteros (como el número de aldos)
public class HechoEntero implements IHecho{

	// Nombre del hecho
	protected String nombre;
	@Override
	public String Nombre() {
		return nombre;
	}
	
	// Valor entero asociado
	protected int valor;
	@Override
	public Object Valor() {
		return valor;
	}
	
	// Nivel (0 para los hechos como entrada)
	protected int nivel;
	@Override
	public int Nivel() {
		return nivel;
	}
	
	public void setNivel(int l) {
		nivel = l;
	}
	
	// Pregunta que hay que hacer al usuario si es necesario
	protected String pregunta = "";
	@Override
	public String Pregunta() {
		return pregunta;
	}
	
	// Constructor
	public HechoEntero(String _nombre, int _valor, String _pregunta, int _nivel) {
		nombre = _nombre;
		valor = _valor;
		pregunta = _pregunta;
		nivel = _nivel;
	}
	
	// Métodos toString (para la visualización)
	@Override
	public String toString() {
		return nombre + "=" + valor + " (" + nivel + ")";
	}
}
