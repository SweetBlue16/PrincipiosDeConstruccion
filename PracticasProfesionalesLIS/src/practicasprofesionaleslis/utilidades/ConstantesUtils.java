package practicasprofesionaleslis.utilidades;

/**
 * Autor: Todos
 * Fecha de creación: 29/05/2025
 * Descripción: Proporciona constantes para mensajes
 * títulos de ventana y expresiones regulares, reutilizables
 * a lo largo de todo el sistema.
 */
public class ConstantesUtils {
    public static final String TITULO_ERROR = "ERROR";
    public static final String TITULO_ADVERTENCIA = "ADVERTENCIA";
    public static final String TITULO_EXITO = "ÉXITO";
    public static final String TITULO_CONFIRMAR = "CONFIRMACIÓN";
    public static final String TITULO_REGISTRAR = "AÑADIR REGISTRO";
    public static final String TITULO_ACTUALIZAR = "ACTUALIZAR REGISTRO";
    public static final String TITULO_ELIMINAR = "ELIMINAR REGISTRO";
    public static final String TITULO_BUSCAR = "BUSCAR REGISTRO";
    public static final String TITULO_PANTALLA_PRINCIPAL = "MENÚ PRINCIPAL";
    public static final String TITULO_INICIO_SESION = "INICIO DE SESIÓN";
    public static final String TITULO_CIERRE_SESION = "CERRAR SESIÓN";
    public static final String TITULO_PERFIL = "MI PERFIL";
    public static final String TITULO_ARCHIVO = "ARCHIVO";
    public static final String TITULO_FUERA_RANGO_FECHAS = "FUERA DEL PERIODO ESCOLAR";
    public static final String TITULO_REQUISITO_NO_CUMPLIDO = "REQUISITO NO CUMPLIDO";
    
    public static final String ALERTA_ERROR_BD = "Su solicitud no puede ser procesada en este momento. Intente más tarde.";
    public static final String ALERTA_DATOS_INVALIDOS = "Los datos ingresados no son válidos. Por favor, verifique.";
    public static final String ALERTA_FORMATO_USUARIO_INVALIDO = "El formato de la matrícula o número de personal ingresado no es válido. Por favor, verifique.";
    public static final String ALERTA_ERROR_CARGAR_VENTANA = "No se pudo cargar la ventana, intente más tarde.";
    public static final String ALERTA_ERROR_CARGAR_INFORMACION = "No se pudo cargar la información, intente más tarde.";
    public static final String ALERTA_ERROR_CARGAR_IMAGEN = "No se pudo cargar la imagen, intente más tarde.";
    public static final String ALERTA_ERROR_CARGAR_DOCUMENTO = "No se pudo cargar el documento, intente más tarde.";
    public static final String ALERTA_REGISTRO_EXITOSO = "Registro guardado con éxito.";
    public static final String ALERTA_REGISTRO_FALLIDO = "Error al guardar el registro. Intente más tarde.";
    public static final String ALERTA_ACTUALIZACION_EXITOSA = "Registro actualizado con éxito.";
    public static final String ALERTA_ACTUALIZACION_FALLIDA = "Error al actualizar el registro. Intente más tarde.";
    public static final String ALERTA_BUSQUEDA_FALLIDA = "No se encontró el registro proporcionado. Por favor, verifique.";
    public static final String ALERTA_CREDENCIALES_INVALIDAS = "Usuario o contraseña incorrectos. Por favor, verifique.";
    public static final String ALERTA_USUARIO_OBLIGATORIO = "Usuario obligatorio";
    public static final String ALERTA_CONTRASEÑA_OBLIGATORIA = "Contraseña obligatoria";
    public static final String ALERTA_CERRAR_SESION = "¿Estás seguro(a) que deseas cerrar sesión?";
    public static final String ALERTA_CONFIRMAR_OPERACION = "¿Estás seguro(a) que deseas guardar los cambios?";
    public static final String ALERTA_DESCARGA_ARCHIVO_EXITOSA = "El archivo se descargó correctamente.";
    public static final String ALERTA_DESCARGA_ARCHIVO_FALLIDA = "La descarga del archivo no pudo completarse, intente nuevamente.";
    public static final String ALERTA_SELECCION_ARCHIVO_FALLIDA = "Seleccione un archivo válido.";
    public static final String ALERTA_ADVERTENCIA_SELECCION_ARCHIVO = "Debe seleccionar un archivo antes de subir la entrega.";
    public static final String ALERTA_SUBIDA_ARCHIVO_EXITOSA = "Documento subido correctamente.";
    public static final String ALERTA_SUBIDA_ARCHIVO_FALLIDA = "Hubo un problema al subir el archivo, intente más tarde.";
    public static final String ALERTA_FUERA_RANGO_FECHAS = "No puedes entregar documentos en este momento, estás fuera del rango de fechas.";
    public static final String ALERTA_DOCUMENTO_INICIAL_FALTANTE = "Debes haber entregado al menos un documento inicial.";
    public static final String ALERTA_DOCUMENTO_INTERMEDIO_FALTANTE = "Debes haber entregado al menos un documento intermedio.";
    
    public static final String REGEX_TELEFONO = "^\\d{10}$";
    public static final String REGEX_EMAIL = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
}
