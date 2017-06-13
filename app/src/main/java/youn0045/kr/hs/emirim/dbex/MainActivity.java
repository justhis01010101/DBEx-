package youn0045.kr.hs.emirim.dbex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    class MyDBHelper extends SQLiteOpenHelper{
        //생성자 호출 시 저스디스라는 데이터베이스가 생성된다
        public MyDBHelper(Context context) {
            super(context, "JUSTHIS", null, 1);
        }
        //저스디스 테이블이라는 이름의 테이블 생성

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql= "create table justhis table(justhis name text not null primary key, justhiscount integer)";
            sqLiteDatabase.execSQL(sql);
        }
        //이미 저스디스 테이블이 존재한다면 기존의 테이블을 삭제하고 새로 테이블을 만들때 호출
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            String sql= "drop table if exist justhis table"; //만약에 저스디스 테이블이 존재한다면 테이블을 삭제해라
            sqLiteDatabase.execSQL(sql);
            onCreate(sqLiteDatabase);
        }
    }
}
