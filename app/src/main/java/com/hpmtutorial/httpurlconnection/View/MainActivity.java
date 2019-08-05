package com.hpmtutorial.httpurlconnection.View;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.hpmtutorial.httpurlconnection.Model.Utils.Employee;
import com.hpmtutorial.httpurlconnection.Model.Utils.RequestHandler;
import com.hpmtutorial.httpurlconnection.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView getAllList;
    private JSONArray jsonArray;
    private ArrayList<Employee> listElements;
    ArrayAdapter<Employee> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllList = findViewById(R.id.employee_list_view);
        new RequestAsync().execute();

        getAllList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Employee item = (Employee) adapterView.getItemAtPosition(i);
                int employeeId = item.getId();
                Intent intent = new Intent(getApplicationContext(), EmployeeDetailsActivity.class);
                intent.putExtra("id", employeeId);
                Toast.makeText(MainActivity.this, "id is " + employeeId, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    public void getAllEmployees(View view) {
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listElements);
        getAllList.setAdapter(arrayAdapter);
    }

    public void addNewEmployee(View view) {
        Intent intent = new Intent(this, AddEmployeeActivity.class);
        startActivity(intent);
    }

    public class RequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                return RequestHandler.sendGet("	http://dummy.restapiexample.com/api/v1/employees");

                // POST Request
//                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("name", "DreVlaTestAndroid");
//                postDataParams.put("salary", "1111");
//                postDataParams.put("age", "1111");
//
//                return RequestHandler.sendPost("http://dummy.restapiexample.com/api/v1/create",postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Log.d("Get All Result", "onPostExecute: " + s);
                try {
                    jsonArray = new JSONArray(s);
                    listElements = new ArrayList<>();
                    Employee employee;
                    int jsonArraySize = jsonArray.length();
                    for (int i = jsonArraySize - 10; i < jsonArraySize; i++) {
                        employee = new Gson().fromJson(jsonArray.get(i).toString(), Employee.class);
                        listElements.add(employee);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new RequestAsync().execute();
        getAllList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Employee item = (Employee) adapterView.getItemAtPosition(i);
                int employeeId = item.getId();
                Intent intent = new Intent(getApplicationContext(), EmployeeDetailsActivity.class);
                intent.putExtra("id", employeeId);
                Toast.makeText(MainActivity.this, "id is " + employeeId, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}
