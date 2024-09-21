/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import dao.SachDAO;
import dto.SachDTO;
import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public class Test {

    public static void main(String[] args) {
        SachDAO sd = new SachDAO();
        ArrayList<SachDTO> listS = new ArrayList<SachDTO>();
        listS = sd.selectAll();
        for (SachDTO s : listS) {
            System.out.println(s);
        }
    }
}
