<?php
/**
 * retornarConexion: retorna una conexión a un servidor
 * que aloja una base de datos, a partir de  nombre del servidor
 *  ($server), cuenta de usuario ($usuario y $clave) y nombre
 * de la base de datos ($base)
 */
function retornarConexion(){
    /*Ojo!! estos datos deberían ser mejor, constantes en un
    fichero aparte */
  $server="localhost";
  $usuario="root";
  $clave="";
  $base="automarket";
  $con=mysqli_connect($server,$usuario,$clave,$base) or die("problemas");
  mysqli_set_charset($con,'utf8');
  return $con;
}

?>