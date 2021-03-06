package net.marvk.chess.core.bitboards;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class EnginePerft {
    private final Path path;
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(8);

    public EnginePerft(final Path path) {
        if (!Files.exists(path)) {
            throw new IllegalStateException("Path does not exist: " + path);
        }

        this.path = path;
    }

    public Set<String> perft(final String fen) throws IOException, ExecutionException, InterruptedException {
        final Process start = new ProcessBuilder(path.toString()).start();

        final BufferedReader br = new BufferedReader(new InputStreamReader(start.getInputStream()));
        final Future<List<String>> future = EXECUTOR_SERVICE.submit(() -> br.lines().collect(Collectors.toList()));

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(start.getOutputStream()))) {
            writer.write("position fen " + fen + "\n");
            writer.flush();
            writer.write("go perft 1\n");
            writer.flush();
            writer.write("quit\n");
            writer.flush();
        }

        final List<String> strings = future.get();

        br.close();

        return strings.stream()
                      .filter(s -> s.matches("^[^ ]*: \\d+$"))
                      .map(s -> s.replaceAll(": \\d+$", "")).collect(Collectors.toSet());
    }
}
