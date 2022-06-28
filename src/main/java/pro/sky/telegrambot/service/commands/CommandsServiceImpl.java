package pro.sky.telegrambot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

@Service
public class CommandsServiceImpl implements CommandsService {

    public SendMessage start(Update update) {
        return new SendMessage(
                update.message().chat().id(),
                getTextMessage(update.message().from().username())
        );
    }

    private String getTextMessage(String username) {
        return "Привет, " + username + " !\n" +
                "Я могу оповестить тебя как ты попросишь!\n" +
                "Если ты напишешь `01.01.2025 00:00 С новым годом!`, то ровно в полночь 1го января 2025 года я поздравлю тебя с Новым годом!\n" +
                "Я могу оповестить тебя о всем что попросишь!\n" +
                "Единственное правило - я не лезу в прошлое, был прецедент - чуть не стерли с жёсткого диска";
    }

}
