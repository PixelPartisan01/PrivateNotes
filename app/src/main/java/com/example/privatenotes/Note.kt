package com.example.privatenotes

import android.content.Context
import android.icu.util.Output
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.IOException
import java.io.OutputStreamWriter

class Note : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        //writeTo()
    }

    fun writeTo(data : String,  context: Context)
    {
        try
        {
            var outputStreamWriter = OutputStreamWriter(context.openFileOutput("secret.pn", Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
        }
        catch (e : IOException)
        {

        }
    }
}