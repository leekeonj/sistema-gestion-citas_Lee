# Diseño de Suite de Pruebas - Sistema de Gestión de Citas

## Documento de Planificación de Pruebas

### Fecha: Enero 2026
### Versión: 1.0

---

## 1. Alcance de las Pruebas

Este documento define la suite de pruebas automatizadas para el Sistema de Gestión de Citas, cubriendo las funcionalidades críticas identificadas en el backlog del proyecto.

## 2. Historias de Usuario Seleccionadas para Pruebas

### HU-01: Agendar Cita (CRÍTICA)
**Prioridad**: Alta  
**Justificación**: Funcionalidad core del sistema

**Casos de Prueba**:

| ID | Descripción | Tipo | Resultado Esperado |
|----|-------------|------|-------------------|
| TC-01-01 | Agendar con datos válidos | Positivo | Cita creada con código único |
| TC-01-02 | Códigos únicos | Funcional | Cada cita tiene código diferente |
| TC-01-03 | Múltiples citas | Integración | Sistema almacena correctamente |
| TC-01-04 | Estado inicial | Validación | Estado = "Pendiente" |
| TC-01-05 | Notificación | Funcional | Genera notificación |
| TC-01-06 | Diferentes horas | Funcional | Acepta múltiples horarios |
| TC-01-07 | Diferentes fechas | Funcional | Acepta múltiples fechas |

---

### HU-03: Cancelar Cita (CRÍTICA)
**Prioridad**: Alta  
**Justificación**: Gestión de cambios es esencial

**Casos de Prueba**:

| ID | Descripción | Tipo | Resultado Esperado |
|----|-------------|------|-------------------|
| TC-03-01 | Cancelar existente | Positivo | Estado cambia a "Cancelada" |
| TC-03-02 | Doble cancelación | Negativo | Retorna false |
| TC-03-03 | Código inexistente | Negativo | Retorna false |
| TC-03-04 | Notificación | Funcional | Genera notificación |
| TC-03-05 | Mantener datos | Integridad | Datos no se pierden |
| TC-03-06 | Múltiples cancelaciones | Integración | Cada una independiente |
| TC-03-07 | No afectar otras | Aislamiento | Solo cambia la seleccionada |
| TC-03-08 | Códigos inválidos | Validación | Maneja null y vacío |

---

### HU-04: Reprogramar Cita (CRÍTICA)
**Prioridad**: Alta  
**Justificación**: Flexibilidad para usuarios

**Casos de Prueba**:

| ID | Descripción | Tipo | Resultado Esperado |
|----|-------------|------|-------------------|
| TC-04-01 | Reprogramar exitosa | Positivo | Nuevo código generado |
| TC-04-02 | Cancela anterior | Funcional | Cita vieja = "Cancelada" |
| TC-04-03 | Nueva fecha/hora | Validación | Datos actualizados |
| TC-04-04 | Mantener paciente | Integridad | Mismos datos personales |
| TC-04-05 | Notificación | Funcional | Genera notificación |
| TC-04-06 | Código inexistente | Negativo | Retorna null |
| TC-04-07 | Múltiples reprogramaciones | Integración | Permite secuencia |
| TC-04-08 | Incrementa contador | Funcional | Total de citas aumenta |
| TC-04-09 | Mismo día | Validación | Permite misma fecha |
| TC-04-10 | No afectar otras | Aislamiento | Solo cambia la seleccionada |

---

## 3. Pruebas de Integración

### Disponibilidad de Horarios
**Objetivo**: Validar prevención de solapamientos

| ID | Descripción | Resultado Esperado |
|----|-------------|-------------------|
| TI-01 | Horario sin citas | Disponible = true |
| TI-02 | Horario con cita | Disponible = false |
| TI-03 | Libera al cancelar | Disponible = true después |
| TI-04 | Diferentes horas/misma fecha | Permite múltiples |
| TI-05 | Misma hora/diferentes fechas | Permite múltiples |
| TI-06 | Consulta por fecha | Retorna solo de esa fecha |
| TI-07 | Prevención solapamiento | No permite duplicados |

---

### Gestión de Usuarios
**Objetivo**: Validar CRUD de usuarios

| ID | Descripción | Resultado Esperado |
|----|-------------|-------------------|
| TU-01 | Agregar usuario | ID generado |
| TU-02 | IDs únicos | Cada usuario diferente ID |
| TU-03 | Obtener todos | Lista completa |
| TU-04 | Actualizar existente | Datos modificados |
| TU-05 | Actualizar inexistente | Retorna false |
| TU-06 | Desactivar usuario | Estado = "Inactivo" |
| TU-07 | Desactivar inexistente | Retorna false |

---

## 4. Matriz de Cobertura

### Objetivo de Cobertura: 70%

| Componente | Cobertura Objetivo | Prioridad |
|------------|-------------------|-----------|
| SistemaGestionCitas | 85% | Alta |
| Métodos de citas | 90% | Alta |
| Métodos de usuarios | 80% | Media |
| Métodos de horarios | 70% | Media |
| Métodos de notificaciones | 70% | Baja |

---

## 5. Estrategia de Ejecución

### Ejecución Local
```bash
mvn clean test
```

### Verificación de Cobertura
```bash
mvn clean verify
```

### Reporte Visual
```bash
mvn jacoco:report
# Abrir: target/site/jacoco/index.html
```

---

## 6. Pipeline CI/CD

### Triggers
- Push a main/master
- Pull requests

### Etapas
1. Checkout
2. Setup Java 17
3. Ejecutar pruebas
4. Generar reporte JaCoCo
5. Verificar cobertura mínima
6. Publicar artifacts

---

## 7. Criterios de Éxito

✅ **Todas las pruebas pasan**  
✅ **Cobertura ≥ 70%**  
✅ **Pipeline ejecuta automáticamente**  
✅ **Reporte generado correctamente**  
✅ **Funcionalidades críticas cubiertas**

---

## 8. Riesgos Identificados

| Riesgo | Probabilidad | Impacto | Mitigación |
|--------|--------------|---------|------------|
| Pruebas frágiles | Media | Alto | Usar @BeforeEach para aislamiento |
| Cobertura insuficiente | Baja | Alto | Verificación automática en CI |
| Falsos positivos | Media | Medio | Revisión de assertions |
| Tests lentos | Baja | Bajo | Optimizar setup |

---

## 9. Mantenimiento de Pruebas

### Frecuencia de Revisión: Mensual

**Acciones**:
1. Revisar pruebas fallidas
2. Actualizar casos según nuevos requisitos
3. Refactorizar tests duplicados
4. Mejorar cobertura de áreas débiles

---

## 10. Herramientas Utilizadas

- **Framework**: JUnit Jupiter 6.0.2
- **Cobertura**: JaCoCo 0.8.11
- **Build**: Maven 3.x
- **CI/CD**: GitHub Actions
- **Lenguaje**: Java 17

---

## Autor

**Laboratorio**: Práctica #6 - MDS  
**Docente**: Ing. John Javier Cruz Garzón Mgtr.  
**NRC**: 30746
