<?php
// Establecer el tipo de respuesta a JSON
header('Content-Type: application/json');

// Incluir el archivo de conexión
require("conexion1.php");

// Establecer la conexión
$conexion = mysqli_connect("localhost", "root", "", "automarket"); // Cambia estos valores según tu configuración

// Verificar si los parámetros necesarios están presentes
if (isset($_POST['nombre']) && isset($_POST['email']) && isset($_POST['contrasenia'])) {

    // Obtener los valores de los parámetros
    $nombre = $_POST['nombre'];
    $email = $_POST['email'];
    $contrasenia = $_POST['contrasenia'];

    // Escapar los valores para evitar inyecciones SQL
    $nombre = mysqli_real_escape_string($conexion, $nombre);
    $email = mysqli_real_escape_string($conexion, $email);
    $contrasenia = mysqli_real_escape_string($conexion, $contrasenia);

    // Realizar la consulta SQL para actualizar las credenciales
    $sql = "UPDATE usuario SET nombre = '$nombre', contrasenia = '$contrasenia' WHERE email = '$email'";

    // Ejecutar la consulta y verificar si la actualización fue exitosa
    if (mysqli_query($conexion, $sql)) {
        echo '{"resultado":"1"}';  // Credenciales actualizadas correctamente
    } else {
        echo '{"resultado":"0", "error": "Error al actualizar credenciales"}';  // Error en la actualización
    }

} else {
    echo '{"resultado":"0", "error": "Parámetros faltantes"}';  // Si los parámetros no están presentes
}

// Cerrar la conexión
mysqli_close($conexion);
?>