package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.errores.ErrorServicio;
import com.libreria.repositorio.LibroRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    @Autowired //se trata de la anotación que permite inyectar unas dependencias con otras dentro de Spring 
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    
    @Transactional(rollbackFor = Exception.class) //modifica la base de datos
    //si quiero solamente leer la base de datos sin modificar utilizo el @Transactional(readOnly=true)
    public Libro guardar(String titulo,long isbn,Integer anio,Integer ejemplares,Integer ejemplaresPrestados,Integer ejemplaresRestantes,String idAutor,String idEditorial) throws ErrorServicio{
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no puede ser nulo");
        }
        if (ejemplares == null) {
            throw new ErrorServicio("Los ejemplares no puede ser nulos");
        }
        
        Libro libro = new Libro();
        
        //sirve para setear el autor en el libro(hecho en AutorServicio)
        Autor autor = autorServicio.buscarPorId(idAutor);
        //sirve para setear la editorial en el libro(hecho en EditorialServicio)
        Editorial editorial = editorialServicio.buscarPorId(idEditorial);
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAlta(true);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(ejemplaresRestantes);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        return libroRepositorio.save(libro);
        
    }
    
    public Libro editar(String id,String titulo,long isbn,Integer anio,Integer ejemplares,Integer ejemplaresPrestados,Integer ejemplaresRestantes) throws ErrorServicio{
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El nombre no puede ser nulo");
        }
        if (anio == null) {
            throw new ErrorServicio("El año no puede ser nulo");
        }
        if (ejemplares == null) {
            throw new ErrorServicio("Los ejemplares no puede ser nulos");
        }
        Optional<Libro> respuesta = libroRepositorio.findAllById(id);
        if (respuesta.isPresent()) { //.isPresent actua como un respuesta != null;
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
            
            return libroRepositorio.save(libro);
            
        }else{
            throw new ErrorServicio("No se encontro el libro solicitado");
        }
        
    }
    
    public void eliminar(String id) throws ErrorServicio{
        if (id == null || id.isEmpty()) {
            throw new ErrorServicio("El id no puede ser nulo");
        }
        Optional<Libro> respuesta = libroRepositorio.findAllById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false); //para borrar el elemento solamente pongo false el alta
            
            libroRepositorio.save(libro);
        }else{
            throw new ErrorServicio("No se encontro el libro solicitado");
        }
    }
}
