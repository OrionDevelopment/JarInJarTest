import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            System.out.println("Only 1 argument accepted. The path to a zip.");
        }

        final String pathCandidate = args[0];
        final Path path = Path.of(pathCandidate);
        listRecursively(path, "");
    }

    private static void listRecursively(final Path path, final String indent) throws IOException
    {
        if (!(path.getFileName().toString().endsWith(".zip") || path.getFileName().toString().endsWith(".jar")))
            return;

        final FileSystem zipFileSystem = FileSystems.newFileSystem(path);
        final Path rootDirectory = zipFileSystem.getPath("/");

        final List<Path> innerPaths = Files.list(rootDirectory).collect(Collectors.toList());
        for (Path innerPath : innerPaths)
        {
            System.out.println(indent + " > " + path.getFileName().toString());
            if (path.getFileName().toString().endsWith(".zip") || path.getFileName().toString().endsWith(".jar"))
            {
                listRecursively(innerPath, indent + "   ");
            }
        }
    }
}
