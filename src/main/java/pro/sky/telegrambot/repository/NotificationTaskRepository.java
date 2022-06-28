package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    boolean existsByChatIdEqualsAndMessageEqualsIgnoreCaseAndDatetimeEqualsAndIsSentFalse(
            @NonNull long chatId, @NonNull String message, @NonNull LocalDateTime datetime
    );

    List<NotificationTask> findByDatetimeLessThanEqualAndIsSentFalse(@NonNull LocalDateTime datetime);

}
