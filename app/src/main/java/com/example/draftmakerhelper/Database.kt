//package com.example.draftmakerhelper
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.sqlite.db.SupportSQLiteDatabase
//import androidx.work.WorkManager
//import com.example.draftmakerhelper.dao.InvestigatorDAO
//import com.example.draftmakerhelper.dao.UnplayedGatorsDAO
//import com.example.draftmakerhelper.dao.WeaknessDAO
//import com.example.draftmakerhelper.entities.CraigInvestigators
//import com.example.draftmakerhelper.entities.InvestigatorEntity
//import com.example.draftmakerhelper.entities.MaxInvestigators
//import com.example.draftmakerhelper.entities.WeaknessEntity
//import androidx.work.OneTimeWorkRequestBuilder
//import androidx.work.workDataOf
//
//@Database(entities = [CraigInvestigators::class,MaxInvestigators::class,InvestigatorEntity::class,WeaknessEntity::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun investigatorDao():InvestigatorDAO
//    abstract fun weaknessDao():WeaknessDAO
//    abstract fun unplayedGators():UnplayedGatorsDAO
//
//    companion object {
//        // For Singleton instantiation
//        @Volatile private var instance: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            return instance ?: synchronized(this) {
//                instance ?: buildDatabase(context).also { instance = it }
//            }
//        }
//
//        // Create and pre-populate the database. See this article for more details:
//        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
//        private fun buildDatabase(context: Context): AppDatabase {
//            return Room.databaseBuilder(context, AppDatabase::class.java, "arkham-db")
//                .addCallback(
//                    object : RoomDatabase.Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
//                                .setInputData(workDataOf(KEY_FILENAME to PLANT_DATA_FILENAME))
//                                .build()
//                            WorkManager.getInstance(context).enqueue(request)
//                        }
//                    }
//                )
//                .build()
//        }
//    }
//}