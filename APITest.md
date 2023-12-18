# Pruebas de API

Este documento describe las pruebas de API realizadas para el proyecto Era feriao.


## Pruebas

### [Obtener información de los feriados]

#### Descripción:
[Esta consulta se realizó para obtener qué información nos retornaba la consulta por los feriados]

#### Método:
[GET]

#### URL:
[https://apis.digital.gob.cl/fl/feriados]

#### Parámetros de Entrada:
- [año]: [Especifica consulta por año especifico de tipo "String"]
- [mes]: [Especifica consulta por mes especifico de tipo "String"]
- [dia]: [Especifica consulta por dia especifico de tipo "String"]
- [limit]: [Especifica la cantidad máxima de feriados a obtener para los metodos de tipo "lista"]
- [offset]: [Especifica limite inferior que se utilizará al obtener los feriados para los metodos de tipo "lista"]

#### Casos de Prueba:

1. [Caso de prueba 1]
    - [Consulta por la url para obtener la info]
    - [Resultado: Información de todos los feriados]
    - [Problema: Al no limitar la informacion u no ordenarla por criterio no muestra la información completa]

2. [Caso de prueba 2]
    - [Consulta por la url limitando año]
    - [nueva url: https://apis.digital.gob.cl/fl/feriados/:año]
    - [Resultado: Información de la lista especificada por el parámetro dado(año)]
    - [Problema: No muestra toda la información de la api]

3. [Caso de prueba 3]
   - [Consulta por la url usando limit = 5 para limitar en 5 la cantidad de feriados mostrada]
   - [nueva url: https://apis.digital.gob.cl/fl/feriados/:año?limit=5]
   - [Resultado: Información de la lista de los 5 primeros feriados del año ingresado]
   - [Problema: No se encuentra problema, muestra la información de 5 feriados]


### [Resultado de Prueba 1]

- Estado: [Éxito ]
- Observaciones: [Al no limitar la informacion u no ordenarla por criterio no muestra la información completa]

### [Resultado de Prueba 2]

- Estado: [Éxito ]
- Observaciones: [No muestra toda la información de la api]

### [Resultado de Prueba 3]

- Estado: [Éxito ]
- Observaciones: [No se encuentra problema, muestra la información de 5 feriados]


