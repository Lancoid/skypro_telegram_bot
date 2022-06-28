package pro.sky.telegrambot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = NotificationTask.TABLE_NAME)
@Setter
@Getter
@NoArgsConstructor
public class NotificationTask {

    public static final String TABLE_NAME = "notification_task";

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "chat_id")
    private long chatId;
    private String message;
    private LocalDateTime datetime;
    @Column(name = "is_sent")
    private boolean isSent = false;

    public NotificationTask(long chatId, String message, LocalDateTime datetime) {
        this.chatId = chatId;
        this.message = message;
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", message='" + message + '\'' +
                ", datetime=" + datetime +
                ", isSent=" + isSent +
                '}';
    }
}
