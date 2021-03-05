package com.example.tetris.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.tetris.R
import com.example.tetris.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {
    lateinit var binding: ActivityConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)
        var b = Bundle()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_config)

        binding.apply {
            btnVoltar.setOnClickListener {

                    if (radioButtonFacil.isChecked){
                        b.putInt("DIFICULDADE",0)
                    }
                    if (radioButtonNormal.isChecked){
                        b.putInt("DIFICULDADE",1)
                    }
                    if (radioButtonDificil.isChecked) {
                        b.putInt("DIFICULDADE", 2)
                    }

                    Toast.makeText(this@ConfigActivity,"Dificuldade: ${b.getInt("DIFICULDADE")}", Toast.LENGTH_SHORT).show()

                    var i = Intent(this@ConfigActivity, MainActivity::class.java)
                    i.putExtras(b)
                    setResult(Activity.RESULT_OK,i)
                    finish()
            }
        }
    }
}