<?php
// borrar_fav.php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');

// Verificar si el archivo de conexión existe antes de incluirlo
if (file_exists('conexion1.php')) {
    include 'conexion1.php';
} else {
    echo json_encode(array("success" => false, "message" => "Error de conexión: archivo conexion1.php no encontrado"));
    exit();
}

// Verificar que los parámetros 'favorito_id' y 'usuario_id' están presentes en la URL
if (isset($_GET['favorito_id']) && isset($_GET['usuario_id'])) {
    // Obtener los valores de los parámetros
    $favoritoId = $_GET['favorito_id'];
    $usuarioId = $_GET['usuario_id'];

    // Verificar que los parámetros son válidos (números enteros)
    if (!is_numeric($favoritoId) || !is_numeric($usuarioId)) {
        echo json_encode(array("success" => false, "message" => "Los parámetros 'favorito_id' y 'usuario_id' deben ser numéricos"));
        exit();
    }

    // Verificar que el favorito pertenece al usuario
    $stmt = $conn->prepare("SELECT usuario_id FROM favorito WHERE id = ?");
    $stmt->bind_param("i", $favoritoId);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows === 0) {
        // Si el favorito no existe
        echo json_encode(array("success" => false, "message" => "Favorito no encontrado"));
        exit();
    }

    // Verificar que el favorito pertenece al usuario
    $row = $result->fetch_assoc();
    if ($row['usuario_id'] != $usuarioId) {
        // Si el favorito no pertenece al usuario
        echo json_encode(array("success" => false, "message" => "No tienes permiso para borrar este favorito"));
        exit();
    }

    // Borrar el favorito de la base de datos
    $stmt = $conn->prepare("DELETE FROM favorito WHERE id = ? AND usuario_id = ?");
    $stmt->bind_param("ii", $favoritoId, $usuarioId);

    if ($stmt->execute()) {
        echo json_encode(array("success" => true, "message" => "Favorito borrado correctamente"));
    } else {
        echo json_encode(array("success" => false, "message" => "Error al borrar el favorito"));
    }

    $stmt->close();
} else {
    echo json_encode(array("success" => false, "message" => "Faltan parámetros"));
}

// Cerrar la conexión si $conn fue creado correctamente
if (isset($conn)) {
    $conn->close();
}
?>