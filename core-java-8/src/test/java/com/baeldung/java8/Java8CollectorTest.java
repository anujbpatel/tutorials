package com.baeldung.java8;


import com.baeldung.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java8CollectorTest {

    List<Employee> empList = new ArrayList<Employee>();

    @Before
    public void setUp() {
        empList.add(new Employee(1, "John", 100000, 1));
        empList.add(new Employee(2, "Joe", 200000, 2));
        empList.add(new Employee(3, "Smith", 300000, 3));
        empList.add(new Employee(4, "Jack", 900000, 1));
        empList.add(new Employee(5, "Alex", 500000, 3));
        empList.add(new Employee(6, "Justin", 800000, 2));
        empList.add(new Employee(7, "Bob", 700000, 1));
    }

    @Test
    public void joiningTest() {
        String joined = Stream.of("1", "2", "3").collect(Collectors.joining(",", "<", ">"));
        Assert.assertEquals("simple example of joining characters sequences", joined, "<1,2,3>");
    }


    @Test
    public void summingDouble() {
        Double salarySum = empList.stream().collect(Collectors.summingDouble(Employee::getSalary));
        Assert.assertEquals("Sum of all employee salaries", salarySum, Double.valueOf(3500000));
    }
}
