package com.example.draftmakerhelper

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.example.draftmakerhelper.models.CardFetchDataFields
import com.example.draftmakerhelper.models.CardFetchOptions as CardFetchOptions
import com.example.draftmakerhelper.models.WeaknessCardModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

class ApiCalls{
    lateinit var queue:RequestQueue

    private fun getCards(options:CardFetchDataFields) {
        var weaknessCards:List<WeaknessCardModel>
        queue = Volley.newRequestQueue(options.context)
        val url = "https://arkhamdb.com/api/public/cards/"
        options.progressBar.visibility = View.VISIBLE;
        options.cardImage.visibility = View.INVISIBLE;
        val collectionType: Type = object : TypeToken<Collection<WeaknessCardModel>>() {}.type
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            weaknessCards = Gson().fromJson<List<WeaknessCardModel>>(response.toString(), collectionType)
            weaknessCards = filterCards(weaknessCards,options.coreSetOnly)
            getWeakness(weaknessCards,options.cardImage,options.staticText,options.progressBar)
        },null)
    queue.add(request)
    }

    private fun filterCards(weaknessCardData:List<WeaknessCardModel>, coreSetOnly: Boolean) : List<WeaknessCardModel>{
        val filteredCards  = weaknessCardData.filter { data-> data.subtype_code == "basicweakness" }
        if (coreSetOnly){
            return filteredCards.filter { data -> data.pack_code == "core"}
        }else{
            return filteredCards
        }
    }

    private fun getWeakness(weaknessCardData:List<WeaknessCardModel>, cardImage: ImageView, staticText: TextView,
                            progressBar: ProgressBar) : Bitmap?{
        val weakness = weaknessCardData.shuffled()[Random(LocalDateTime.now().nano).nextInt(weaknessCardData.size)]
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

    private fun saveListOfCards(){

    }
}
