/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bus;

import java.util.ArrayList;

/**
 *
 * @author mansh
 */
public interface BUSInterface<T> {
    public ArrayList<T> getAll();
    
    public void refreshData();
    
    public T getById(T t);
    
    public int add(T t);
    
    public int update(T t);
    
    public int delete(T t);
    
    public T search();
    
    public int getLastElementId();
}
