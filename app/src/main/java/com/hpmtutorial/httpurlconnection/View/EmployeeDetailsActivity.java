package com.hpmtutorial.httpurlconnection.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.hpmtutorial.httpurlconnection.Model.Utils.Employee;
import com.hpmtutorial.httpurlconnection.Model.Utils.RequestHandler;
import com.hpmtutorial.httpurlconnection.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeDetailsActivity extends AppCompatActivity {

    private EditText editTextName, editTextSalary, editTextAge;
    private JSONObject jsonObject;
    private int idToFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        idToFind = getIntent().getIntExtra("id", -1);

        editTextName = findViewById(R.id.employee_details_name_textview);
        editTextSalary = findViewById(R.id.employee_details_salary_textview);
        editTextAge = findViewById(R.id.employee_details_age_textview);

        new RequestAsync().execute();
    }

    public void enableEdit(View view) {
        editTextName.setEnabled(true);
        editTextAge.setEnabled(true);
        editTextSalary.setEnabled(true);
    }

    public void executeUpdateEmployee(View view) {
        editTextName.setEnabled(false);
        editTextAge.setEnabled(false);
        editTextSalary.setEnabled(false);
    }

    public class RequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                return RequestHandler.sendGet("	http://dummy.restapiexample.com/api/v1/employee/" + idToFind);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {

                try {
                    jsonObject = new JSONObject(s);
                    Employee employee = new Gson().fromJson(jsonObject.toString(), Employee.class);
                    Log.d("Find Result", "onPostExecute: " + employee.getName());
                    editTextName.setText(employee.getName());
                    editTextSalary.setText(String.valueOf(employee.getSalary()));
                    editTextAge.setText(String.valueOf(employee.getAge()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
