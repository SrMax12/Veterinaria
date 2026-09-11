@echo off
cd /d "%~dp0"

echo ============================================
echo  Lanzador Local: Dashboard Veterinaria (Casa)
echo ============================================
echo.

echo [1/2] Compilando modulos de codigo fuente...
if not exist bin mkdir bin

:: Compilacion masiva limpia usando tu conector real dentro de la carpeta lib
javac -encoding UTF-8 -cp "lib\mysql-connector-j-8.0.33.jar" -d bin src\dominio\*.java src\conexion\*.java src\dao\*.java src\forms\*.java src\App.java
if errorlevel 1 goto error_compilar

echo [2/2] Abriendo el Dashboard visual...
echo.
:: Lanzamos el sistema completo acoplando el Classpath nativo
java -cp "bin;lib\mysql-connector-j-8.0.33.jar" App
goto fin

:error_compilar
echo.
echo ERROR: No se pudo compilar. Revisa la sintaxis en VS Code.
echo.

:fin
pause
