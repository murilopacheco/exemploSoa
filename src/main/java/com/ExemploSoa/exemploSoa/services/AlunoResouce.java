package com.ExemploSoa.exemploSoa.services;

import com.ExemploSoa.exemploSoa.repository.AlunoRepository;
import model.Aluno;
import model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/alunos") //caminho geral do serviço
public class AlunoResouce {

    @Autowired
    private AlunoRepository alunoRepository;


    @RequestMapping(value = "/all", method = RequestMethod.GET) //caminho específico para o método
    public @ResponseBody Iterable<Aluno> listaTodosCursos() {
        // metodo retorna JSON ou XML de todos os cursos.
        return alunoRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Aluno buscar(@PathVariable("id") Long id) throws ChangeSetPersister.NotFoundException {

        return alunoRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletar(@RequestBody Aluno aluno ,@PathVariable("id") Long id) {


        if (aluno == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            alunoRepository.delete(aluno);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Object> criarCurso(@RequestBody Aluno aluno) {
        alunoRepository.save(aluno);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(aluno.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping(value = "/{id}")
    public Aluno atualizarCurso(@RequestBody Aluno aluno, @PathVariable Long id) {

        return alunoRepository.save(aluno);
    }

}