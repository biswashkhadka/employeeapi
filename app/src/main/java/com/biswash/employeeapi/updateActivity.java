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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class updateActivity extends AppCompatActivity {
    private final static String BASE_URL = "http://dummy.restapiexample.com/api/v1/";
    private Button btnS, btnUpdate, btnDelete;
    private EditText etName, etAge, etSalary;
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
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etSalary = findViewById(R.id.etSalary);
        etEmployee = findViewById(R.id.etEmployee);

        btnS.setOnClickListener(new View.OnClickListener() {


            private final void CreateInstance() {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                employeeAPI = retrofit.create(EmployeeAPI.class);
            }

            @Override
            public void onClick(View view) {
                loadData();
            }

            private void loadData() {

                CreateInstance();
                Call<Employee> listCall = employeeAPI.getEmployeeByID(Integer.parseInt(etEmployee.getText().toString()));
                listCall.enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        etName.setText(response.body().getEmployee_name());
                        etSalary.setText(Float.toString(response.body().getEmployee_salary()));
                        etAge.setText(Integer.toString(response.body().getEmployee_age()));
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        Toast.makeText(updateActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            private void updateEmployee() {
                CreateInstance();
                EmployeeCUD employeeCUD = new EmployeeCUD(
                        etName.getText().toString(),
                        Integer.parseInt(etSalary.getText().toString()),
                        Integer.parseInt(etAge.getText().toString())

                );
                Call<Void> voidCall = employeeAPI.updateEmployee(Integer.parseInt(etEmployee.getText().toString()), employeeCUD);

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

            private void deleteEmployee() {
                CreateInstance();
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


        });
    }
}