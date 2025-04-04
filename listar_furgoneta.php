<?php
// Conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "automarket";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Consultar furgonetas
$sql = "SELECT v.marca, v.modelo, a.descripcion, a.precio 
        FROM vehiculo v
        JOIN anuncio a ON v.id = a.vehiculo_id
        WHERE v.id IN (SELECT id FROM furgoneta)";
$result = $conn->query($sql);

$furgonetas = array();

if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $furgonetas[] = $row;
    }
    echo json_encode($furgonetas); // Devolver las furgonetas como JSON
} else {
    echo json_encode([]); // No hay furgonetas
}

$conn->close();
?>
