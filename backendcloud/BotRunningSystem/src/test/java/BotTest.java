import com.celest.botrunningsystem.runningbot.Bot;

public class BotTest {
    public static void main(String[] args) {
        Bot bot = new Bot();
        int move = bot.move("11111111111111110001001000011000010000000110000000010111100000000000011000000011100110000000000001100111000000011000000000000111101000000001100000001000011000010010001111111111111111#1#12#()#11#1#()");
        System.out.println(move);
    }
}
