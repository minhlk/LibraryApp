package com.example.minhlk.myapplication;

import java.util.List;

public class Events {
    // Event used to send message from service to activity.
    public static class AuthorMessage {
        private List<Author> message;
        public AuthorMessage (List<Author> message) {
            this.message = message;
        }
        public List<Author> getMessage() {
            return message;
        }
    }
    public static class GenreMessage {
        private List<Genre> message;
        public GenreMessage (List<Genre> message) {
            this.message = message;
        }
        public List<Genre> getMessage() {
            return message;
        }
    }

}
