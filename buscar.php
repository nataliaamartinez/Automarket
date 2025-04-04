<?php
// Configuración de la conexión con la base de datos
$servidor = "localhost";  // Cambia esto por el servidor donde tienes la base de datos
$usuario = "root";  // Tu usuario de la base de datos
$contrasena = "";  // Tu contraseña de la base de datos
$basededatos = "automarket";  // El nombre de tu base de datos

// Crear la conexión
$conn = new mysqli($servidor, $usuario, $contrasena, $basededatos);

// Verificar la conexión
if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}

// Obtener el término de búsqueda desde la URL
if (isset($_GET['q'])) {
    $busqueda = $_GET['q'];
    
    // Evitar inyección SQL utilizando preparación de consultas
    $stmt = $conn->prepare("SELECT * FROM coche WHERE modelo LIKE ? OR marca LIKE ?");
    $likeBusqueda = "%" . $busqueda . "%";  // Buscar por el término proporcionado
    $stmt->bind_param("ss", $likeBusqueda, $likeBusqueda);
    
    // Ejecutar la consulta
    $stmt->execute();
    $resultado = $stmt->get_result();
    
    // Verificar si se encontraron resultados
    if ($resultado->num_rows > 0) {
        // Si se encontraron resultados, retornar los detalles
        $coches = array();
        while ($row = $resultado->fetch_assoc()) {
            $coches[] = $row;
        }
        
        // Devolver los resultados como JSON
        echo json_encode($coches);
    } else {
        // Si no se encontraron resultados
        echo json_encode(array("mensaje" => "No se encontraron resultados"));
    }

    // Cerrar la consulta
    $stmt->close();
} else {
    echo json_encode(array("mensaje" => "No se proporcionó término de búsqueda"));
}

// Cerrar la conexión
$conn->close();
?>
