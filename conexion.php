?>
<?php
$servername = "localhost"; // Servidor (localhost en XAMPP)
$username = "root"; // Usuario de MySQL
$password = ""; // Contraseña (vacía en XAMPP)
$database = "automarket"; // Nombre de la base de datos

$conn = new mysqli($servername, $username, $password, $database);

// Verificar conexión
if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}
?>