package com.example.roomwordsample2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao //Daoに指定するアノテーション
public interface WordDao { //Daoはinterfaceかabstractの必要がある

    //一つの単語を挿入するメソッド
    //InsertはSQLを提供する必要がない特殊なDaoメソッド。onConflict:新規単語が既存と完全一致する場合は無視する設定にしている
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    //全ての単語を削除するメソッド
    @Query("DELETE FROM word_table") //QueryはSQLクエリを文字列として指定
    void deleteAll();

    //全ての単語を取得し、単語のListを返すメソッド
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();
}
