package org.example.taskservice.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateForm {
    private String content;
    private String userId;
}
