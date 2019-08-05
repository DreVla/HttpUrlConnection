package com.hpmtutorial.httpurlconnection.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    private String editedName,editedAge,editedSalary;

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
        editedName = editTextName.getText().toString();
        editedAge = editTextAge.getText().toString();
        editedSalary = editTextSalary.getText().toString();
        editTextName.setEnabled(false);
        editTextAge.setEnabled(false);
        editTextSalary.setEnabled(false);
        new RequestAsyncUpdate().execute();
    }

    public void deleteEmployee(View view) {
        new RequestAsyncDelete().execute();
    }

    public class RequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                return RequestHandler.sendGet("http://dummy.restapiexample.com/api/v1/employee/" + idToFind);
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

    public class RequestAsyncDelete extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                return RequestHandler.sendDelete("http://dummy.restapiexample.com/api/v1/delete/" + idToFind);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("200")) {
                Toast.makeText(EmployeeDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EmployeeDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class RequestAsyncUpdate extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //PUT Request
                JSONObject putDataParams = new JSONObject();
                putDataParams.put("name", editedName);
                putDataParams.put("salary", editedSalary);
                putDataParams.put("age", editedAge);
                return RequestHandler.sendPut("http://dummy.restapiexample.com/api/v1/update/" + idToFind, putDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("200")) {
                Toast.makeText(EmployeeDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EmployeeDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
