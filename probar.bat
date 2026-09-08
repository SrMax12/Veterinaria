@echo off
cd /d "%~dp0"

echo ============================================
echo  Prueba de conexion con MySQL (XAMPP)
echo ============================================
echo.

echo [1/2] Compilando...
if not exist bin mkdir bin
javac -encoding UTF-8 -cp "Servidor\lib\mysql-connector-j-8.0.33.jar" -d bin ^
  Dominio\App.java Dominio\Consulta.java Dominio\Dueño.java Dominio\Mascota.java Dominio\Vacuna.java Dominio\Veterinario.java ^
  Servidor\conexion\ConexionBD.java Servidor\dao\ConsultaDAO.java Servidor\dao\DuenoDAO.java Servidor\dao\MascotaDAO.java Servidor\dao\VacunaDAO.java Servidor\dao\VeterinarioDAO.java ^
  PruebaConexion.java
if errorlevel 1 goto error_compilar

echo [2/2] Ejecutando...
echo.
java -cp "bin;Servidor\lib\mysql-connector-j-8.0.33.jar" PruebaConexion
echo.
echo ============================================
echo  Si ves los datos de los duenos: todo OK.
echo  Si ves ERROR: revisa que MySQL este
echo  iniciado en el panel de XAMPP.
echo ============================================
goto fin

:error_compilar
echo.
echo ERROR: no se pudo compilar.
echo Revisa que tengas instalado el JDK, con java y javac.
echo.

:fin
pause
