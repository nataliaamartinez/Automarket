<?php
include 'conexion.php';

// Registra los datos POST recibidos para la depuración
error_log("Datos POST recibidos: " . print_r($_POST, true));

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Obtener y verificar los parámetros
    $marca = isset($_POST['marca']) ? $_POST['marca'] : null;
    $modelo = isset($_POST['modelo']) ? $_POST['modelo'] : null;
    $anio = isset($_POST['anio']) ? $_POST['anio'] : null;
    $kilometraje = isset($_POST['kilometraje']) ? $_POST['kilometraje'] : null;
    $cilindrada = isset($_POST['cilindrada']) ? $_POST['cilindrada'] : null;  // Cilindrada específica para motos
    $precio = isset($_POST['precio']) ? $_POST['precio'] : null;
    $descripcion = isset($_POST['descripcion']) ? $_POST['descripcion'] : null;
    $vendedor_id = isset($_POST['vendedor_id']) ? $_POST['vendedor_id'] : null;

    // Depuración de los valores recibidos
    error_log("marca: $marca, modelo: $modelo, anio: $anio, kilometraje: $kilometraje, cilindrada: $cilindrada, precio: $precio, descripcion: $descripcion, vendedor_id: $vendedor_id");

    // Verificar que todos los campos estén completos
    $missing_fields = [];
    if (empty($marca)) $missing_fields[] = "marca";
    if (empty($modelo)) $missing_fields[] = "modelo";
    if (empty($anio)) $missing_fields[] = "anio";
    if (empty($kilometraje)) $missing_fields[] = "kilometraje";
    if (empty($cilindrada)) $missing_fields[] = "cilindrada";  // Verificación de la cilindrada para motos
    if (empty($precio)) $missing_fields[] = "precio";
    if (empty($descripcion)) $missing_fields[] = "descripcion";
    if (empty($vendedor_id)) $missing_fields[] = "vendedor_id";

    if (count($missing_fields) > 0) {
        echo "error_faltan_datos. Faltan: " . implode(", ", $missing_fields);
        exit;
    }

    // Comenzar la transacción
    $conn->begin_transaction();

    try {
        // Insertar en la tabla Vehiculo
        $stmt1 = $conn->prepare("INSERT INTO vehiculo (marca, modelo, anio, kilometraje) VALUES (?, ?, ?, ?)");
        $stmt1->bind_param("ssii", $marca, $modelo, $anio, $kilometraje);
        $stmt1->execute();
        $vehiculo_id = $stmt1->insert_id;
        $stmt1->close();

        // Insertar en la tabla Moto (tabla heredada de Vehiculo)
        $stmt2 = $conn->prepare("INSERT INTO moto (id, cilindrada) VALUES (?, ?)");
        $stmt2->bind_param("id", $vehiculo_id, $cilindrada);
        $stmt2->execute();
        $stmt2->close();

        // Insertar en la tabla Anuncio
        $stmt3 = $conn->prepare("INSERT INTO anuncio (vehiculo_id, precio, descripcion, vendedor_id) VALUES (?, ?, ?, ?)");
        $stmt3->bind_param("idss", $vehiculo_id, $precio, $descripcion, $vendedor_id);
        $stmt3->execute();
        $stmt3->close();

        // Confirmar la transacción
        $conn->commit();
        echo "success";  // Esto se mostrará en el navegador
    } catch (Exception $e) {
        $conn->rollback();
        error_log("Error: " . $e->getMessage());  // Logueo del error completo
        echo "error: " . $e->getMessage();  // Esto aparecerá en el navegador
    }

    $conn->close();
}
?>