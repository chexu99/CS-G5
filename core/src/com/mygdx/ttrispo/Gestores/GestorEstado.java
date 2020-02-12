package com.mygdx.ttrispo.Gestores;

import com.mygdx.ttrispo.Pantalla.Partida;

public class GestorEstado {
    public static final int SINPIEZA = 2; //Valor del Gestor de Estado cuando no hay pieza en el tablero
    public static final int REPOSO = 0; //Valor del Gestor de Estado en reposo
    public static final int CAER = 1; //Valor del Gestor de Estado cuando cae una pieza
    public static final int DERECHA = 3; //Valor del Gestor de Estado cuando realizamos un desplaz. a la der.
    public static final int IZQUIERDA = 4; //Valor del Gestor de Estado cuando realizamos un desplaz a la izq.
    public static final int GIRO = 5; //Valor del Gestor de Estado cuando realizamos un estadoGiro
    public static final int BLOQUEAR = 6; //Valor del Gestor de Estado cuando lo bloqueamos

    public static final int SINPIEZA2 = 2; //Valor del Gestor de Estado cuando no hay pieza en el tablero
    public static final int REPOSO2 = 0; //Valor del Gestor de Estado en reposo
    public static final int CAER2 = 1; //Valor del Gestor de Estado cuando cae una pieza
    public static final int DERECHA2 = 3; //Valor del Gestor de Estado cuando realizamos un desplaz. a la der.
    public static final int IZQUIERDA2 = 4; //Valor del Gestor de Estado cuando realizamos un desplaz a la izq.
    public static final int GIRO2 = 5; //Valor del Gestor de Estado cuando realizamos un estadoGiro
    public static final int BLOQUEAR2 = 6; //Valor del Gestor de Estado cuando lo bloqueamos

    private final Partida partida;
    private float velocity = 0.4f;
    private float velocity2 = 0.3f;
    private float contador = 0;
    private float contador2 = 0;
    private int estado = SINPIEZA;
    private int estado2 = SINPIEZA2;

    public GestorEstado(Partida partida) {
        this.partida = partida;
    }

    public int getEstado(float delta) {
        if (contador < velocity) {
            contador += delta;
        } else {
            contador = 0;
            estado = CAER;
        }
        return estado;
    }

    /** Hay que hacer otro estado para el gestor 2 porque es verdad que tienen el mismo delta,
     * y por ese motivo confunde al ciclo de vida, porque si hacemos referencia a los mismos estados,
     * solo se caera una sola pieza, ademas la velocidad es distina, de otra forma habria que recuperar
     * la que estaba...mucho lio, mejor asi
      */
    public int getEstado2(float delta) {
        if (contador2 < velocity2) {
            contador2 += delta;
        } else {
            contador2 = 0;
            estado2 = CAER2;
        }
        return estado2;
    }
    public void setEstado2(int estado2) {
        this.estado2 = estado2;
    }

    public void setVelocity2(float velocity2) {
        this.velocity2 = velocity2;
    }

    public float getVelocity2() {
        return velocity2;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getVelocity() {
        return velocity;
    }
}
