package com.ExemploSoa.exemploSoa.services;

import java.net.URI;

import com.ExemploSoa.exemploSoa.repository.CursoRepository;
import model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/cursos") //caminho geral do serviço
public class CursoResource {

    @Autowired
    private CursoRepository cursoRepository;


    @RequestMapping(value = "/all", method = RequestMethod.GET) //caminho específico para o método
    public @ResponseBody Iterable<Curso> listaTodosCursos() {
        // metodo retorna JSON ou XML de todos os cursos.
        return cursoRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Curso buscar(@PathVariable("id") Long id) throws ChangeSetPersister.NotFoundException {

        return cursoRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletar(@RequestBody Curso curso ,@PathVariable("id") Long id) {


        if (curso == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            cursoRepository.delete(curso);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Object> criarCurso(@RequestBody Curso curso) {
        cursoRepository.save(curso);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping(value = "/{id}")
    public Curso atualizarCurso(@RequestBody Curso curso, @PathVariable Long id) {

        return cursoRepository.save(curso);
    }

}