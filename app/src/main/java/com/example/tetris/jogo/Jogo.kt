package com.example.tetris.jogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModel

class Jogo (val LINHA: Int = 22, val COLUNA: Int = 12): ViewModel(){
    var nivel = longArrayOf(450,300,150)

    var rodando = false

    var tabuleiro = Array(LINHA){
        Array(COLUNA){0}
    }

    var tabuleiroView = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }

    var pontos:Int = 0

    fun aumentaPontos(){
        pontos += 10
    }
}