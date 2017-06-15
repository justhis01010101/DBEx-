package youn0045.kr.hs.emirim.dbex;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editName, editCount, editResultName, editResultCount;
    Button but_init;
    Button but_insert;
    Button but_select;
    Button but_update;
    MyDBHelper myHelper;
    SQLiteDatabase sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = (EditText) findViewById(R.id.edit_group_name);
        editCount = (EditText) findViewById(R.id.edit_group_cnt);
        editResultName = (EditText) findViewById(R.id.edit_result_name);
        editResultCount = (EditText) findViewById(R.id.edit_result_cnt);
        but_init = (Button) findViewById(R.id.but_init);
        but_insert = (Button) findViewById(R.id.but_insert);
        but_select = (Button) findViewById(R.id.but_select);
        but_update=(Button)findViewById(R.id.but_update);

        // 데이터 베이스 생성
        myHelper = new MyDBHelper(this);
        //기존의 테이블이 존재하면 삭제하고 테이블을 새로 생성한다.
        but_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqldb = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqldb, 1, 2);
                sqldb.close();
            }
        });
        but_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqldb = myHelper.getWritableDatabase();
                String sql = "insert into justhis values('" + editName.getText() + "'," + editCount.getText() + ")";
                sqldb.execSQL(sql);
                sqldb.close();
                Toast.makeText(MainActivity.this, "저장됨", Toast.LENGTH_LONG).show();
            }
        });
        but_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqldb = myHelper.getReadableDatabase();
                String sql = "select * from justhis";
                Cursor cursor = sqldb.rawQuery(sql, null);
                String names = "justhis  이름" + "\r\n" + "===============" + "\r\n";
                String counts = "justhis  인원수" + "\r\n" + "===============" + "\r\n";
                while (cursor.moveToNext()) { //data형이 존재할동안만 반복문 실행
                    names+=cursor.getString(0)+"\r\n";//첫번째 calmn
                    counts+=cursor.getInt(1)+"\r\n";//\r\n: 다음줄로 연결
                }
                editResultName.setText(names);
                editResultCount.setText(counts);
                cursor.close();
                sqldb.close();
            }
        });
        but_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqldb = myHelper.getWritableDatabase();
                String sql = "update justhis set justhiscount="+editCount.getText()+" where justhisName='"+"'";
                sqldb.execSQL(sql);
                sqldb.close();
                Toast.makeText(MainActivity.this, "인원수가 수정 됨", Toast.LENGTH_LONG).show();
            }
        });


    }
    class MyDBHelper extends SQLiteOpenHelper {
        //생성자 호출 시 저스디스라는 데이터베이스가 생성된다
        public MyDBHelper(Context context) {
            super(context, "JUSTHIS", null, 1);
        }
        //저스디스 테이블이라는 이름의 테이블 생성

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sql = "create table justhis(name text not null primary key, justhiscount integer)";
            sqLiteDatabase.execSQL(sql);
        }

        //이미 저스디스 테이블이 존재한다면 기존의 테이블을 삭제하고 새로 테이블을 만들때 호출
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            String sql = "drop table if exists justhis"; //만약에 저스디스 테이블이 존재한다면 테이블을 삭제해라
            sqLiteDatabase.execSQL(sql);
            onCreate(sqLiteDatabase);
        }
    }
}