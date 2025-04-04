<?php
header('Content-Type: application/json');

// Incluye el archivo de conexión
require('conexion1.php');
$conexion = retornarConexion();  // Usamos la función retornarConexion para obtener la conexión

// Realiza la consulta para obtener la información de los vehículos favoritos
$sql = "SELECT v.marca, v.modelo, a.descripcion, a.precio 
        FROM Vehiculo v
        JOIN Anuncio a ON v.id = a.vehiculo_id
        WHERE a.vendedor_id = 1";  // Cambia '1' al ID correcto del usuario si es necesario

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
