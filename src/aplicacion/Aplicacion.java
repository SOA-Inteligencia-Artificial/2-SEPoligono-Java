package aplicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import sistemaexperto.IHecho;
import sistemaexperto.IHM;
import sistemaexperto.MotorInferencia;
import sistemaexperto.Regla;

public class Aplicacion implements IHM {
	
	public static void main(String[] args) {
        Aplicacion app = new Aplicacion();
        app.Run();
    }

    // Funcionamiento del programa, con el ejemplo de las pol�gonos
    public void Run() {
        // Creaci�n del motor
        System.out.println("** Creaci�n del motor **");
        MotorInferencia m = new MotorInferencia(this);
        
        // Agregar las reglas
        System.out.println("** Agregar las reglas **");
        m.AgregarRegla("R1 : IF (Orden=3(�Cu�l es el orden?)) THEN  Tri�ngulo");
        m.AgregarRegla("R2 : IF (Tri�ngulo AND �ngulo Recto(�La figura tiene al menos un �ngulo recto?)) THEN Tri�ngulo Rect�ngulo");
        m.AgregarRegla("R3 : IF (Tri�ngulo AND Lados Iguales=2(�Cu�ntos lados iguales tiene la figura?)) THEN Tri�ngulo Is�sceles");
        m.AgregarRegla("R4 : IF (Tri�ngulo rect�ngulo AND Tri�ngulo Is�sceles) THEN Tri�ngulo Rect�ngulo Is�sceles");
        m.AgregarRegla("R5 : IF (Tri�ngulo AND Lados Iguales=3(�Cu�ntos lados iguales tiene la figura?)) THEN Tri�ngulo Equil�tero");
        m.AgregarRegla("R6 : IF (Orden=4(�Cu�l es el orden?)) THEN Cuadril�tero");
        m.AgregarRegla("R7 : IF (Cuadril�tero AND Lados Paralelos=2(�Cu�ntos lados paralelos entre s� - 0, 2 o 4?)) THEN Trapecio");
        m.AgregarRegla("R8 : IF (Cuadril�tero AND Lados Paralelos=4(�Cu�ntos lados paralelos entre s� - 0, 2 o 4?)) THEN Paralelogramo");
        m.AgregarRegla("R9 : IF (Paralelogramo AND �ngulo Recto(�La figura tiene al menos un �ngulo recto?)) THEN Rect�ngulo");
        m.AgregarRegla("R10 : IF (Paralelogramo AND Lados Iguales=4(�Cu�ntos lados iguales tiene la figura?)) THEN Rombo");
        m.AgregarRegla("R11 : IF (Rect�ngulo AND Rombo THEN Cuadrado");
         
        // Resoluci�n
        while(true) {
            System.out.println("\n** Resoluci�n **");
            m.Resolver();
        }
    }
	
	// Solicita un valor entero al usuario
	@Override
	public int PedirValorEntero(String pregunta) {
		System.out.println(pregunta);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			return Integer.decode(br.readLine());
		}
		catch (Exception e) {
			return 0;
		}
	}
	
	// Solicita un valor booleano, con si (verdadero) o no (falso)
	// Los errores se ignoran (devuelve falso)
	@Override
	public boolean PedirValorBooleano(String pregunta) {
		try {
			System.out.println(pregunta + " (si, no)");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String res = br.readLine();
			return (res.equals("si"));
		}
		catch (Exception e) {
			return false;
		}
	}
	
	 // Muestra la lista de hechos de nivel >0 y por orden decreciente de nivel
    @Override
    public void MostrarHechos(ArrayList<IHecho> hechos) {
        String res = "Soluci�n(s) encontrada(s) : \n"; 
        Collections.sort(hechos,(IHecho f1, IHecho f2) -> {
            return Integer.compare(f2.Nivel(), f1.Nivel());
        });
        for(IHecho f : hechos) {
            if (f.Nivel() != 0) {
                res += f.toString() + "\n";
            }
        }
        System.out.println(res);
    }

    // Muestra las reglas contenidas en la base
    @Override
    public void MostrarReglas(ArrayList<Regla> reglas) {
        String res = "";
        for(Regla r : reglas) {
            res += r.toString() + "\n";
        }
        System.out.println(res);
    }
}
