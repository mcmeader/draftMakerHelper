package com.example.draftmakerhelper

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.room.Database
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import com.example.draftmakerhelper.constants.RandomOrgDevKey
import com.example.draftmakerhelper.constants.SubTypeCodes
import com.example.draftmakerhelper.constants.ToastText
import com.example.draftmakerhelper.models.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.File
import java.io.PrintWriter
import java.lang.reflect.Type

class ApiCalls(val context: Context,val db: AppDatabase) {
    lateinit var queue: RequestQueue
    lateinit var options: CardFetchDataFields

    fun fetchCardsFromApi(options: CardFetchDataFields) {
        this.options = options
        queue = Volley.newRequestQueue(this.context)
        val url = "https://arkhamdb.com/api/public/cards/"
        options.progressBar.visibility = View.VISIBLE;
        options.cardImage.visibility = View.INVISIBLE;
        val collectionType: Type = object : TypeToken<Collection<CardResponseModel>>() {}.type
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            val allCards =
                Gson().fromJson<List<CardResponseModel>>(response.toString(), collectionType)
            filterCards(allCards)
        }, null)
        queue.add(request)
    }

    fun getRandomCard(type: SubTypeCodes): CachedCardModel {
        if (type.equals(SubTypeCodes.weakness)) {
           val cache = db.weaknessDao().getAllWeaknesses()
            val randomNumber = getRandomNumber(cache.cards.size)
            return cache.cards[randomNumber]
        } else {
            val cache = db.weaknessDao().getAllWeaknesses()
            val randomNumber = getRandomNumber(cache.cards.size)
            return cache.cards[randomNumber]
        }
    }

    fun getCardImage(imageId: String): Bitmap? {
        val url = "https://arkhamdb.com/bundles/cards/$imageId.jpg"
        var image: Bitmap? = null
        val imageRequest = ImageRequest(
            url,
            { response ->
                image = response
            }, 350, 350, ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.RGB_565, null
        )
        queue.add(imageRequest)
        return image
    }

    private fun getRandomNumber(numberItems: Int): Int {
        queue = Volley.newRequestQueue(this.context)
        var randomValue: Int = 0
        val url = "https://api.random.org/json-rpc/4/invoke"
        val jsonString = "{\n" +
                "    \"jsonrpc\": \"2.0\",\n" +
                "    \"method\": \"generateIntegers\",\n" +
                "    \"params\": {\n" +
                "        \"apiKey\": \"${RandomOrgDevKey.keyValue.key}\",\n" +
                "        \"n\": 1,\n" +
                "        \"min\": 1,\n" +
                "        \"max\": $numberItems\n" +
                "    },\n" +
                "    \"id\": 1\n" +
                "}"
        val collectionType: Type = object : TypeToken<RandomOrgModel>() {}.type
        val request =
            JsonObjectRequest(Request.Method.POST, url, JSONObject(jsonString), { response ->
                val responseObject =
                    Gson().fromJson<RandomOrgModel>(response.toString(), collectionType)
                randomValue = responseObject.result.random.data[0]
            }, null)
        queue.add(request)
        return randomValue
    }

    private fun filterCards(cards: List<CardResponseModel>) {
        val cachedCards: List<CachedCardModel> = listOf()
        val weaknessCards: List<CachedCardModel> = listOf()
        val allWeaknesses = cards.filter { it.subtype_code == SubTypeCodes.investigator.name }
        val investigators = cards.filter { it.subtype_code == SubTypeCodes.weakness.name }

        investigators.forEach {
            val cached = CachedCardModel(it.name, it.code, it.faction_name)
            cachedCards.plus(cached)
        }

        allWeaknesses.forEach {
            val weakness = CachedCardModel(it.name, it.code, null)
            weaknessCards.plus(weakness)
        }

        db.investigatorDao().addInvestigators(cachedCards)
        db.weaknessDao().addWeaknesses(weaknessCards)

        this.options.progressBar.visibility = View.INVISIBLE;
        this.options.cardImage.visibility = View.VISIBLE;
    }
}
