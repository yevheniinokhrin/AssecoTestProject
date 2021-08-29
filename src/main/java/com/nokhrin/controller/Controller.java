package com.nokhrin.controller;

import com.nokhrin.dao.DistrictDao;
import com.nokhrin.dao.EmployeeDao;
import com.nokhrin.dao.PositionDao;
import com.nokhrin.model.District;
import com.nokhrin.model.Employee;
import com.nokhrin.model.Position;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private EmployeeDao employeeDao;
    private DistrictDao districtDao;
    private PositionDao positionDao;


    public Controller(){
        employeeDao = new EmployeeDao();
        districtDao = new DistrictDao();
        positionDao = new PositionDao();
    }
    public void saveEmployee(String firstName, String lastName, String salary, int idDistrict, int idPosition) {
            Employee employee = new Employee();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setSalary(Integer.valueOf(salary));
            employee.setPosition(positionDao.getPositionById(idPosition + 1));
            employee.setDistrict(districtDao.getDistrictById(idDistrict + 1));
            employeeDao.saveEmployee(employee);
    }
    public void saveDistrict(String name){
            District district = new District();
            district.setDistrictName(name);
            districtDao.saveDistrict(district);
    }
    public void savePosition(String name){
            Position position = new Position();
            position.setPosition(name);
            positionDao.savePosition(position);

    }
    public void deleteEmployee(int selectedIndex, int id) {
        if (selectedIndex == 0) {
            employeeDao.deleteEmployee(id);
        }
    }

    public String getAvgSalary(int districtId) {
        return String.valueOf(employeeDao.getAvgSalary(districtId));
    }
    public String[][] getTableDataWithSalary() {
        return castListToArrayWithSalary(getTableDataByIndex(0));
    }

    public boolean isRemoveButtonEnabled(int selectedIndex) {
        if (selectedIndex == 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isRemoveAvgButton(int selectedIndex, int rows) {
        if (selectedIndex == 2 && rows>0) {
            return true;
        } else {
            return false;
        }
    }
    public String getUserInfo(int id) {
        Employee employee = employeeDao.getEmployeeById(id);
        if(employee==null){
            return "No employees yet";
        }
        String info = employee.getFirstName() + " " + employee.getLastName() + " from district" + employee.getDistrict().getDistrictName() + " works on position " + employee.getPosition().getPosition();
        return info;
    }

    public String[] getDataListForComboBox(int selectedIndex) {
        return getArrayForComboBox(getTableDataByIndex(selectedIndex));
    }

    public String[][] getDataListForTable(int selectedIndex) {
        return castListToArray(getTableDataByIndex(selectedIndex));
    }

    private List<String[]> getTableDataByIndex(int index) {
        List<String[]> s = new ArrayList<>();
        switch (index) {
            case 0:
                List<Employee> employees = employeeDao.listEmployees();
                employees.stream().forEach(e -> s.add(new String[]{String.valueOf(e.getId()), e.getLastName(), e.getDistrict().getDistrictName(),String.valueOf(e.getSalary())}));
                return s;
            case 1:
                List<Position> positions = positionDao.listPositions();
                positions.stream().forEach(p -> s.add(new String[]{String.valueOf(p.getId()), p.getPosition()}));
                return s;
            case 2:
                List<District> districts = districtDao.listDistricts();
                districts.stream().forEach(p -> s.add(new String[]{String.valueOf(p.getId()), p.getDistrictName()}));
                return s;
            default:
                return s;
        }
    }

    private String[][] castListToArray(List<String[]> list) {
        String[][] s = new String[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            s[i][0] = list.get(i)[0];
            s[i][1] = list.get(i)[1];
        }
        return s;
    }
    private String[][] castListToArrayWithSalary(List<String[]> list) {
        String[][] s = new String[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            s[i][0] = list.get(i)[0];
            s[i][1] = list.get(i)[1];
            s[i][2] = list.get(i)[2];
            s[i][3] = list.get(i)[3];
        }
        return s;
    }
    private String[] getArrayForComboBox(List<String[]> list) {
        String[] s = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            s[i] = list.get(i)[1];
        }
        return s;
    }
}
