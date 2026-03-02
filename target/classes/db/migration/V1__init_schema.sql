-- ============================================================
-- V1__init_schema.sql
-- Sistema de Malla de Turnos
-- Arquitectura: Microservicio Hexagonal | PostgreSQL
-- ============================================================

-- ============================================================
-- AREAS DE TRABAJO
-- ============================================================
CREATE TABLE areas (
    id          BIGSERIAL    PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(350) NOT NULL
);

CREATE TABLE cargos (
    id          BIGSERIAL    PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(350) NOT NULL
);

-- ============================================================
-- USUARIOS DEL SISTEMA
-- Roles: ADMINISTRADOR | SUPERVISOR | FUNCIONARIO
-- ============================================================
CREATE TABLE usuarios (
    id             BIGSERIAL    PRIMARY KEY,
    nombre        VARCHAR(100) NOT NULL,
    tipo_identificacion VARCHAR(3)  NOT NULL,
    numero_identificacion      BIGINT NOT NULL UNIQUE,
    contrasena BIGINT NOT NULL,
    rol            VARCHAR(15)  NOT NULL CHECK (rol IN ('ADMINISTRADOR','SUPERVISOR','FUNCIONARIO')),
    area_id        BIGINT       REFERENCES areas(id),  -- NULL para ADMINISTRADOR
    cargo_id         BIGINT       REFERENCES cargos(id),
    activo         BOOLEAN      NOT NULL DEFAULT TRUE
);

-- ============================================================
-- TURNOS POR AREA
-- ============================================================
CREATE TABLE turnos (
    id          BIGSERIAL    PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    hora_inicio TIME         NOT NULL,
    hora_fin    TIME         NOT NULL,
    hora_almuerzo TIME         NOT NULL,
    hora_break TIME         NOT NULL
);

-- ============================================================
-- ASIGNACIONES DE TURNO (La Malla)
-- Una persona solo puede tener UN turno por dia.
-- turno_id NULL = día libre / descanso.
-- ============================================================
CREATE TABLE asignaciones_turno (
    id             BIGSERIAL PRIMARY KEY,
    funcionario_id BIGINT    NOT NULL REFERENCES usuarios(id),
    turno_id       BIGINT    REFERENCES turnos(id),      -- NULL = día libre
    area_id        BIGINT    NOT NULL REFERENCES areas(id),
    fecha          DATE      NOT NULL,
    --máximo 1 turno por día por funcionario
    CONSTRAINT uq_funcionario_fecha UNIQUE (funcionario_id, fecha)
);

-- ============================================================
-- SOLICITUDES DE TURNO
-- Si DENEGADA, el funcionario no puede volver a solicitar
-- para ese mismo turno.
-- ============================================================
CREATE TABLE solicitudes_turno (
    id                   BIGSERIAL    PRIMARY KEY,
    asignacion_turno_id  BIGINT       NOT NULL REFERENCES asignaciones_turno(id),
    tipo_solicitud       VARCHAR(20)  NOT NULL CHECK (tipo_solicitud IN ('MODIFICACION','ELIMINACION')),
    motivo_solicitud     VARCHAR(500) NOT NULL,
    estado               VARCHAR(20)  NOT NULL DEFAULT 'PENDIENTE'
                                     CHECK (estado IN ('PENDIENTE','APROBADA','DENEGADA'))
);

-- ============================================================
-- DATOS SEMILLA: usuario ADMINISTRADOR inicial
-- ============================================================
INSERT INTO usuarios (nombre, tipo_identificacion, numero_identificacion, contrasena, rol, activo)
VALUES (
    'Administrador',
    'CC',
    1234567890,
    486275,  -- REEMPLAZAR
    'ADMINISTRADOR',
    TRUE
);
