# Sistema de Gestión de Citas - Proyecto con Pruebas Automatizadas

## Descripción
Sistema de gestión de citas médicas desarrollado con Java 17 y Swing, implementando pruebas automatizadas con JUnit 6 (Jupiter) y análisis de cobertura con JaCoCo.

## Tecnologías Utilizadas
- **Java**: 17
- **Framework de Pruebas**: JUnit Jupiter 6.0
- **Herramienta de Cobertura**: JaCoCo 0.8.11
- **Build Tool**: Maven 3.x
- **GUI Framework**: Java Swing

## Estructura del Proyecto

```
sistema-gestion-citas/
├── pom.xml                          # Configuración Maven
├── .github/
│   └── workflows/
│       └── ci.yml                   # Pipeline CI/CD
├── src/
│   ├── main/
│   │   └── java/
│   │       └── prototipos/
│   │           ├── SistemaGestionCitas.java
│   │           ├── AgendarCitaGUI.java
│   │           ├── CancelarCitaGUI.java
│   │           ├── ReprogramarCitaGUI.java
│   │           ├── ConsultarDisponibilidadGUI.java
│   │           ├── GestionarUsuariosGUI.java
│   │           ├── HistorialCitasGUI.java
│   │           ├── MarcarAtendidaGUI.java
│   │           ├── NotificacionesGUI.java
│   │           ├── PrevencionSolapamientosGUI.java
│   │           ├── ReportesGUI.java
│   │           ├── GestionarHorariosGUI.java
│   │           └── MenuPrincipalGUI.java
│   └── test/
│       └── java/
│           └── prototipos/
│               ├── AgendarCitaTest.java
│               ├── CancelarCitaTest.java
│               ├── ReprogramarCitaTest.java
│               ├── DisponibilidadHorariosTest.java
│               └── GestionUsuariosTest.java
└── README.md
```

## Historias de Usuario Probadas

### HU-01: Agendar Cita
**Criterios de Aceptación:**
- ✅ Agendar cita con datos válidos
- ✅ Generar código único
- ✅ Validar datos obligatorios
- ✅ Validar formato de email
- ✅ Registrar con estado "Pendiente"

**Suite de Pruebas:** `AgendarCitaTest.java`
- 7 pruebas unitarias
- Cobertura de casos positivos y negativos

### HU-03: Cancelar Cita
**Criterios de Aceptación:**
- ✅ Cancelar cita existente
- ✅ Cambiar estado a "Cancelada"
- ✅ Prevenir cancelación de citas ya canceladas
- ✅ Generar notificación
- ✅ Manejar códigos inexistentes

**Suite de Pruebas:** `CancelarCitaTest.java`
- 8 pruebas unitarias
- Validación de estados y excepciones

### HU-04: Reprogramar Cita
**Criterios de Aceptación:**
- ✅ Reprogramar cita existente
- ✅ Cancelar cita anterior
- ✅ Crear nueva cita con nueva fecha/hora
- ✅ Generar nuevo código
- ✅ Mantener datos del paciente

**Suite de Pruebas:** `ReprogramarCitaTest.java`
- 10 pruebas unitarias
- Verificación de integridad de datos

### Pruebas de Integración
**Disponibilidad de Horarios:**
- ✅ Verificar disponibilidad
- ✅ Prevenir solapamientos
- ✅ Consultar horarios ocupados

**Gestión de Usuarios:**
- ✅ Agregar usuarios
- ✅ Actualizar información
- ✅ Desactivar usuarios

## Instalación y Configuración

### Prerrequisitos
- Java JDK 17 o superior
- Maven 3.6 o superior
- Git

### Clonar el Repositorio
```bash
git clone <url-del-repositorio>
cd sistema-gestion-citas
```

### Compilar el Proyecto
```bash
mvn clean compile
```

### Ejecutar las Pruebas
```bash
mvn test
```

### Generar Reporte de Cobertura
```bash
mvn clean test jacoco:report
```

El reporte HTML se generará en:
```
target/site/jacoco/index.html
```

### Verificar Cobertura Mínima (70%)
```bash
mvn clean verify
```

## Pipeline CI/CD

