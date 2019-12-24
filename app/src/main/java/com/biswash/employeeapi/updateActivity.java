package com.biswash.employeeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.biswash.employeeapi.api.EmployeeAPI;
import com.biswash.employeeapi.model.Employee;
import com.biswash.employeeapi.model.EmployeeCUD;
import com.biswash.employeeapi.url.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class updateActivity extends AppCompatActivity {
    private Button btnS, btnUpdate, btnDelete;
    private EditText eteName, eteAge, eteSalary;
    private EditText etEmployee;
    Retrofit retrofit;
    EmployeeAPI employeeAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btnS = findViewById(R.id.btnS);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        eteName = findViewById(R.id.eteName);
        eteAge = findViewById(R.id.eteAge);
        eteSalary = findViewById(R.id.eteSalary);
        etEmployee = findViewById(R.id.etEmployee);


        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee();
            }
        });

    }

   /* private void CreateInstance() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        employeeAPI = retrofit.create(EmployeeAPI.class);

    }
*/

    private void loadData() {
        //CreateInstance();
        EmployeeAPI employeeAPI = URL.createInstance().create(EmployeeAPI.class);
        Call<Employee> listCall = employeeAPI.getEmployeeByID(Integer.parseInt(etEmployee.getText().toString()));


        listCall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                eteName.setText(response.body().getEmployee_name());
                eteAge.setText(Integer.toString(response.body().getEmployee_age()));
                eteSalary.setText(Float.toString(response.body().getEmployee_salary()));
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(updateActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateEmployee(){
        //CreateInstance();
        EmployeeAPI employeeAPI = URL.createInstance().create(EmployeeAPI.class);
        EmployeeCUD employeeCUD = new EmployeeCUD(
                eteName.getText().toString(),
                Float.parseFloat( eteSalary.getText().toString()),
                Integer.parseInt(eteAge.getText().toString())

        );
        Call<Void> voidCall = employeeAPI.updateEmployee(Integer.parseInt(etEmployee.getText().toString()),employeeCUD);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(updateActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(updateActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void deleteEmployee(){
        EmployeeAPI employeeAPI = URL.createInstance().create(EmployeeAPI.class);
        Call<Void> voidCall = employeeAPI.deleteEmployee(Integer.parseInt(etEmployee.getText().toString()));

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(updateActivity.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(updateActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
