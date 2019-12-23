package com.biswash.employeeapi.api;

import com.biswash.employeeapi.model.Employee;
import com.biswash.employeeapi.model.EmployeeCUD;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeAPI {

    @GET("employees")
    Call<List<Employee>> getAllEmployees();

    //Get employee on basis of EmpId
    @GET("employee/{empID}")
    Call<Employee> getEmployeeByID(@Path("empID") int empId);

    @POST("create")
    Call<Void> registerEmployee(@Body EmployeeCUD emp);

    //update
    @PUT("update/{empID}")
    Call<Void> updateEmployee(@Path("empID") int empId,@Body EmployeeCUD emp);

    //delete
    @DELETE("delete/{empID}")
    Call<Void> deleteEmployee(@Path("empID") int empId);
}


