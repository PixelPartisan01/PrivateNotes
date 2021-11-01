package com.example.privatenotes

import android.content.Context
import android.icu.util.Output
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import java.io.*
import java.lang.StringBuilder

class Note : AppCompatActivity()
{
    lateinit var textArea: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        textArea = findViewById(R.id.textArea)
        textArea.setText(readFrom(this))
    }

    fun writeTo(data : String,  context: Context)
    {
        try
        {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput("sec.pn", Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        }
        catch (e : IOException)
        {

        }
    }

    fun readFrom(context : Context): String
    {
        lateinit var ret : String
        try
        {
            val inputStream = context.openFileInput("sec.pn")

            if(inputStream != null)
            {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = null
                val stringBuilder = StringBuilder()

                while ( {receiveString = bufferedReader.readLine(); receiveString}() != null )
                {
                    stringBuilder.append(receiveString)
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