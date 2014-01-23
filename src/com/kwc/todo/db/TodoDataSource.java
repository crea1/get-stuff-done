package com.kwc.todo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.kwc.todo.TodoItem;

import java.util.ArrayList;
import java.util.List;

import static com.kwc.todo.db.SQLiteHelper.COLUMN_CREATED_DATE;
import static com.kwc.todo.db.SQLiteHelper.COLUMN_DETAILS;
import static com.kwc.todo.db.SQLiteHelper.COLUMN_END_DATE;
import static com.kwc.todo.db.SQLiteHelper.COLUMN_ID;
import static com.kwc.todo.db.SQLiteHelper.COLUMN_PARENT_ID;
import static com.kwc.todo.db.SQLiteHelper.COLUMN_TITLE;
import static com.kwc.todo.db.SQLiteHelper.TABLE_TODOITEM;

/**
 * @author Marius Kristensen
 */
public class TodoDataSource {
    private SQLiteHelper dbHelper;
    private SQLiteDatabase database;
    private String[] allColumns = {COLUMN_ID, COLUMN_TITLE, COLUMN_DETAILS, COLUMN_CREATED_DATE, COLUMN_END_DATE, COLUMN_PARENT_ID};

    public TodoDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public TodoItem saveTodoItem(TodoItem todoItem) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, todoItem.getTitle());
        values.put(COLUMN_DETAILS, todoItem.getDetails());
        values.put(COLUMN_CREATED_DATE, todoItem.getCreatedDate());
        values.put(COLUMN_END_DATE, todoItem.getEndDate());
        values.put(COLUMN_PARENT_ID, todoItem.getParentId());
        long id = database.insert(TABLE_TODOITEM, null, values);
        Cursor cursor = database.query(TABLE_TODOITEM, allColumns, COLUMN_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        TodoItem savedTodoItem = cursorToTodoItem(cursor);
        cursor.close();
        Log.d(TodoDataSource.class.getName(), "Saved todoitem with id = " + savedTodoItem.getId());
        return savedTodoItem;
    }

    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItems = new ArrayList<TodoItem>();
        Cursor cursor = database.query(TABLE_TODOITEM, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TodoItem todoItem = cursorToTodoItem(cursor);
            todoItems.add(todoItem);
            cursor.moveToNext();
        }
        cursor.close();
        return todoItems;
    }

    public void deleteTodoItem(TodoItem todoItem) {
        Log.d(TodoDataSource.class.getName(), "Deleting todoitem with id = " + todoItem.getId());
        database.delete(TABLE_TODOITEM, COLUMN_ID + " = " + todoItem.getId(), null);
    }

    private TodoItem cursorToTodoItem(Cursor cursor) {
        TodoItem todoItem = new TodoItem();
        todoItem.setId(cursor.getLong(0));
        todoItem.setTitle(cursor.getString(1));
        todoItem.setDetails(cursor.getString(2));
        return todoItem;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}


