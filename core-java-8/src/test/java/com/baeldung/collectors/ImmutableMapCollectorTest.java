package com.baeldung.collectors;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.Employee;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public class ImmutableMapCollectorTest {

    List<Employee> empList = new ArrayList<Employee>();
    DoubleSummaryStatistics stats = new DoubleSummaryStatistics();

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

    public DoubleSummaryStatistics summarizingEmployeeSalaryDouble(List<Employee> empList) {
        return empList.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
    }

    //min(Comparator)
    public Optional<Employee> employeeWithMinSalary(List<Employee> empList) {
        Optional<Employee> min = empList.stream().collect(Collectors.minBy(new Comparator<Employee>() {
            public int compare(Employee o1, Employee o2) {
                return o1.compare(o1, o2);
            }
        }));
        return min;
    }

    //max(Comparator)
    public static Optional<Employee> employeeWithMaxSalary(List<Employee> empList) {
        Optional<Employee> max = empList.stream().collect(Collectors.maxBy(new Comparator<Employee>() {
            public int compare(Employee o1, Employee o2) {
                return o1.compare(o1, o2);
            }
        }));
        return max;
    }

    //groupingBy(Function, Collector)
    public static Map<Integer, Optional<Employee>> groupByReducingMax(List<Employee> empList) {
        return empList.stream().collect(Collectors.groupingBy(Employee::getDeptId, Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Employee::getSalary)))));
    }

    //averagingDouble
    public static Double averagingDouble(List<Employee> empList) {
        return empList.stream().collect(Collectors.averagingDouble(Employee::getSalary));
    }

    //mappingDeptEmpSal(function, collector)
    public static Map<Integer, Set<Long>> mappingDeptEmpSal(List<Employee> empList) {
        return empList.stream().collect(Collectors.groupingBy(Employee::getDeptId, Collectors.mapping(Employee::getSalary, Collectors.toSet())));
    }

    //Joining(delimiter, prefix, suffix)
    public static String joining() {
        return Stream.of("1", "2", "3").collect(Collectors.joining(",", "<", ">"));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void joiningTest() {
        Assert.assertEquals("simple example of joining characters sequences", joining(), "<1,2,3>");
    }

    @Test
    public void mappingDeptEmployeeSalaryTest() {
        Map<Integer, Set<Long>> deptSalaryMap = new HashMap<Integer, Set<Long>>();
        Set<Long> dept1SalarySet = new HashSet<Long>();
        deptSalaryMap.put(1, dept1SalarySet);
        dept1SalarySet.add(100000L);
        dept1SalarySet.add(900000L);
        dept1SalarySet.add(700000L);
        Set<Long> dept2SalarySet = new HashSet<Long>();
        deptSalaryMap.put(2, dept2SalarySet);
        dept2SalarySet.add(200000L);
        dept2SalarySet.add(800000L);
        Set<Long> dept3SalarySet = new HashSet<Long>();
        deptSalaryMap.put(3, dept3SalarySet);
        dept3SalarySet.add(300000L);
        dept3SalarySet.add(500000L);
        Assert.assertEquals("Mapping employee salaries to department", mappingDeptEmpSal(empList), deptSalaryMap);
    }

    @Test
    public void averagingEmployeeSalary() {
        Assert.assertEquals("", averagingDouble(empList), 500000.0, 0);
    }

    @Test
    public void groupingByDeptIdReducingByMaxSalary() {
        Map<Integer, Optional<Employee>> result = new HashMap<Integer, Optional<Employee>>();
        result.put(1, Optional.of(empList.get(3)));
        result.put(2, Optional.of(empList.get(5)));
        result.put(3, Optional.of(empList.get(4)));
        Assert.assertEquals("Grouping by department id reducing by maximum salary", groupByReducingMax(empList), result);
    }

    @Test
    public void maxSalaryEmployee() {
        Assert.assertEquals("Employee with maximum salary", employeeWithMaxSalary(empList).get().getSalary(), empList.get(3).getSalary());
    }

    @Test
    public void minSalaryEmployee() {
        Assert.assertEquals("Employee with minimum salary", employeeWithMinSalary(empList).get().getSalary(), empList.get(0).getSalary());
    }

    @Test
    public void summarizingDouble() {
        Assert.assertEquals("Statistics of employee salaries like count, sum, min, average, max", summarizingEmployeeSalaryDouble(empList).toString(), stats.toString());
    }

    @Test
    public void summingDouble() {
        Double salarySum = empList.stream().collect(Collectors.summingDouble(Employee::getSalary));
        Assert.assertEquals("Sum of all employee salaries", salarySum, new Double(stats.getSum()));
    }
}
