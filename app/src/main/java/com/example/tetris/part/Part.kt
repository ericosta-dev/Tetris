package com.example.tetris.part

import androidx.lifecycle.ViewModel

abstract class Part( var x:Int, var y:Int): ViewModel(){
    var minSpace: Int = 0

    constructor(x: Int,y: Int,minSpace: Int):this(x,y){
        this.minSpace = minSpace
    }


    var dot1 = Dot(x,y)
    lateinit var dot2 :Dot
    lateinit var dot3 :Dot
    lateinit var dot4 :Dot


    var state = true


    abstract fun down()

    abstract fun left()

    abstract fun right()

    abstract fun rotate()
}