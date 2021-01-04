package com.example.roomwordsample2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    /*WordRepositoryをユニットテストするためには、Application依存関係を削除する必要があることに注意してください。
    このサンプルはテストを目的としたものではありません。
    アンドロイドアーキテクチャコンポーネントのリポジトリ https://github.com/googlesamples にある BasicSample を参照してください。*/

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

//    Roomはすべてのクエリを別スレッドで実行します。
//    Observed LiveDataは、データが変更されたときにオブザーバーに通知します。
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

//    UI以外のスレッドでこれを呼び出す必要があります。そうしないと、アプリが例外をスローします。
//    Roomは、メインスレッドで長時間実行される操作を行わないようにし、UIをブロックします。
    void insert(Word word){
        WordRoomDatabase.databaseWriteExecutor.execute(()->{
            mWordDao.insert(word);
        });
    }
}