El proyecto incluye un pipeline de GitHub Actions que se ejecuta automáticamente en:
- Push a ramas `main` o `master`
- Pull Requests

### Etapas del Pipeline:
1. **Checkout**: Obtención del código
2. **Setup Java**: Configuración de Java 17
3. **Install & Test**: Instalación de dependencias y ejecución de pruebas
4. **Coverage Report**: Generación de reporte de cobertura con JaCoCo

### Archivo de Configuración
`.github/workflows/ci.yml`

## Ejecutar la Aplicación

### Menú Principal
```bash
mvn exec:java -Dexec.mainClass="prototipos.MenuPrincipalGUI"
```

### Módulos Individuales
```bash
# Agendar Cita
mvn exec:java -Dexec.mainClass="prototipos.AgendarCitaGUI"

# Cancelar Cita
mvn exec:java -Dexec.mainClass="prototipos.CancelarCitaGUI"

# Reprogramar Cita
mvn exec:java -Dexec.mainClass="prototipos.ReprogramarCitaGUI"
```

## Métricas de Cobertura

### Objetivo de Cobertura: 70%

El proyecto está configurado con JaCoCo para garantizar:
- **Cobertura de Líneas**: Mínimo 70%
- **Cobertura de Métodos**: Mínimo 70%

Si la cobertura es inferior al 70%, el build fallará.

## Tipos de Pruebas Implementadas

### 1. Pruebas Unitarias
- Validación de métodos individuales
- Casos positivos y negativos
- Manejo de excepciones

### 2. Pruebas de Integración
- Interacción entre componentes
- Validación de flujos completos
- Prevención de solapamientos

### 3. Pruebas Funcionales
- Criterios de aceptación de historias de usuario
- Validación de reglas de negocio

## Resultados Esperados

Al ejecutar las pruebas:
```
[INFO] Tests run: 32, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] --- jacoco-maven-plugin:0.8.11:report (report) @ sistema-gestion-citas ---
[INFO] Loading execution data file target/jacoco.exec
[INFO] Analyzed bundle 'sistema-gestion-citas' with X classes
```

## Estructura de las Pruebas

Cada suite de pruebas sigue el patrón AAA:
- **Arrange**: Preparación del escenario
- **Act**: Ejecución de la acción
- **Assert**: Verificación de resultados

### Ejemplo:
```java
@Test
@DisplayName("Debe agendar cita con datos válidos")
void debeAgendarCitaConDatosValidos() {
    // Arrange
    String nombre = "Juan Pérez";
    String email = "juan@mail.com";
    
    // Act
    String codigo = sistema.agendarCita(nombre, email, ...);
    
    // Assert
    assertNotNull(codigo);
    assertEquals(4, codigo.length());
}
```

## Análisis de Cobertura

### Revisar Reporte HTML
1. Ejecutar: `mvn clean test`
2. Abrir: `target/site/jacoco/index.html`
3. Navegar por paquetes y clases
4. Identificar:
   - ✅ Líneas en verde (cubiertas)
   - ❌ Líneas en rojo (no cubiertas)
   - 🟨 Líneas en amarillo (parcialmente cubiertas)

## Buenas Prácticas Implementadas

1. **Nombres Descriptivos**: Los tests tienen nombres que describen claramente lo que prueban
2. **Aislamiento**: Cada prueba es independiente (uso de `@BeforeEach`)
3. **Orden Lógico**: Uso de `@Order` para ejecutar tests en secuencia lógica
4. **Documentación**: Cada suite incluye descripción de criterios de aceptación
5. **Cobertura Mínima**: Verificación automática del 70% de cobertura

## Contribuir

Para agregar nuevas pruebas:

1. Crear archivo de prueba en `src/test/java/prototipos/`
2. Usar anotación `@DisplayName` para describir el propósito
3. Implementar el patrón AAA
4. Ejecutar `mvn test` para verificar
5. Revisar cobertura con `mvn jacoco:report`

## Autor

**Estudiante**: [Keonjae Lee]
**Materia**: Metodologías de Desarrollo de Software
**NRC**: 30746
**Docente**: Ing. John Javier Cruz Garzón Mgtr.

## Licencia

Proyecto académico - ESPE 2025
