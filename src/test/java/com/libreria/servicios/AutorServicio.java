package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorio.AutorRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional(rollbackFor = Exception.class)
    public Autor guardar(String nombre) throws ErrorServicio{
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del Autor no puede ser nulo.");
        }
        
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        autor.setAlta(true);
        
        return autorRepositorio.save(autor);
    }
    
    public Autor editar(String id,String nombre) throws ErrorServicio{
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del Autor no puede ser nulo.");
        }
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            
            return autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("No se encontro el autor     solicitado");
        }  
        
    }
    
    public void eliminar(String id) throws ErrorServicio{
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);
            
            autorRepositorio.save(autor);
        }else{
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }  
    }
    
    @Transactional(readOnly = true)
    public Autor buscarPorId(String id) throws ErrorServicio{
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            return respuesta.get();
        }else{
            throw new ErrorServicio("No existe el autor");
        }
        
    }
}
