package ru.otus.homework20210428.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Комментарий к книге
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "book_comments")
public class BookComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String author;
    @Column(length = 1024)
    private String text;
}
