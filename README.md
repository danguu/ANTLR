# ANTLR
# Calculadora ANTLR4 — Java & Python

Este proyecto implementa una calculadora basada en la gramática **`labeldExpr.g4`** del capítulo 4 del libro *The Definitive ANTLR 4 Reference*.
Se amplió la funcionalidad para soportar operaciones matemáticas avanzadas y dos lenguajes de destino: **Java** y **Python**.

## Características

La calculadora soporta:

* Operaciones aritméticas: `+`, `-`, `*`, `/`, `^`
* Factorial: `n!` (ej: `5! = 120`)
* Funciones matemáticas:

  * `sin(x)`, `cos(x)`, `tan(x)` (modo grados o radianes)
  * `sqrt(x)` → raíz cuadrada
  * `ln(x)` → logaritmo natural
  * `log(x)` → logaritmo base 10
* Cambio entre grados y radianes:

  * `deg()` → activa modo grados
  * `rad()` → activa modo radianes

## Estructura del proyecto

```
ANTLR4/
│── labeldExpr.g4           # Gramática ANTLR
│── Calc.java               # Main en Java
│── EvalVisitor.java        # Visitante en Java
│── calc.py                 # Main en Python
│── EvalVisitor.py          # Visitante en Python
```

## Instalación de ANTLR4

Si no tienes ANTLR4 instalado:

```bash
sudo apt install default-jdk python3 python3-pip -y
curl -O https://www.antlr.org/download/antlr-4.13.1-complete.jar
export CLASSPATH=".:$PWD/antlr-4.13.1-complete.jar:$CLASSPATH"
alias antlr4='java -jar $PWD/antlr-4.13.1-complete.jar'
alias grun='java org.antlr.v4.gui.TestRig'
```

## Ejecución en **Java**

1. Generar archivos con ANTLR:

   ```bash
   antlr4 -visitor labeldExpr.g4
   javac *.java
   ```
2. Ejecutar:

   ```bash
   java -cp .:antlr-4.13.1-complete.jar Calc
   ```
   
## Ejecución en **Python**

1. Generar archivos con ANTLR:

   ```bash
   antlr4 -Dlanguage=Python3 -visitor labeldExpr.g4
   ```
2. Ejecutar:

   ```bash
   python3 calc.py
   ```
   
## Ejemplos de uso

```
deg()
sin(30)       → 0.5
cos(60)       → 0.5
rad()
sin(3.1416/2) → 1.0
sqrt(25)      → 5.0
log(1000)     → 3.0
ln(2.71828)   → 1.0
5!            → 120
2^8           → 256.0
```
