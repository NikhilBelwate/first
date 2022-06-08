package com.demo;

import com.demo.model.Condition;
import com.demo.model.Employee;
import com.demo.service.EmployeeService;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/emp")
public class EmployeeController {
    @javax.inject.Inject
    private EmployeeService employeeService;

    @Get("/list")
    public List<Employee> getList(){
        List<Employee> empList=employeeService.getAllEmployees();
        return empList;
    }
    @Get("/get/{id}")
    public Employee getEmployee(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);
    }
    @Post("/add")
    public String addEmployee(@Body Employee employee){
        employeeService.addEmployee(employee);
        return "{\"status\":\"saved\"}";
    }

    @Delete("/delete/{id}")
    public String deleteEmployee(@PathVariable Integer id){
        employeeService.deleteEmployee(id);
        return "{\"status\":\"deleted\"}";
    }
    @Put("/update")
    public String updateEmployee(@Body Employee employee){
        employeeService.updateEmployee(employee);
        return "{\"status\":\"updated\"}";
    }
    @Property(name="query.error")
    private String errorMsg;

    @Post("/emp/find")
    public Object findEmployee(@Header HttpHeaders headers, @Body List<Condition> conditions){

        try{
            if(headers.get("token").contains("abc***52")) {
                List<Employee> empList = employeeService.fineEmployees(conditions);
                return empList;
            }else{
                return "Token mismatch";
            }
        }catch (Exception se){
            return errorMsg;
        }
    }
    @Put("/find")
    public List<Employee> findEmployee(@Body Condition condition) {
        List<Employee> empList = employeeService.findEmployees(condition);
        return empList;
    }
}
