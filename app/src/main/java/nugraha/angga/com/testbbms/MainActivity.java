package nugraha.angga.com.testbbms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import nugraha.angga.com.testbbms.model.DummyData;

public class MainActivity extends AppCompatActivity  {
    private RecyclerView rvTest;
    private Adapter adapter;
    private ArrayList<DummyData> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = new ArrayList<>();
        for (int i=0;i<10;i++){
            dataList.add(new DummyData("https://techcrunch.com/wp-content/uploads/2015/04/codecode.jpg?w=1390&crop=1","Test Title","bla bla bla bla bla",3f,"04.35"));
        }

        rvTest = (RecyclerView)findViewById(R.id.rvTest);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvTest.setLayoutManager(mLayoutManager);
        adapter = new Adapter(getApplicationContext(), dataList);
        rvTest.setAdapter(adapter);
    }

}
