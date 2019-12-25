package com.herokuapp.s3_test_hibernate;

import java.util.List;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageEmployee {
    private SessionFactory factory;
    private boolean factoryReady = false;

    public boolean isFactoryReady() {
        if (factory == null) {
            factoryReady = true;

            try {
                factory = new Configuration().configure().buildSessionFactory();
            } catch (Throwable e) {
                System.err.println("Failed to create a session factory.\n" + e);
                factoryReady = false;
                //throw new ExceptionInInitializerError(e);
            }
        }

        return factoryReady;
    }

    public Integer addEmployee(String firstName, String lastName, int salary){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            Employee employee = new Employee(firstName, lastName, salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    public void demo() {
        System.out.println("ManageEmployee demo running...");

        ManageEmployee manageEmployee = new ManageEmployee();

        if (manageEmployee.isFactoryReady()) {
            Integer empID1 = manageEmployee.addEmployee("Zara", "Ali", 1000);
        } else {
            System.out.println("Method isFactoryReady() returned false.");;
        }

        System.out.println("ManageEmployee demo finished.");
    }
}
