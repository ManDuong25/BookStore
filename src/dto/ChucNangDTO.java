/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author mansh
 */
public class ChucNangDTO {
    private String maChucNang;
    private String tenChucNang;
    
    public ChucNangDTO() {
        
    }

    public ChucNangDTO(String maChucNang, String tenChucNang) {
        this.maChucNang = maChucNang;
        this.tenChucNang = tenChucNang;
    }

    public String getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(String maChucNang) {
        this.maChucNang = maChucNang;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }

    @Override
    public String toString() {
        return "ChucNangDTO{" + "maChucNang=" + maChucNang + ", tenChucNang=" + tenChucNang + '}';
    }
    
    
}
