package org.example.taskservice.mapper;

import org.example.taskservice.dto.CardDto;
import org.example.taskservice.dto.CommentDto;
import org.example.taskservice.form.CommentCreateForm;
import org.example.taskservice.model.Card;
import org.example.taskservice.model.Comment;

public class CommentMapper {
    public static Comment map(CommentCreateForm form, Card card){
        var comment = new Comment();
        map(form, comment, card);
        return comment;
    }

    public static CommentDto map(Comment comment){
        var commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setUserId(comment.getUserId());
        commentDto.setCard(CardMapper.map(comment.getCard()));
        commentDto.setIsModified(comment.getIsModified());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setUpdatedAt(comment.getUpdatedAt());

        return commentDto;
    }

    public static void map(CommentCreateForm form, Comment comment, Card card){
        comment.setUserId(form.getUserId());
        comment.setContent(form.getContent());
        comment.setCard(card);
    }
}
