<?php
// Configuración de la conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "automarket";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $dbname);

$nombre = $_POST['nombre'];
$contrania = $_POST['contrasenia'];

// Verificar la conexión
if ($conn->connect_error) {
    die(json_encode(["error" => "Conexión fallida: " . $conn->connect_error]));
}

// Consulta a la base de datos
$sql = "SELECT * FROM usuario u WHERE u.nombre LIKE '$nombre' and u.contrasenia like '$contrania'";
$result = $conn->query($sql);

$response = [];

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $response[] = $row;
    }
    // Devolver el resultado en formato JSON
    header('Content-Type: application/json');
    echo json_encode($response);
} else {
    echo "";
}

// Cerrar la conexión
$conn->close();

?>
