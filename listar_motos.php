<?php
header('Content-Type: application/json');

// Incluye el archivo de conexión
require('conexion1.php');
$conexion = retornarConexion();  // Usamos la función retornarConexion para obtener la conexión

// Realiza la consulta para obtener la información de todos los coches
$sql = "SELECT v.marca, v.modelo, a.descripcion, a.precio 
        FROM vehiculo v
        JOIN anuncio a ON v.id = a.vehiculo_id
        WHERE v.id IN (SELECT m.id FROM moto m)";  // Se asegura de que solo se listan los coches

// Ejecutar la consulta
$datos = mysqli_query($conexion, $sql);

// Verifica si la consulta fue exitosa
if (!$datos) {
    echo json_encode(["error" => "Error al ejecutar la consulta: " . mysqli_error($conexion)]);
    exit;
}

// Extraer los resultados como un array asociativo
$resultado = mysqli_fetch_all($datos, MYSQLI_ASSOC);

// Devuelve los resultados como JSON
echo json_encode($resultado);

// Cierra la conexión
mysqli_close($conexion);
?>