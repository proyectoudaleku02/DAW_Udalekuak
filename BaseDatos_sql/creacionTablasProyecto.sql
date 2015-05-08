DROP TABLE SORTEOS CASCADE CONSTRAINTS;

CREATE TABLE SORTEOS (
  idSorteo NUMBER(10),
  numSorteo NUMBER(10),
  cadenciaSorteo NUMBER(10),
  fechaApertura DATE,
  fechaSorteo DATE,
  fechaCierre DATE,
  CONSTRAINT sorteos_pk PRIMARY KEY (idSorteo));

DROP TABLE SOLICITUDES CASCADE CONSTRAINTS; 
 
CREATE TABLE SOLICITUDES (
  idSolicitud NUMBER(10),
  fechaHoraCita DATE NULL,
  situacionSolic VARCHAR2(45) NOT NULL,
  ordenSorteo NUMBER(10) NULL,
  CONSTRAINT solicitudes_pk PRIMARY KEY (idSolicitud));

DROP TABLE PROVINCIAS CASCADE CONSTRAINTS; 
  
CREATE TABLE PROVINCIAS (
  idProvincia VARCHAR2(3),
  nombreProv VARCHAR2(45) NOT NULL,
  CONSTRAINT provincias_pk PRIMARY KEY (idProvincia));

DROP TABLE CENTROS CASCADE CONSTRAINTS; 
  
CREATE TABLE CENTROS (
  idCentro NUMBER(10),
  nombreCent VARCHAR2(45) NOT NULL,
  modeloCent VARCHAR2(3) NOT NULL,
  idProvincia VARCHAR2(3),
  CONSTRAINT centros_pk PRIMARY KEY (idCentro),
  CONSTRAINT centProv_fk FOREIGN KEY (idProvincia)
    REFERENCES PROVINCIAS (idProvincia));

DROP TABLE INSCRIPCIONES CASCADE CONSTRAINTS; 

CREATE TABLE INSCRIPCIONES (
  idInscripcion VARCHAR2(10),
  idSolicitud NUMBER(10),
  CONSTRAINT inscripciones_pk PRIMARY KEY (idInscripcion),
  CONSTRAINT insSol_fk FOREIGN KEY (idSolicitud)
    REFERENCES SOLICITUDES (idSolicitud));

DROP TABLE TUTORES CASCADE CONSTRAINTS; 

CREATE TABLE TUTORES (
  idSolicitante NUMBER(10),
  dniTutor VARCHAR2(13) UNIQUE NOT NULL,
  nombreTutor VARCHAR2(20) NOT NULL,
  ape1Tutor VARCHAR2(30) NOT NULL,
  ape2Tutor VARCHAR2(30) NOT NULL,
  idInscrip VARCHAR2(10),
  CONSTRAINT tutores_pk PRIMARY KEY (idSolicitante),
  CONSTRAINT tutIns_fk FOREIGN KEY (idInscrip)
    REFERENCES INSCRIPCIONES (idInscripcion));

DROP TABLE TELEFONOS CASCADE CONSTRAINTS; 

CREATE TABLE TELEFONOS (
  numeroTelf VARCHAR2(9),
  idSolicitante NUMBER(10),
  CONSTRAINT telefonos_pk PRIMARY KEY (numeroTelf),
  CONSTRAINT telTut_fk FOREIGN KEY (idSolicitante)
    REFERENCES TUTORES (idSolicitante));

DROP TABLE MUNICIPIOS CASCADE CONSTRAINTS; 

CREATE TABLE MUNICIPIOS (
  idMunicipio NUMBER(10)
  ,
  nombreMunic VARCHAR2(45) NOT NULL,
  idProvincia VARCHAR2(3),
  CONSTRAINT municipios_pk PRIMARY KEY (idMunicipio),
  CONSTRAINT munProv_fk FOREIGN KEY (idProvincia)
    REFERENCES PROVINCIAS (idProvincia));

DROP TABLE VIAS CASCADE CONSTRAINTS; 

CREATE TABLE VIAS (
  idVia NUMBER(10),
  tipoVia VARCHAR2(30) NOT NULL,
  nombreVia VARCHAR2(45) NOT NULL,
  idMunicipio NUMBER(10),
  CONSTRAINT vias_pk PRIMARY KEY (idVia),
  CONSTRAINT viasMun_fk FOREIGN KEY (idMunicipio)
    REFERENCES MUNICIPIOS (idMunicipio));

DROP TABLE DIRECCIONES CASCADE CONSTRAINTS; 

CREATE TABLE DIRECCIONES (
  idDireccion VARCHAR2(40),
  numDir VARCHAR2(3),
  letra VARCHAR2(1),
  piso VARCHAR2(2),
  escalera VARCHAR2(10),
  mano VARCHAR2(3),
  cp VARCHAR2(5) NOT NULL,
  idVia NUMBER(10),
  CONSTRAINT direcciones_pk PRIMARY KEY (idDireccion),
  CONSTRAINT dirVias_fk FOREIGN KEY (idVia)
    REFERENCES VIAS (idVia));

DROP TABLE MENORES CASCADE CONSTRAINTS; 

CREATE TABLE MENORES (
  idSolicitante NUMBER(10),
  dniMenor VARCHAR2(13) UNIQUE,
  nombreMenor VARCHAR2(20) NOT NULL,
  apel1Menor VARCHAR2(30) NOT NULL,
  apel2Menor VARCHAR2(30) NOT NULL,
  sexoMenor VARCHAR2(1) NOT NULL,
  fechaNacMenor DATE NOT NULL,
  discapacidadMenor VARCHAR2(2) NOT NULL,
  idInscrip VARCHAR2(10),
  idDir VARCHAR2(40),
  CONSTRAINT menores_pk PRIMARY KEY (idSolicitante),
  CONSTRAINT menIns_fk FOREIGN KEY (idInscrip)
    REFERENCES INSCRIPCIONES (idInscripcion),
  CONSTRAINT menDir_fk FOREIGN KEY (idDir)
    REFERENCES DIRECCIONES (idDireccion),
  CONSTRAINT discapacidadMenor_ck CHECK(lower(discapacidadMenor) IN('si','no')));


CREATE OR REPLACE SEQUENCE idSolicitud_seq INCREMENT BY 1 START WITH 1;
		
CREATE OR REPLACE SEQUENCE idCentro_seq INCREMENT BY 1 START WITH 1;

CREATE OR REPLACE SEQUENCE idSolicitante_seq INCREMENT BY 1 START WITH 1;

CREATE OR REPLACE SEQUENCE idMunicipio_seq INCREMENT BY 1 START WITH 1;

















