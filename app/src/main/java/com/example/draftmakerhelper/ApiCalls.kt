package com.example.draftmakerhelper

import android.content.Context
import android.graphics.Bitmap
import android.os.ResultReceiver
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.example.draftmakerhelper.models.CardModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

class ApiCalls{
    lateinit var queue:RequestQueue

    fun getWeaknesses(context: Context,coreSetOnly:Boolean,cardImage:ImageView,staticText:TextView,
                      progressBar: ProgressBar) {
        var cards:List<CardModel>
        queue = Volley.newRequestQueue(context)
        val url = "https://arkhamdb.com/api/public/cards/"
        progressBar.visibility = View.VISIBLE;
        cardImage.visibility = View.INVISIBLE;
        context
        val collectionType: Type = object : TypeToken<Collection<CardModel>>() {}.type
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            cards = Gson().fromJson<List<CardModel>>(response.toString(), collectionType)
            cards = filterCards(cards,coreSetOnly)
            getWeakness(cards,cardImage,staticText,progressBar)
        },null)
    queue.add(request)
    }

    private fun filterCards(cardData:List<CardModel>, coreSetOnly: Boolean) : List<CardModel>{
        val filteredCards  = cardData.filter { data-> data.subtype_code == "basicweakness" }
        if (coreSetOnly){
            return filteredCards.filter { data -> data.pack_code == "core"}
        }else{
            return filteredCards
        }
    }

    private fun getWeakness(cardData:List<CardModel>,cardImage: ImageView,staticText: TextView,
    progressBar: ProgressBar) : Bitmap?{
        val weakness = cardData.shuffled()[Random(LocalDateTime.now().nano).nextInt(cardData.size)]
        val cardNumber = weakness.url.substringAfterLast('/')
        var picture:Bitmap? = null
        val imageRequest = ImageRequest("https://arkhamdb.com/bundles/cards/" + cardNumber + ".jpg",
            {response ->
                staticText.text = "Your Weakness is:"
                cardImage.setImageBitmap(response)
                progressBar.visibility = View.INVISIBLE;
                cardImage.visibility = View.VISIBLE;
            },350,350, ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565,null)
        queue.add(imageRequest)
        return picture
    }
}
