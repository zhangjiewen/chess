package net.marvk.chess.lichess.serialization;

import com.google.gson.*;
import net.marvk.chess.lichess.model.ChatLine;
import net.marvk.chess.lichess.model.GameState;
import net.marvk.chess.lichess.model.GameStateFull;
import net.marvk.chess.lichess.model.GameStateResponse;

import java.lang.reflect.Type;

public class GameStateResponseDeserializer implements JsonDeserializer<GameStateResponse> {
    @Override
    public GameStateResponse deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context) throws JsonParseException {
        final JsonObject object = jsonElement.getAsJsonObject();

        final GameStateResponse.Type responseType = context.deserialize(object.get("type"), GameStateResponse.Type.class);

        if (responseType == GameStateResponse.Type.GAME_FULL) {
            final GameStateFull gameStateFull = context.deserialize(object, GameStateFull.class);

            return new GameStateResponse(responseType, gameStateFull, null, null);
        } else if (responseType == GameStateResponse.Type.GAME_STATE) {
            final GameState gameStateFull = context.deserialize(object, GameState.class);

            return new GameStateResponse(responseType, null, gameStateFull, null);
        } else if (responseType == GameStateResponse.Type.CHAT_LINE) {
            final ChatLine chatLine = context.deserialize(object, ChatLine.class);

            return new GameStateResponse(responseType, null, null, chatLine);
        } else {
            return null;
        }
    }
}
