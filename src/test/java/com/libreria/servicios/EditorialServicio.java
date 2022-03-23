package com.libreria.servicios;

import com.libreria.entidades.Editorial;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorio.EditorialRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {  
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional(rollbackFor = Exception.class)
    public Editorial guardar(String nombre) throws ErrorServicio{
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la editorial no puede ser nulo");
        }
        
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        
        return editorialRepositorio.save(editorial);
    }
    
    public Editorial editar(String nombre,String id) throws ErrorServicio{
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la editorial no puede ser nulo");
        }
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            
            return editorialRepositorio.save(editorial);
            
        }else{
            throw new ErrorServicio("No se encontro la editorial solicitada");
        }
    }
    
    public void eliminar(String id) throws ErrorServicio{
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            
            editorialRepositorio.save(editorial);
        }else{
                throw new ErrorServicio("No se encontro la editorial solicitada");
        }
    }
    
    public Editorial buscarPorId(String id) throws ErrorServicio{
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            return respuesta.get();
        }else{
            throw new ErrorServicio("No existe la editorial");
        }
        
    }
}
