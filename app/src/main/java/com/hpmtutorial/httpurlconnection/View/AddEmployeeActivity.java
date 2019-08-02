package com.hpmtutorial.httpurlconnection.View;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hpmtutorial.httpurlconnection.Model.Utils.RequestHandler;
import com.hpmtutorial.httpurlconnection.R;

import org.json.JSONObject;

public class AddEmployeeActivity extends AppCompatActivity {

    private EditText nameEditText, salaryEditText, ageEditText;
    private String newEmployeeName, newEmployeeSalary, newEmployeeAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        nameEditText = findViewById(R.id.add_new_employee_name_edittext);
        salaryEditText = findViewById(R.id.add_new_employee_salary_edittext);
        ageEditText = findViewById(R.id.add_new_employee_age_edittext);

    }

    public void executeAddNewEmployee(View view) {
        newEmployeeName = nameEditText.getText().toString();
        newEmployeeSalary = salaryEditText.getText().toString();
        newEmployeeAge = ageEditText.getText().toString();
        new RequestAsync().execute();
    }

    public class RequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("name", newEmployeeName);
                postDataParams.put("salary", newEmployeeSalary);
                postDataParams.put("age", newEmployeeAge);

                return RequestHandler.sendPost("http://dummy.restapiexample.com/api/v1/create", postDataParams);
            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Log.d("Add Result", "onPostExecute: " + s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }


    }
}
