<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro</title>
<link rel="stylesheet" href="formularios.css">
</head>
<body>
    <header>
        <div>
            <a> <img alt="Logo de UTEC"
                src="images/utec-removebg-preview.png" />
            </a>
            <h1>Registro de Usuario</h1>
        </div>

    </header>

    <h1 style="margin-top: 2em">Rol de usuario</h1>

    <form id="registroForm" method="Post" name="tipoRegistro">
        <p
            style="text-align: center; margin-top: 20px; display: flex; align-items: center; justify-content: center;">
            <select id="tipoRegistro" name="tipoRegistro" style="width: 200px;">
                <option value="">Seleciona un rol...</option>
                <option value="SvRegistroAnalista">Analista</option>
                <option value="SvRegistroTutor">Tutor</option>
                <option value="SvRegistroEstudiante">Estudiante</option>
            </select>
        </p>

        <button type="submit">Enviar</button>
    </form>

    <form action="index.jsp" method="get">
        <button type="submit">Volver</button>
    </form>

    <script>
        document.getElementById('tipoRegistro').addEventListener('change', function() {
            var selectedOption = this.value;
            var form = document.getElementById('registroForm');
            form.action = selectedOption;
        });
    </script>

</body>
</html>
