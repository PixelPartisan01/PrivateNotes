package com.example.privatenotes

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.icu.util.Output
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.*
import java.lang.StringBuilder

class Note : AppCompatActivity()
{
    lateinit var textArea: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {        supportActionBar?.hide()

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.layoutAnimatedBackground)
        val animationDrawable: AnimationDrawable = constraintLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        val path = this.getExternalFilesDir(null)
        val folder = File(path, "Privat Note")
        folder.mkdirs()

        textArea = findViewById(R.id.textArea)
        textArea.setText(readFrom(this))
    }

    fun writeTo(data : String,  context: Context)
    {

        try
        {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput("secret.pn", Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()

            //Toast.makeText(this, filesDir.toString(), Toast.LENGTH_LONG).show()
        }
        catch (e : IOException)
        {

        }
    }

    fun readFrom(context : Context): String
    {
        var ret = ""
        try
        {
            val inputStream = context.openFileInput("secret.pn")

            if(inputStream != null)
            {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = null
                val stringBuilder = StringBuilder()

                while ( {receiveString = bufferedReader.readLine(); receiveString}() != null )
                {
                    stringBuilder.append(receiveString).append("\n")
                }

                inputStream.close()
                ret = stringBuilder.toString()
            }
        }
        catch (e : FileNotFoundException)
        {
            writeTo("", this)
            readFrom(this)
        }

        return ret
    }

    override fun onPause() {
        super.onPause()
        writeTo(textArea.text.toString(), this)
    }

    override fun onDestroy() {
        super.onDestroy()
        writeTo(textArea.text.toString(), this)
    }

    override fun onRestart() {
        super.onRestart()
        writeTo(textArea.text.toString(), this)
    }
}