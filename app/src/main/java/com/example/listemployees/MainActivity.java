package com.example.listemployees;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listemployees.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvEmployees;
    private int nrmlColor = Color.rgb(0xED, 0xE2, 0x75);
    private int slctColor = Color.rgb(0xE2, 0xA7, 0x6F);
    private int curItem = -1;
    private View curView = null;
    private ActivityMainBinding binding;
    private List<Human> employees;
    ArrayAdapter<Human> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        this.lvEmployees = (ListView) this.findViewById(R.id.lvEmployees);
        employees = new ArrayList<Human>();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        employees.addAll(Arrays.asList(new Human("Bill", "White", true, Calendar.getInstance()),
                new Human("Tom", "Black", true, Calendar.getInstance()),
                new Human("Mary", "Gray", false, Calendar.getInstance()),
                new Human("Linda", "Blue", false, Calendar.getInstance())));

        adapter =
                new ArrayAdapter<Human>(this,
                        R.layout.human_item, R.id.empName, employees) {
                    @Override
                    public View getView(int position,
                                        View convertView, ViewGroup parent) {
                        View view = super.getView(position,
                                convertView, parent);
                        Human h = this.getItem(position);
                        TextView empName = (TextView) view.
                                findViewById(R.id.empName);

                        TextView empSurname = (TextView) view.
                                findViewById(R.id.empSurname);
                        TextView empBirth = (TextView) view.
                                findViewById(R.id.empBirth);
                        empName.setText(h.firstName);
                        empSurname.setText(h.lastName);
                        SimpleDateFormat dateFormat
                                = new SimpleDateFormat("dd-MM-yyyy");
                        empBirth.setText(dateFormat.format(h.birthDay.getTime()));
                        ImageView iv1 = (ImageView) view.findViewById(R.id.iv1);
                        try (InputStream IS = h.gender? getApplicationContext().getAssets().open("male-avatar.png"): getApplicationContext().getAssets().open("female-avatar.png")) {
                            Bitmap bmp = BitmapFactory.decodeStream(IS);
                            iv1.setImageBitmap(bmp);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        return view;
                    }
                };
        this.lvEmployees.setAdapter(adapter);
        registerForContextMenu(this.lvEmployees);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        new MenuInflater(this).inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.context_delete:
                employees.remove(i.position);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete:
                employees.remove(i.position);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}