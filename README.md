# ADNes Studio

Plugin base para **Paper 1.12.2 + Java 8**.

## Compilar
Requiere Maven y Java 8:
`mvn clean package`

El JAR queda en `target/adnes-studio-1.0.0.jar`.

## Comandos
- `/adnes items`
- `/adnes give <jugador> <nexus_sword|lunar_sword|nexus_helmet|lunar_helmet>`
- `/adnes boss` o `/adnes spawn`
- `/adnes reload`

## Estado de recursos
Esta primera base crea el plugin y su estructura. Los modelos originales Nexus, Lunar y From The Shadows deben convertirse y mapearse individualmente al formato de resource pack 1.12.2. Esta versión no dispone de CustomModelData; se usará el valor de durabilidad como selector. Los modelos de mobs necesitan una técnica compatible con 1.12.2 y no se consideran integrados hasta probar su renderizado en cliente.