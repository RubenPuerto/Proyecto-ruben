/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.proyecto.conexion;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ruben P
 */
public class Conexion {
    private Connection con;
    private PreparedStatement consulta;
    public ResultSet datos;
    private String server,user,bd,pass;
   
    
    public Conexion(){
    
     this.server="localhost";
        this.bd="proyecto";
        this.user="root";
        this.pass="root";
        
    
    }
    
    public void con() throws SQLException {
        
        try {
             Class.forName("com.mysql.jdbc.Driver");
        this.con=DriverManager.getConnection("jdbc:mysql://"+this.server+"/"+this.bd,this.user,this.pass);
        
        
        
           
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       

}
    public void desconectar() throws SQLException{
         this.con.close();
    }
    
   
    public ResultSet getCursos() throws SQLException 
    {
    this.con();

    this.consulta=(PreparedStatement) this.con.prepareStatement("select * from programas");
    this.datos=this.consulta.executeQuery();
    return this.datos;
    }
    
    public ResultSet llenarTema(String ID) throws SQLException 
    {
    this.con();

    this.consulta=(PreparedStatement) this.con.prepareStatement("select * from Temas where IdPadre="+ID+"");
    this.datos=this.consulta.executeQuery();
    return this.datos;
    }
    
    public ResultSet llenarDescripcion(String IdTemas) throws SQLException 
    {
    this.con();

    this.consulta=(PreparedStatement) this.con.prepareStatement("select IdTemas, Costo,Titulo,Descripcion,ImgTema from temas where IdTemas="+IdTemas+"");
    this.datos=this.consulta.executeQuery();
    return this.datos;
    }
    
    
    public ResultSet ContadorInscritos(String IdTemas) throws SQLException{
    this.con();

    this.consulta=(PreparedStatement) this.con.prepareStatement("SELECT COUNT(*) AS personas FROM newpeople where IdTema="+IdTemas+";");
    this.datos=this.consulta.executeQuery();
    return this.datos;
    }
    public ResultSet GuardarPersonas(String IdCurso,String IdTemas,String Correo, String Nombre, String Celular) throws SQLException{
    this.con();
    
    this.consulta=(PreparedStatement) this.con.prepareStatement("insert into newpeople(IdCurso,IdTema,Correo,Celular,Nombre)values("+IdCurso+","+IdTemas+",'"+Correo+"','"+Celular+"','"+Nombre+"');");
    this.consulta.executeUpdate();
    return this.datos;
    }
    public ResultSet llenarcurso(String IdCurso) throws SQLException 
    {
    this.con();

    this.consulta=(PreparedStatement) this.con.prepareStatement("select * from programas where IdCurso="+IdCurso+"");
    this.datos=this.consulta.executeQuery();
    return this.datos;
    }
    public ResultSet GuardarCursos(String nombre,String apellido) throws SQLException{
    this.con();

    this.consulta=(PreparedStatement) this.con.prepareStatement("INSERT INTO `proyecto`.`programas` (`TituloCurso`, `DescripcionCurso`) VALUES ('"+nombre+"', '"+apellido+"');");
    this.consulta.executeUpdate();
    return this.datos;
    }
    
     public ResultSet PedirCursos(String nombre,String Correo, String Celular,String Curso,String Categoria) throws SQLException{
    this.con();

    this.consulta=(PreparedStatement) this.con.prepareStatement("INSERT INTO `proyecto`.`cursonuevos` (`Nombre`, `Correo`,`Celular`,`Curso`,`Categoria`) VALUES ('"+nombre+"', '"+Correo+"','"+Celular+"','"+Curso+"','"+Categoria+"');");
    this.consulta.executeUpdate();
    return this.datos;
    }
     public byte[] obtenImagenProducto(int idProducto)  {
        ResultSet rs = null;
        byte[] buffer = null;
        try {
            this.con();
            this.consulta=(PreparedStatement) this.con.prepareStatement("select ImgCursosInicio from programas where IdCurso='"+idProducto+"';");
            rs = this.consulta.executeQuery();
            System.out.println(rs);
            while (rs.next()){
                Blob bin = rs.getBlob("ImgCursosInicio");
                if (bin != null) {
                    InputStream inStream = bin.getBinaryStream();
                    int size = (int) bin.length();
                    buffer = new byte[size];
                    int length = -1;
                    int k = 0;
                    try {
                        inStream.read(buffer, 0, size);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            return null;
        } 
        return buffer;
    }
    public byte[] obtenImagenDetalle(int idProducto)  {
        ResultSet rs = null;
        byte[] buffer = null;
        try {
            this.con();
            this.consulta=(PreparedStatement) this.con.prepareStatement("select ImagenCurso from programas where IdCurso='"+idProducto+"';");
            rs = this.consulta.executeQuery();
            System.out.println(rs);
            while (rs.next()){
                Blob bin = rs.getBlob("ImagenCurso");
                if (bin != null) {
                    InputStream inStream = bin.getBinaryStream();
                    int size = (int) bin.length();
                    buffer = new byte[size];
                    int length = -1;
                    int k = 0;
                    try {
                        inStream.read(buffer, 0, size);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            return null;
        } 
        return buffer;
    }
    public ResultSet GetUser(String user, String Password) throws SQLException 
    {
    this.con();
    this.consulta=(PreparedStatement) this.con.prepareStatement("select * from admin where User='"+user+"' and Password='"+Password+"'");
    this.datos=this.consulta.executeQuery();
    return this.datos;
    }
    public ResultSet EliminarItem(String Id) throws SQLException{
    this.con();

    this.consulta=(PreparedStatement) this.con.prepareStatement("DELETE FROM `proyecto`.`programas` WHERE `IdCurso`='"+Id+"';");
    this.consulta.executeUpdate();
    return this.datos;
    }
    public ResultSet getBanner() throws SQLException 
    {
    this.con();

    this.consulta=(PreparedStatement) this.con.prepareStatement("select * from banner");
    this.datos=this.consulta.executeQuery();
    return this.datos;
    }
    public byte[] obtenImagenBanner(String IdBanner)  {
        ResultSet rs = null;
        byte[] buffer = null;
        try {
            this.con();
            this.consulta=(PreparedStatement) this.con.prepareStatement("select Img from Banner where Id="+IdBanner+";");
            rs = this.consulta.executeQuery();
            System.out.println(rs);
            while (rs.next()){
                Blob bin = rs.getBlob("Img");
                if (bin != null) {
                    InputStream inStream = bin.getBinaryStream();
                    int size = (int) bin.length();
                    buffer = new byte[size];
                    int length = -1;
                    int k = 0;
                    try {
                        inStream.read(buffer, 0, size);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            return null;
        } 
        return buffer;
    }
    public byte[] obtenImagenTema(int idProducto)  {
        ResultSet rs = null;
        byte[] buffer = null;
        try {
            this.con();
            this.consulta=(PreparedStatement) this.con.prepareStatement("select  ImgTema from temas where IdTemas="+idProducto+";");
            rs = this.consulta.executeQuery();
            while (rs.next()){
                Blob bin = rs.getBlob("ImgTema");
                if (bin != null) {
                    InputStream inStream = bin.getBinaryStream();
                    int size = (int) bin.length();
                    buffer = new byte[size];
                    int length = -1;
                    int k = 0;
                    try {
                        inStream.read(buffer, 0, size);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            return null;
        } 
        return buffer;
    }
    
    
}
