package com.example.tetris.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.tetris.R
import com.example.tetris.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val COD = 5
    val PREFS = "prefs_file2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var continuar: Boolean
        var configuracoes = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {

                continuar = intent.extras?.getBoolean("CONTINUAR") ?: false

                if (!continuar){
                    btnContinuar.visibility = View.GONE
                }

            btnContinuar.setOnClickListener {
                var i = Intent(this@MainActivity,TabuleiroActivity::class.java)
                var b = Bundle()
                b.putInt("DIFICULDADE",configuracoes.getInt("DIFICULDADE",1))
                i.putExtras(b)
                startActivity(i)

            }

            btnConfiguracoes.setOnClickListener{
                var i = Intent(this@MainActivity,ConfigActivity::class.java)
                startActivityForResult(i,COD)
            }

            btnNovoJogo.setOnClickListener {
                var i = Intent(this@MainActivity,TabuleiroActivity::class.java)
                var b = Bundle()
                b.putInt("DIFICULDADE",configuracoes.getInt("DIFICULDADE",1))
                i.putExtras(b)
                startActivity(i)
                finish()
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var configuracoes = getSharedPreferences(PREFS,Context.MODE_PRIVATE)
        var editor = configuracoes.edit()
        val params = data?.extras

        when(resultCode){
            Activity.RESULT_OK->{
                editor.clear()
                editor.putInt("DIFICULDADE", params!!.getInt("DIFICULDADE"))
                editor.commit()
            }
        }

    }
}