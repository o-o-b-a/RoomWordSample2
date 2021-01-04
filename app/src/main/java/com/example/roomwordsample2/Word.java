package com.example.roomwordsample2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//SQLite table アノテーション（注釈） 指定なしだとクラス名がそのままテーブル名
@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey //SQLの主キー
    @NonNull //戻り値が決してnullにならないことを示す
    @ColumnInfo(name = "word")//カラム。指定なしだと変数名がそのままカラム名
    private String mWord;

    public Word(@NonNull String mWord) {
        this.mWord = mWord;
    }

    //データベースに格納されている全フィールドはpublicであるか、getterを持つ必要があるのでgetterを作成
    public String getWord(){
        return this.mWord;
    }
}
