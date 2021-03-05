package com.example.tetris.part

class Z(x: Int = 1, y: Int = 5): Part(x, y,minSpace = 1) {

    init {
        dot1 = Dot(x,y)
        dot2 = Dot(x,(y+1))
        dot3 = Dot((x-1),y)
        dot4 = Dot((x-1),(y-1))
    }

    override fun down() {
        dot1.down()
        dot2.down()
        dot3.down()
        dot4.down()
    }

    override fun left() {
        dot1.y -= 1
        dot2.y -= 1
        dot3.y -= 1
        dot4.y -= 1
    }

    override fun right() {
        dot1.y += 1
        dot2.y += 1
        dot3.y += 1
        dot4.y += 1
    }

    override fun rotate() {
        if (state)
        {
            dot2 = Dot((dot1.x+1),(dot1.y))
            dot3 = Dot((dot1.x),(dot1.y+1))
            dot4 = Dot((dot1.x-1),(dot1.y+1))

            state = false
        }else{
            dot2 = Dot((dot1.x),(dot1.y+1))
            dot3 = Dot((dot1.x-1),(dot1.y))
            dot4 = Dot((dot1.x-1),(dot1.y-1))

            state = true
        }
    }
}