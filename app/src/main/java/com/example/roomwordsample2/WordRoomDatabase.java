package com.example.roomwordsample2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//RoomのクラスはRoomDatabaseを拡張した抽象クラスにする
/*データベースの移行はこのコードラボの範囲を超えているので、ビルド時の警告を避けるために、ここでは exportSchema を false に設定しています。
実際のアプリでは、現在のスキーマをバージョン管理システムにチェックできるように、
スキーマをエクスポートするためにRoomが使用するディレクトリを設定することを検討する必要があります。*/
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();

    private static volatile WordRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static WordRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (WordRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class,"word_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

//            アプリの再起動でデータ保持する場合は次ブロックをコメントアウト
            databaseWriteExecutor.execute(()->{
                WordDao dao = INSTANCE.wordDao();
                dao.deleteAll();
                //データベースにバックグラウンドでデータを入力します。
                // もっと多くの単語から始めたい場合は、単語を追加してください。
                Word word = new Word("Hello");
                dao.insert(word);
                word = new Word("World");
                dao.insert(word);
            });
        }
    };
}
