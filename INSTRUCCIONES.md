# Guía Rápida de Ejecución - Sistema de Gestión de Citas

## 🚀 Pasos para Ejecutar el Proyecto

### 1. Verificar Requisitos

Asegúrate de tener instalado:
- Java JDK 17 o superior
- Maven 3.6 o superior

Verificar versiones:
```bash
java -version
mvn -version
```

---

### 2. Ubicarse en el Directorio del Proyecto

```bash
cd sistema-gestion-citas
```

---

### 3. Compilar el Proyecto

```bash
mvn clean compile
```

**Resultado esperado**: `BUILD SUCCESS`

---

### 4. Ejecutar las Pruebas Unitarias

```bash
mvn test
```

**Resultado esperado**:
```
[INFO] Tests run: 32, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

### 5. Generar Reporte de Cobertura

```bash
mvn clean test jacoco:report
```

**Ver el reporte**:
1. Navega a: `target/site/jacoco/`
2. Abre el archivo: `index.html` en tu navegador

O en Linux/Mac:
```bash
open target/site/jacoco/index.html
```

En Windows:
```bash
start target/site/jacoco/index.html
```

---

### 6. Verificar Cobertura Mínima (70%)

```bash
mvn clean verify
```

Si la cobertura es inferior al 70%, el build fallará con un mensaje indicando las métricas.

---

### 7. Ejecutar la Aplicación GUI

#### Menú Principal
```bash
mvn exec:java -Dexec.mainClass="prototipos.MenuPrincipalGUI"
```

#### Módulos Individuales

**Agendar Cita**:
```bash
mvn exec:java -Dexec.mainClass="prototipos.AgendarCitaGUI"
```

**Cancelar Cita**:
```bash
mvn exec:java -Dexec.mainClass="prototipos.CancelarCitaGUI"
```

**Reprogramar Cita**:
```bash
mvn exec:java -Dexec.mainClass="prototipos.ReprogramarCitaGUI"
```

**Consultar Disponibilidad**:
```bash
mvn exec:java -Dexec.mainClass="prototipos.ConsultarDisponibilidadGUI"
```

---

## 📊 Interpretación del Reporte JaCoCo

Al abrir `target/site/jacoco/index.html` verás:

### Colores en el Código
- 🟢 **Verde**: Líneas cubiertas por pruebas
- 🔴 **Rojo**: Líneas NO cubiertas
- 🟨 **Amarillo**: Ramas parcialmente cubiertas

### Métricas Importantes
- **Instructions Coverage**: % de instrucciones ejecutadas
- **Branches Coverage**: % de ramificaciones probadas
- **Lines Coverage**: % de líneas ejecutadas
- **Methods Coverage**: % de métodos probados
- **Classes Coverage**: % de clases con pruebas

---

## 🔍 Ejecutar Pruebas Específicas

### Por Clase de Prueba
```bash
mvn test -Dtest=AgendarCitaTest
mvn test -Dtest=CancelarCitaTest
mvn test -Dtest=ReprogramarCitaTest
```

### Por Método Específico
```bash
mvn test -Dtest=AgendarCitaTest#debeAgendarCitaConDatosValidos
```

### Modo Verbose (más detalles)
```bash
mvn test -X
```

---

## 🐛 Resolución de Problemas

### Error: "Java version mismatch"
**Solución**: Verifica que estés usando Java 17:
```bash
export JAVA_HOME=/ruta/a/jdk-17
```

### Error: "Maven command not found"
**Solución**: Instala Maven o verifica PATH:
```bash
export PATH=$PATH:/ruta/a/maven/bin
```

### Error: "Tests failing"
**Solución**: Limpia y vuelve a compilar:
```bash
mvn clean compile test
```

### Error: "Cobertura insuficiente"
**Solución**: Revisa el reporte para identificar código sin cubrir:
```bash
mvn jacoco:report
# Abrir target/site/jacoco/index.html
```

---

## 📁 Estructura de Archivos Generados

Después de ejecutar las pruebas:
```
target/
├── classes/                    # Clases compiladas
├── test-classes/              # Pruebas compiladas
├── surefire-reports/          # Reportes XML de pruebas
│   ├── TEST-*.xml
│   └── *.txt
├── jacoco.exec                # Datos de ejecución JaCoCo
└── site/
    └── jacoco/               # Reporte HTML de cobertura
        ├── index.html        # ⭐ Página principal
        ├── prototipos/       # Cobertura por paquete
        └── jacoco-sessions.html
```

---

## ✅ Checklist de Validación

Antes de entregar, verifica:

- [ ] El proyecto compila sin errores: `mvn compile`
- [ ] Todas las pruebas pasan: `mvn test`
- [ ] La cobertura es ≥ 70%: `mvn verify`
- [ ] El reporte HTML se genera correctamente
- [ ] El pipeline CI/CD está configurado (`.github/workflows/ci.yml`)
- [ ] El README está completo
- [ ] El archivo .gitignore está presente

---

## 📝 Comandos Útiles Resumidos

```bash
# Limpiar, compilar y probar
mvn clean test

# Generar reporte completo
mvn clean verify jacoco:report

# Ver solo estadísticas de cobertura
mvn jacoco:check

# Ejecutar aplicación
mvn exec:java -Dexec.mainClass="prototipos.MenuPrincipalGUI"

# Empaquetar proyecto
mvn package

# Instalar en repositorio local
mvn install
```

---

## 🎯 Objetivos del Laboratorio

1. ✅ Suite de pruebas automatizadas implementada
2. ✅ Pipeline CI/CD configurado
3. ✅ Reporte de cobertura generado
4. ✅ Cobertura mínima del 70% alcanzada
5. ✅ Historias de usuario críticas probadas

---

## 📞 Soporte

Para preguntas sobre el laboratorio:
- **Docente**: Ing. John Javier Cruz Garzón Mgtr.
- **Materia**: Metodologías de Desarrollo de Software
- **NRC**: 30746

---

**¡Éxito con tu laboratorio! 🚀**
