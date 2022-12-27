package edu.veceloe.notes.controller;

class NoteNotFoundException extends RuntimeException {

    NoteNotFoundException(Long id) {
        super("Could not find note " + id);
    }
}