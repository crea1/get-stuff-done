package com.kwc.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.kwc.todo.db.TodoDataSource;

import java.util.List;

public class ListTodosActivity extends Activity {

    private TodoDataSource datasource;
    private ArrayAdapter<TodoItem> adapter;
    private List<TodoItem> todos;
    private static final int ADD_ITEM_ACTIVITY = 1;
    private ListView listView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        datasource = new TodoDataSource(this);
        datasource.open();
        todos = datasource.getAllTodoItems();

        adapter = new ArrayAdapter<TodoItem>(this, android.R.layout.simple_list_item_1, todos);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datasource.deleteTodoItem(adapter.getItem(position));
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();
            }
        });
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivityForResult(i, ADD_ITEM_ACTIVITY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_ITEM_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    datasource.open();
                    todos = datasource.getAllTodoItems();
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(new ArrayAdapter<TodoItem>(this, android.R.layout.simple_list_item_1, todos));
                    Log.d(ListTodosActivity.class.getName(), "Refresh... " + todos.size());
                }
                break;
        }

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
