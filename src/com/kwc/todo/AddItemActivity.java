package com.kwc.todo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.kwc.todo.db.TodoDataSource;

/**
 * @author Marius Kristensen
 */
public class AddItemActivity extends Activity {

    TodoDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additem);
        datasource = new TodoDataSource(this);
        datasource.open();

        Button addButton = (Button) findViewById(R.id.saveTodoItemButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText) findViewById(R.id.editText);
                TodoItem todoItem = datasource.saveTodoItem(new TodoItem(title.getText().toString(), "herp derp"));
                setResult(RESULT_OK);
                finish();
            }
        });

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
