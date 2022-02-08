package com.example.draftmakerhelper.models

@Entity
public class CachedCardModel (

        var name: String,
        var picture_id: String,
        var faction: String?
)

data class CachedCards (var cards:List<CachedCardModel>)