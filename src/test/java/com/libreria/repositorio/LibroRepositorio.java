package com.libreria.repositorio;

import com.libreria.entidades.Libro;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{

    public Optional<Libro> findAllById(String id);
    
}
