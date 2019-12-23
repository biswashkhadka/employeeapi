package com.biswash.employeeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.biswash.employeeapi.api.EmployeeAPI;
import com.biswash.employeeapi.model.Employee;
import com.biswash.employeeapi.url.URL;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class showemployeeActivity extends AppCompatActivity {
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showemployee);
        tvOutput = findViewById(R.id.tvOutput);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<List<Employee>> listCall = employeeAPI.getAllEmployees();

        //Asynchronous call

        listCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {

                if (!response.isSuccessful()){
                    Toast.makeText(showemployeeActivity.this, "Error code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Employee> employeeList = response.body();
                for (Employee emp : employeeList){
                    String data = "";
                    data += "Name is :" + emp.getEmployee_name() + "\n";
                    data += "Salary is :" + emp.getEmployee_salary() + "\n";
                    data += "employee_age :" + emp.getEmployee_age() + "\n";
                    data += "profile_image :" + emp.getProfile_image() + "\n";
                    tvOutput.append(data);
                }

            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.d("Msg", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(showemployeeActivity.this, "Error" + t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
