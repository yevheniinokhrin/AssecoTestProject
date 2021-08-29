package com.nokhrin.dao;

import com.nokhrin.config.HibernateConfig;
import com.nokhrin.model.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmployeeDao {
    Session session;
    public void saveEmployee(Employee employee) {

        session = HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;


        try {
            tx = session.beginTransaction();

            session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void deleteEmployee(int employeeID){
        session = HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee)session.get(Employee.class, employeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public List<Employee> listEmployees( ){
        session = HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;
        List<Employee> employeeList = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Employee").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                employeeList.add((Employee) iterator.next());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeList;
    }
    public Employee getEmployeeById(int id){
        session = HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;
        Employee employee = null;
        try {
            tx = session.beginTransaction();
            employee = session.get(Employee.class, id);

            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employee;
    }
    public String getAvgSalary(int districtId){
        session = HibernateConfig.getSessionFactory().openSession();
        Transaction tx = null;
        String districtName;
        String avgSalary;
        String result = "No employees";
        try {
            tx = session.beginTransaction();


            Query query = session.createQuery("select e.district.districtName, avg(e.salary) from Employee e where e.district.id=" + districtId + " group by e.district.districtName");
            List<Object[]> results = query.list();
            if(results.size()>0) {
                districtName=results.get(0)[0].toString();
                avgSalary=results.get(0)[1].toString();
                result=districtName+": "+avgSalary;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
}
