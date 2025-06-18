package practicasprofesionaleslis.utilidades;

import java.util.regex.Pattern;

/**
 * Autor: Todos
 * Fecha de creación: 27/05/2025
 * Descripción: Proporciona métodos auxiliares y
 * constantes para validar las credenciales de los
 * diferentes actores del sistema.
 */
public class ValidacionUtils {
    private static final String REGEX_ESTUDIANTE = "^S\\d{8}$";
    private static final String REGEX_COORDINADOR = "^C\\d{3}$";
    private static final String REGEX_PROFESOR = "^P\\d{3}$";
    private static final String REGEX_EVALUADOR = "^E\\d{3}$";
    
    public static boolean validarIDEstudiante(String id) {
        return Pattern.matches(REGEX_ESTUDIANTE, id);
    }
    
    public static boolean validarIDCoordinador(String id) {
        return Pattern.matches(REGEX_COORDINADOR, id);
    }
    
    public static boolean validarIDProfesor(String id) {
        return Pattern.matches(REGEX_PROFESOR, id);
    }
    
    public static boolean validarIDEvaluador(String id) {
        return Pattern.matches(REGEX_EVALUADOR, id);
    }
}
