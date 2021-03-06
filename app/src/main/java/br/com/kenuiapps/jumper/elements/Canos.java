package br.com.kenuiapps.jumper.elements;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import br.com.kenuiapps.jumper.engine.Som;
import br.com.kenuiapps.jumper.grafics.Tela;

/**
 * Created by daniel on 20/04/15.
 */
public class Canos {


    private static final double QTD_DE_CANOS = 5;
    private static final int POSICAO_INICIAL = 400;
    private static final int DISTANCIA_ENTRE_CANOS = 200;
    private List<Cano> canos = new ArrayList<>();
    private Tela tela;
    private Pontuacao pontuacao;
    private Context context;
    private Som som;

    public Canos(Tela tela, Pontuacao pontuacao, Context context) {
        this.tela = tela;
        this.pontuacao = pontuacao;
        this.context = context;
        int posicao = POSICAO_INICIAL;

        for (int i = 0; i < QTD_DE_CANOS; i++) {

            posicao += DISTANCIA_ENTRE_CANOS;
            Cano cano = new Cano(tela, posicao, context);
            canos.add(cano);
        }
        som = new Som(context);

    }

    public void desenhaNo(Canvas canvas) {

        for (Cano cano: canos){
            cano.desenhaNo(canvas);
        }
    }

    public void move() {
        ListIterator<Cano> interator = canos.listIterator();
        while (interator.hasNext()){
            Cano cano = interator.next();
            cano.move();
            if(cano.saiuDaTela()){
                som.tocaSom(Som.PONTOS);
                pontuacao.aumenta();
                interator.remove();
                Cano outroCano = new Cano(tela, getMaximo() + DISTANCIA_ENTRE_CANOS, context);
                interator.add(outroCano);
            }

        }

    }

    private int getMaximo() {
        int maximo = 0;
        for (Cano cano: canos){
            maximo = Math.max(cano.getPosicao(), maximo);
        }
        return maximo;
    }

    public boolean temColisaoCom(Passaro passaro) {
        for (Cano cano: canos){
            if(cano.temColisaoHorizontal(passaro)
                    && cano.temColisaoVertical(passaro)){
                return true;
            }
        }
        return false;
    }
}
