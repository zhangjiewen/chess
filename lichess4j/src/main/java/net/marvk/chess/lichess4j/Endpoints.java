package net.marvk.chess.lichess4j;

import net.marvk.chess.core.UciMove;
import net.marvk.chess.lichess4j.model.Room;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class Endpoints {
    private static final String URL = "https://lichess.org";

    private Endpoints() {
        throw new AssertionError("No instances of utility class " + Endpoints.class);
    }

    public static String url() {
        return URL;
    }

    public static String eventStream() {
        return URL + "/api/stream/event";
    }

    public static String acceptChallenge(final String gameId) {
        return URL + "/api/challenge/" + gameId + "/accept";
    }

    public static String declineChallenge(final String gameId) {
        return URL + "/api/challenge/" + gameId + "/decline";
    }

    public static String makeMove(final String gameId, final UciMove move) {
        return URL + "/api/bot/game/" + gameId + "/move/" + move;
    }

    public static String gameStream(final String gameId) {
        return URL + "/api/bot/game/stream/" + gameId;
    }

    public static String writeInChat(final String gameId, final Room room, final String text) {
        return URL + "/api/bot/game/" + gameId + "/chat?room=" + room.getRepresentation() + "&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}
