package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private List<Message> messages = new ArrayList<>();

    @GetMapping
    public List<Message> getAllMessages() {
        return messages;
    }

    @GetMapping("/{id}")
    public void updateMessage(Message message) {
        String sql = "UPDATE Message SET text = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, message.getText());
            statement.setInt(2, message.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping
    public void createMessage(@RequestBody Message message) {
        messages.add(message);
    }

    @PutMapping("/{id}")
    public void updatePerson(Person person) {
        String sql = "UPDATE Person SET name = ?, age = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setInt(3, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable int id) {
        Message message = getMessageById(id);
        if (message != null) {
            messages.remove(message);
        }
    }
}

class Message {
    private int id;
    private String title;
    private String text;
    private LocalDateTime time;

    public Message(int id, String title, String text, LocalDateTime time) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}