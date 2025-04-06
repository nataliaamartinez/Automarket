<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);
include 'conexion.php';

// Mostrar datos recibidos
file_put_contents("log_registro.txt", json_encode($_POST) . PHP_EOL, FILE_APPEND);

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (isset($_POST['nombre']) && isset($_POST['email']) && isset($_POST['contrasenia'])) {
        $nombre = $_POST['nombre'];
        $email = $_POST['email'];
        $contrasenia = $_POST['contrasenia'];

        // Verificar si el usuario ya existe
        $stmt_check = $conn->prepare("SELECT id FROM usuario WHERE email = ?");
        if (!$stmt_check) {
            die("Error en la consulta de verificación: " . $conn->error);
        }
        $stmt_check->bind_param("s", $email);
        $stmt_check->execute();
        $stmt_check->store_result();

        if ($stmt_check->num_rows > 0) {
            echo "existe";
        } else {
            // Intentamos insertar el usuario
            $stmt = $conn->prepare("INSERT INTO usuario (nombre, email, contrasenia) VALUES (?, ?, ?)");
            if (!$stmt) {
                die("Error en la consulta de inserción: " . $conn->error);
            }
            $stmt->bind_param("sss", $nombre, $email, $contrasenia);
            
            if ($stmt->execute()) {
                echo "success";
            } else {
                die("Error al ejecutar la consulta: " . $stmt->error);
            }

            $stmt->close();
        }

        $stmt_check->close();
        $conn->close();
    } else {
        die("error_faltan_datos. Recibidos: " . json_encode($_POST));
    }
}
?>
