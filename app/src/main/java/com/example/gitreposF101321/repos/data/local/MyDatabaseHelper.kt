package com.example.gitreposF101321.repos.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.gitreposF101321.owner.RepositoryOwnerModel
import com.example.gitreposF101321.repos.data.model.RepositoryModel
import com.example.gitreposF101321.utils.Constants.Companion.COLUMN_INDEX_REPO_ID
import com.example.gitreposF101321.utils.Constants.Companion.COLUMN_INDEX_REPO_LANGUAGE
import com.example.gitreposF101321.utils.Constants.Companion.COLUMN_INDEX_REPO_LINK
import com.example.gitreposF101321.utils.Constants.Companion.COLUMN_INDEX_REPO_OWNER
import com.example.gitreposF101321.utils.Constants.Companion.COLUMN_INDEX_REPO_TITLE

const val DATABASE_NAME = "repos.db"
const val DATABASE_VERSION = 1

//Database helper; DAO
class MyDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Create a table
    override fun onCreate(database: SQLiteDatabase) {
        val createTableStatement =
            "CREATE TABLE $TABLE_REPOSITORIES ($COLUMN_REPOSITORY_ID TEXT NOT NULL PRIMARY KEY , $COLUMN_REPOSITORY_TITLE TEXT NOT NULL,$COLUMN_REPOSITORY_OWNER TEXT NOT NULL,$COLUMN_REPOSITORY_LANGUAGE TEXT NOT NULL,$COLUMN_REPOSITORY_LINK TEXT NOT NULL);"

        // Execute SQL statement
        database.execSQL(createTableStatement)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Delete the old table
        database.execSQL("DROP TABLE IF EXISTS $TABLE_REPOSITORIES")

        // Create a new table
        onCreate(database)
    }

    fun saveRepository(repository: RepositoryModel): Boolean {
        val database: SQLiteDatabase = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_REPOSITORY_ID, repository.id)
        values.put(COLUMN_REPOSITORY_TITLE, repository.title)
        values.put(COLUMN_REPOSITORY_OWNER, repository.owner.name)
        values.put(COLUMN_REPOSITORY_LANGUAGE, repository.language)
        values.put(COLUMN_REPOSITORY_LINK, repository.link)

        //-1 on fail
        val insert = database.insert(TABLE_REPOSITORIES, null, values)
        return insert != -1L
    }

    fun getAllSavedRepositories(): List<RepositoryModel> {
        val repositoriesResult: MutableList<RepositoryModel> = ArrayList()
        val queryString = "SELECT * FROM $TABLE_REPOSITORIES"
        val database: SQLiteDatabase = this.readableDatabase

        val cursor = database.rawQuery(queryString, null)
        //traverse the results, if any
        if (cursor.moveToFirst()) {
            do {
                val repositoryId = cursor.getString(COLUMN_INDEX_REPO_ID)
                val repositoryTitle = cursor.getString(COLUMN_INDEX_REPO_TITLE)
                val repositoryOwner = cursor.getString(COLUMN_INDEX_REPO_OWNER)
                val repositoryLanguage = cursor.getString(COLUMN_INDEX_REPO_LANGUAGE)
                val repositoryLink = cursor.getString(COLUMN_INDEX_REPO_LINK)

                val repository = RepositoryModel(
                    repositoryId,
                    repositoryTitle,
                    RepositoryOwnerModel(repositoryOwner),
                    repositoryLanguage,
                    repositoryLink
                )
                repositoriesResult.add(repository)
            } while (cursor.moveToNext())
        }

        cursor.close()
        database.close()

        return repositoriesResult
    }

    companion object {
        const val TABLE_REPOSITORIES = "repositories"
        const val COLUMN_REPOSITORY_ID = "repository_id"
        const val COLUMN_REPOSITORY_TITLE = "repository_title"
        const val COLUMN_REPOSITORY_OWNER = "repository_owner"
        const val COLUMN_REPOSITORY_LANGUAGE = "repository_language"
        const val COLUMN_REPOSITORY_LINK = "repository_link"
    }
}