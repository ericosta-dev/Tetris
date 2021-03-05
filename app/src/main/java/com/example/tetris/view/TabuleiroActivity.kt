package com.example.tetris.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.tetris.R
import com.example.tetris.databinding.ActivityTabuleiroBinding
import com.example.tetris.jogo.Jogo
import com.example.tetris.part.*
import kotlin.random.Random


class TabuleiroActivity : AppCompatActivity() {
    lateinit var binding: ActivityTabuleiroBinding

    var nivel:Int = 2
    val jogo : Jogo by lazy {
        ViewModelProviders.of(this)[jogo::class.java]
    }

    var p = newPart()

    private var continuar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabuleiro)

        var params = intent.extras
        nivel = params!!.getInt("DIFICULDADE")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_config)
        binding.apply {
            gridTabuleiro.rowCount = jogo.LINHA
            gridTabuleiro.columnCount = jogo.COLUNA
            var inflater = LayoutInflater.from(this@TabuleiroActivity)

            jogo.rodando = true

            for (i in 0 until jogo.LINHA) {
                for (j in 0 until jogo.COLUNA) {
                    jogo.tabuleiroView[i][j] = inflater.inflate(R.layout.ponto, gridTabuleiro, false) as ImageView
                    gridTabuleiro.addView(jogo.tabuleiroView[i][j])
                }
            }

            rodarJogo()

        }
    }

        private fun rodarJogo(){

            binding = DataBindingUtil.setContentView(this, R.layout.activity_config)
            continuar = false

            binding.apply {
                Thread{
                    while (jogo.rodando){
                        Thread.sleep(jogo.nivel[nivel])
                        runOnUiThread {
                            lblPontuacaoAtual.text = jogo.pontos.toString()

                            redesenhaTabuleiro()

                            if (fimJogo(p) && colisao(p)) {
                                resultado()
                            }

                            btnPausa.setOnClickListener {
                                pausar()
                            }

                            btnRodar.setOnClickListener {
                                rodarPeca(p)
                            }

                            btnEsquerda.setOnClickListener {
                                if(!esquerdaTabuleiroColisao(p) && !colisaoEsquerda(p))
                                    p.left()
                            }

                            btnDireita.setOnClickListener { if(!direitaTabuleiroColisao(p) && !colisaoDireita(p))
                                p.right() }

                            btnDescer.setOnClickListener {
                                if(!baseTabuleiroColisao(p) && !colisao(p))
                                    p.down()
                            }

                            if(!baseTabuleiroColisao(p) && !colisao(p)){
                                p.down()

                                try {
                                    jogo.tabuleiroView[p.dot1.x][p.dot1.y]!!.setImageResource(R.drawable.gray)
                                    jogo.tabuleiroView[p.dot2.x][p.dot2.y]!!.setImageResource(R.drawable.gray)
                                    jogo.tabuleiroView[p.dot3.x][p.dot3.y]!!.setImageResource(R.drawable.gray)
                                    jogo.tabuleiroView[p.dot4.x][p.dot4.y]!!.setImageResource(R.drawable.gray)
                                } catch (e: ArrayIndexOutOfBoundsException) {
                                    jogo.rodando = false
                                }

                            }else{
                                atualizaTabuleiro(p)
                                pontuou()
                                try {
                                    jogo.tabuleiroView[p.dot1.x][p.dot1.y]!!.setImageResource(R.drawable.gray)
                                    jogo.tabuleiroView[p.dot2.x][p.dot2.y]!!.setImageResource(R.drawable.gray)
                                    jogo.tabuleiroView[p.dot3.x][p.dot3.y]!!.setImageResource(R.drawable.gray)
                                    jogo.tabuleiroView[p.dot4.x][p.dot4.y]!!.setImageResource(R.drawable.gray)
                                } catch (e: ArrayIndexOutOfBoundsException) {
                                    jogo.rodando = false
                                }

                                p = newPart()
                            }

                        }
                    }
                }
            }


        }

        private fun redesenhaTabuleiro(){
            binding = DataBindingUtil.setContentView(this, R.layout.activity_config)
            binding.apply {
                for (i in 0 until jogo.LINHA){
                    for (j in 0 until jogo.COLUNA){
                        if (jogo.tabuleiro[i][j] == 0){
                            jogo.tabuleiroView[i][j]!!.setImageResource(R.drawable.ciano)
                        } else {
                            jogo.tabuleiroView[i][j]!!.setImageResource(R.drawable.gray)
                        }
                    }
                }
            }
        }

        private fun pontuou(){
            for (i in 0 until jogo.LINHA){
                if(jogo.tabuleiro[i].sum() == 12){
                    jogo.aumentaPontos()
                    desceTabuleiro(i)
                }
            }
        }

        private fun desceTabuleiro(linha:Int){
            for (i in linha downTo 1){
                jogo.tabuleiro[i] = jogo.tabuleiro[i-1]
            }
        }

        override fun onPause() {
            super.onPause()
            jogo.rodando = false
        }

        override fun onRestart() {
            super.onRestart()
            jogo.rodando = true
            rodarJogo()
        }

    private fun colisao(obj : Part):Boolean{
        return     jogo.tabuleiro[obj.dot1.x +1][obj.dot1.y] == 1 || jogo.tabuleiro[obj.dot2.x +1][obj.dot2.y] == 1
                || jogo.tabuleiro[obj.dot3.x +1][obj.dot3.y] == 1 || jogo.tabuleiro[obj.dot4.x +1][obj.dot4.y] == 1

    }


    private fun colisaoDireita(obj : Part):Boolean{
        return jogo.tabuleiro[obj.dot1.x][obj.dot1.y+1] == 1 || jogo.tabuleiro[obj.dot2.x][obj.dot2.y+1] == 1
                || jogo.tabuleiro[obj.dot3.x][obj.dot3.y+1] == 1 || jogo.tabuleiro[obj.dot4.x][obj.dot4.y+1] == 1
    }

    private fun colisaoEsquerda(obj : Part):Boolean{
        return  jogo.tabuleiro[obj.dot1.x][obj.dot1.y-1] == 1 || jogo.tabuleiro[obj.dot2.x][obj.dot2.y-1] == 1
                || jogo.tabuleiro[obj.dot3.x][obj.dot3.y-1] == 1 || jogo.tabuleiro[obj.dot4.x][obj.dot4.y-1] == 1
    }

    //retorna verdadeiro caso algum ponto da peça atinja a linha 22(última linha do tabuleiro)

    private fun baseTabuleiroColisao(obj: Part):Boolean{
        return obj.dot1.x == 21 || obj.dot2.x == 21 || obj.dot3.x == 21 || obj.dot4.x == 21
    }

    //retorna verdadeiro caso algum ponto atinja a borda direita do tabuleiro

    private fun direitaTabuleiroColisao(obj : Part):Boolean{
        return obj.dot1.y == 11 || obj.dot2.y == 11 || obj.dot3.y == 11 || obj.dot4.y == 11
    }

    //retorna verdadeiro caso algum ponto atinja a borda esquerda do tabuleiro

    private fun esquerdaTabuleiroColisao(obj : Part):Boolean{
        return obj.dot1.y == 0 || obj.dot2.y == 0 || obj.dot3.y == 0 || obj.dot4.y == 0
    }


    //retorna uma nova peça aleatória
    private fun newPart(): Part {
        var p = Random.nextInt(0,7)

        when(p){
            0->{
                var i = I()
                return i
            }
            1->{
                var j = J()
                return j
            }
            2->{
                var l = L()
                return l
            }
            3->{
                var o = O()
                return o
            }
            4->{
                var s = S()
                return s
            }
            5->{
                var t = T()
                return t
            }
            else ->{
                var z = Z()
                return z
            }
        }
    }

    private fun rodarPeca(obj:Part){
        var aux1 = Dot(obj.dot1.x,obj.dot1.y)
        var aux2 = Dot(obj.dot2.x,obj.dot2.y)
        var aux3 = Dot(obj.dot3.x,obj.dot3.y)
        var aux4 = Dot(obj.dot4.x,obj.dot4.y)

        while (obj.dot1.y < obj.minSpace || obj.dot1.y > (jogo.COLUNA - 1) - obj.minSpace){

            if (obj.dot1.y < obj.minSpace) {
                obj.right()
            }
            else {
                obj.left()
            }
        }

        if(obj.dot1.x < jogo.LINHA - obj.minSpace) {

            obj.rotate()

            if (jogo.tabuleiro[obj.dot1.x][obj.dot1.y] == 1 || jogo.tabuleiro[obj.dot2.x][obj.dot2.y] == 1 ||
                jogo.tabuleiro[obj.dot3.x][obj.dot3.y] == 1 || jogo.tabuleiro[obj.dot4.x][obj.dot4.y] == 1
            ) {
                obj.dot1 = Dot(aux1.x,aux1.y)
                obj.dot2 = Dot(aux2.x,aux2.y)
                obj.dot3 = Dot(aux3.x,aux3.y)
                obj.dot4 = Dot(aux4.x,aux4.y)

            }
        }

    }
    private fun pausar(){
        jogo.rodando = false
        var i = Intent(this,MainActivity::class.java)
        var b = Bundle()
        continuar = true
        b.putBoolean("CONTINUAR", continuar)
        i.putExtras(b)
        startActivity(i)
    }

    private fun resultado() {
        jogo.rodando=false
        continuar = false
        var b = Bundle()
        b.putInt("DIFICULDADE",nivel)
        b.putInt("RECORDE",jogo.pontos)
        var i = Intent(this,Result::class.java)
        i.putExtras(b)
        startActivity(i)
        finish()

    }
    private fun fimJogo(obj: Part): Boolean {
        return     obj.dot1.x == 0 || obj.dot2.x== 0
                || obj.dot3.x == 0 || obj.dot4.x== 0
    }

    private fun atualizaTabuleiro(obj: Part){
        jogo.tabuleiro[obj.dot1.x][obj.dot1.y] = 1
        jogo.tabuleiro[obj.dot2.x][obj.dot2.y] = 1
        jogo.tabuleiro[obj.dot3.x][obj.dot3.y] = 1
        jogo.tabuleiro[obj.dot4.x][obj.dot4.y] = 1
    }
}

