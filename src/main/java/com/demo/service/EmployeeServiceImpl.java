package com.demo.service;

import com.demo.model.Condition;
import com.demo.model.Employee;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import io.micronaut.context.annotation.Bean;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Bean
public class EmployeeServiceImpl implements EmployeeService{
    private Firestore firestoreDB;
    @Override
    public List<Employee> getAllEmployees() {
        firestoreDB= FirestoreClient.getFirestore();
        List<Employee> eList=new LinkedList<>();
        ApiFuture<QuerySnapshot> future = firestoreDB.collection("employee").get();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            documents.forEach(queryDocumentSnapshot -> {
                eList.add(queryDocumentSnapshot.toObject(Employee.class));
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return eList;
    }

    @Override
    public Employee getEmployeeById(Integer ID) {
        firestoreDB= FirestoreClient.getFirestore();
        DocumentReference documentReference=firestoreDB.collection("employee").document(ID.toString());
        try {
            DocumentSnapshot document=documentReference.get().get();
            if(document.exists()){
                return document.toObject(Employee.class);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void addEmployee(Employee employee) {
        firestoreDB= FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future=firestoreDB.collection("employee")
                .document(employee.getEmp_id().toString()).set(employee);
    }

    @Override
    public boolean deleteEmployee(Integer ID) {
        firestoreDB= FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future=firestoreDB.collection("employee")
                .document(ID.toString()).delete();
        return false;
    }

    @Override
    public void updateEmployee(Employee employee) {
        firestoreDB= FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future=firestoreDB.collection("employee")
                .document(employee.getEmp_id().toString()).set(employee);
    }

    @Override
    public List<Employee> fineEmployees(List<Condition> conditions) {
        firestoreDB= FirestoreClient.getFirestore();
        List<Employee> eList=new LinkedList<>();
        CollectionReference collectionReference= firestoreDB.collection("employee");
        Query query=collectionReference.select("emp_id","name","roll","education","age");

        for(Condition c:conditions) {
            switch (c.getOperator()) {
                case "==":
                    query = query.whereEqualTo(c.getParam(), c.getValue());
                    break;
                case "!=":
                    query = query.whereNotEqualTo(c.getParam(), c.getValue());
                    break;
                case ">":
                    query = query.whereGreaterThan(c.getParam(), c.getValue());
                    break;
                case "<":
                    query = query.whereLessThan(c.getParam(), c.getValue());

            }
        }
        try {
            ApiFuture<QuerySnapshot> future = query.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            documents.forEach(queryDocumentSnapshot -> {
                eList.add(queryDocumentSnapshot.toObject(Employee.class));
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return eList;
    }

    @Override
    public List<Employee> findEmployees(Condition condition) {
        firestoreDB= FirestoreClient.getFirestore();
        List<Employee> eList=new LinkedList<>();
        CollectionReference collectionReference= firestoreDB.collection("employee");
        Query query=collectionReference.select("emp_id","name","roll","education","age");

            switch (condition.getOperator()){
                case "==":
                    query=query.whereEqualTo(condition.getParam(),condition.getValue());
                    break;
                case "!=":
                    query=query.whereNotEqualTo(condition.getParam(),condition.getValue());
                    break;
                case ">":
                    query=query.whereGreaterThan(condition.getParam(),condition.getValue());
                    break;
                case "<":
                    query=query.whereLessThan(condition.getParam(),condition.getValue());

            }

        try {
            ApiFuture<QuerySnapshot> future = query.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            documents.forEach(queryDocumentSnapshot -> {
                eList.add(queryDocumentSnapshot.toObject(Employee.class));
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return eList;
    }
}
