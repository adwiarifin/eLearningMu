package io.elearningmu.android.muvon.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.elearningmu.android.muvon.model.Question;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "muvon_db";

    // Table Property
    private static final String TABLE_NAME = "questions";

    // CREATE STATEMENT
    private static final String CREATE_QUESTIONS_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "type VARCHAR(16),"
                    + "hint VARCHAR(100),"
                    + "explanation VARCHAR(100),"
                    + "content VARCHAR(100),"
                    + "options TEXT,"
                    + "correct VARCHAR(100),"
                    + "marks INTEGER,"
                    + "user_marks INTEGER,"
                    + "status INTEGER,"
                    + "marked VARCHAR(100),"
                    + "auto INTEGER,"
                    + "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create questions table
        db.execSQL(CREATE_QUESTIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertQuestion(String type, String hint, String explanation, String content, String options, String correct, int marks, int user_marks, int status, String marked, int auto) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("type", type);
        values.put("hint", hint);
        values.put("explanation", explanation);
        values.put("content", content);
        values.put("options", options);
        values.put("correct", correct);
        values.put("marks", marks);
        values.put("user_marks", user_marks);
        values.put("status", status);
        values.put("marked", marked);
        values.put("auto", auto);

        // insert row
        long id = db.insert(TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Question getQuestion(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        // select query
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + id;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            String optionsString = cursor.getString(cursor.getColumnIndex("options"));
            String[] optionsArray = optionsString.split(":");

            Question question = new Question();
            question.type = cursor.getString(1);
            question.hint = cursor.getString(2);
            question.explanation = cursor.getString(3);
            question.content = cursor.getString(4);
            question.options = Arrays.asList(optionsArray);
            question.correct = cursor.getString(6);
            question.marks = cursor.getInt(7);
            question.userMarks = cursor.getInt(8);
            question.status = cursor.getInt(9);
            question.marked = cursor.getString(10);
            question.auto = cursor.getInt(11);

            cursor.close();
            db.close();

            return question;
        }

        return null;
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();

        // select query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String optionsString = cursor.getString(cursor.getColumnIndex("options"));
                String[] optionsArray = optionsString.split(":");

                Question question = new Question();
                question.type = cursor.getString(1);
                question.hint = cursor.getString(2);
                question.explanation = cursor.getString(3);
                question.content = cursor.getString(4);
                question.options = Arrays.asList(optionsArray);
                question.correct = cursor.getString(6);
                question.marks = cursor.getInt(7);
                question.userMarks = cursor.getInt(8);
                question.status = cursor.getInt(9);
                question.marked = cursor.getString(10);
                question.auto = cursor.getInt(11);

                questions.add(question);
            } while (cursor.moveToNext());
        }

        // close db connection
        cursor.close();
        db.close();

        // return questions list
        return questions;
    }

    public int getQuestionsCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public void deleteAllQuestions() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
