package Util

import android.content.Context
import android.content.Intent

class Util {
//Este es un metodo estatico
    companion object{
        fun openActivity(context: Context, objClass: Class<*>){
            val intent = Intent(context,objClass)
            context.startActivity(intent)
        }
    }
}