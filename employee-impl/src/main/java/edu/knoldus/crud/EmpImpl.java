package edu.knoldus.crud;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import edu.knoldus.crud.utiliy.Employee;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class EmpImpl implements EmpAPI {

    private Employee ayush = new Employee("1","ayush","24","new delhi");
    private Employee vinisha = new Employee("2","vinisha","23","dwarka");
    private Map<Integer,Employee> empDB = new HashMap<>();

    @Override
    public ServiceCall<NotUsed, Employee> getEmp(String id) {
        empDB.put(1,ayush);
        empDB.put(2,vinisha);
            return request -> CompletableFuture.completedFuture(
                    Optional.of(empDB.get(Integer.parseInt(id))).orElseThrow(RuntimeException::new)
            );
    }

    @Override
    public ServiceCall<NotUsed, String> deleteEmp(String id) {
        return emp -> {
            if(empDB.containsKey(Integer.parseInt(id))){
                empDB.remove(Integer.parseInt(id));
                return CompletableFuture.completedFuture("Employee with ID " + id + " is removed");
            }
            else{
                return CompletableFuture.completedFuture("Employee with ID " + id + " doesn't exist");
            }
        };

    }

    @Override
    public ServiceCall<Employee, String> postEmp() {
        return emp -> {
            if(empDB.containsKey(Integer.parseInt(emp.getId()))){
                return CompletableFuture.completedFuture("User"+ emp.getName() + " already exists");
            }
            else
            {
                empDB.put(Integer.parseInt(emp.getId()),new Employee(emp.getId(),emp.getName(),emp.getAge(),emp.getCity()));
                return CompletableFuture.completedFuture("User" + emp.getName() + " added");
            }
        };
    }

    @Override
    public ServiceCall<Employee, String> updateEmp(String id) {
        return emp -> {
            if(empDB.containsKey(Integer.parseInt(id))){
                empDB.put(Integer.parseInt(id),new Employee(id,emp.getName(),emp.getAge(),emp.getCity()));
                return CompletableFuture.completedFuture("User " + emp.getName()+" added");
            }
            else{
                return CompletableFuture.completedFuture("User not found");
            }
        };
    }


}
