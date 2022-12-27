package edu.veceloe.notes.controller;

import edu.veceloe.notes.model.Note;
import edu.veceloe.notes.model.User;
import edu.veceloe.notes.repository.NoteRepository;
import edu.veceloe.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class NoteController {
    @Autowired
    private final NoteRepository noteRepository;
    @Autowired
    private final UserRepository userRepository;
    private User user;

    NoteController(NoteRepository repository, UserRepository userRepository) {
        this.noteRepository = repository;
        this.userRepository = userRepository;
    }

    @GetMapping("/notes")
    List<Note> all(@CurrentSecurityContext(expression="authentication?.name") String name) {
        this.user = userRepository.findByEmail(name);
        return noteRepository.findByUserId(user.getId());
    }

    @PostMapping("/notes")
    Note newNote(@RequestBody Note newNote) {
        newNote.setUser(user);
        return noteRepository.save(newNote);
    }

    @GetMapping("/notes/{id}")
    Note one(@PathVariable Long id) {

        return noteRepository.findById(id)
               .orElseThrow(() -> new NoteNotFoundException(id));
    }

    @PutMapping("/notes/{id}")
    Note replaceNote(@RequestBody Note newNote, @PathVariable Long id) {

        return noteRepository.findById(id)
                .map(note -> {
                    note.setTitle(newNote.getTitle());
                    note.setContent(newNote.getContent());
                    return noteRepository.save(note);
                })
                .orElseGet(() -> {
                    newNote.setId(id);
                    return noteRepository.save(newNote);
                });
    }

    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable Long id) {
        noteRepository.deleteById(id);
        for (Note note : noteRepository.findByUserId(user.getId())) if (id == note.getId()) {
            noteRepository.findByUserId(user.getId()).remove(note.getId());
        }
    }
}