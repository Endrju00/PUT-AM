package com.example.listdetail

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB (var context: Context):SQLiteOpenHelper(context,DATABASE_NAME,
    null, DATABASE_VER) {
    companion object {
        private val DATABASE_VER = 2
        private val DATABASE_NAME = "EDMTDBASDF.db"
        //Table
        private val TABLE_NAME = "Routes"
        private val COL_NAME = "name"
        private val COL_DESCRIPTION = "description"
        private val COL_ID = "id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY, $COL_NAME TEXT, $COL_DESCRIPTION TEXT)")
        db!!.execSQL(CREATE_TABLE_QUERY)
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (1, \"Droga 1\", \"Opis drogi 1\nDługość trasy: 2h\nTrasa przebiega przez miasta: Poznań\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (2, \"Droga 2\", \"Opis drogi 2\nDługość trasy: 1h\nTrasa przebiega przez miasta: Poznań, Luboń\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (3, \"Droga 3\", \"Opis drogi 3\nDługość trasy: 1.5h\nTrasa przebiega przez miasta: Warszawa\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (4, \"Droga 4\", \"Opis drogi 4\nDługość trasy: 1h\nTrasa przebiega przez miasta: Kraków\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (5, \"Droga 5\", \"Opis drogi 5\nDługość trasy: 2.5h\nTrasa przebiega przez miasta: Gdańsk, Gdynia, Sopot\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (6, \"Droga 6\", \"Opis drogi 6\nDługość trasy: 0.5h\nTrasa przebiega przez miasta: Poznań\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (7, \"Droga 7\", \"Opis drogi 7\nDługość trasy: 3.2h\nTrasa przebiega przez miasta: Poznań, Luboń\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (8, \"Droga 8\", \"Opis drogi 8\nDługość trasy: 0.2h\nTrasa przebiega przez miasta: Warszawa\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (9, \"Droga 9\", \"Opis drogi 9\nDługość trasy: 3h\nTrasa przebiega przez miasta: Gdańsk, Gdynia, Sopot\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (10, \"Droga 10\", \"Opis drogi 10\nDługość trasy: 0.5h\nTrasa przebiega przez miasta: Poznań\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (11, \"Droga 11\", \"Opis drogi 11\nDługość trasy: 3h\nTrasa przebiega przez miasta: Poznań, Luboń\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (12, \"Droga 12\", \"Opis drogi 12\nDługość trasy: 1h\nTrasa przebiega przez miasta: Warszawa\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (13, \"Droga 13\", \"Opis drogi 13\nDługość trasy: 3.5h\nTrasa przebiega przez miasta: Gdańsk, Gdynia, Sopot\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (14, \"Droga 14\", \"Opis drogi 14\nDługość trasy: 2h\nTrasa przebiega przez miasta: Poznań\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (15, \"Droga 15\", \"Opis drogi 15\nDługość trasy: 4h\nTrasa przebiega przez miasta: Poznań, Luboń\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (16, \"Droga 16\", \"Opis drogi 16\nDługość trasy: 2h\nTrasa przebiega przez miasta: Warszawa\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (17, \"Droga 17\", \"Opis drogi 17\nDługość trasy: 4.5h\nTrasa przebiega przez miasta: Gdańsk, Gdynia, Sopot\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (18, \"Droga 18\", \"Opis drogi 18\nDługość trasy: 1.5h\nTrasa przebiega przez miasta: Poznań\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (19, \"Droga 19\", \"Opis drogi 19\nDługość trasy: 5h\nTrasa przebiega przez miasta: Poznań, Luboń\")"))
        db!!.execSQL( ("INSERT INTO $TABLE_NAME ($COL_ID, $COL_NAME, $COL_DESCRIPTION) VALUES (20, \"Droga 20\", \"Opis drogi 20\nDługość trasy: 1.2h\nTrasa przebiega przez miasta: Warszawa\")"))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allRouteNames:Array<String>
    @SuppressLint("Range")
    get(){
        val result = ArrayList<String>()
        val selectQuery = "SELECT $COL_NAME FROM $TABLE_NAME ORDER BY $COL_ID"
        val db = this.writableDatabase
        val cursor =  db.rawQuery(selectQuery, null)
        var routeName = ""
        if(cursor.moveToFirst()){
            do {
                routeName = cursor.getString(cursor.getColumnIndex(COL_NAME))
                result.add(routeName)
            } while (cursor.moveToNext())
        }
        db.close()
        return result.toTypedArray()
    }


    @SuppressLint("Range")
    fun getDescription(name: String?): String {
        val db = this.readableDatabase
        val query =
            "SELECT $COL_DESCRIPTION from $TABLE_NAME WHERE $COL_NAME = \"$name\" "
        val cursor = db.rawQuery(query, null)
        var description:String=""
        if (cursor.moveToFirst()){
            description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION))
        }
        cursor.close()
        db.close()
        return description;
    }
}