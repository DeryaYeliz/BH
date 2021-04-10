package destek;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;


public class VectorWriter {
/* Bir star listesi alir ve starlari (test vektorlerini) dosyaya yazar.*/
	
	public static boolean writeVector2File(List <List<Integer>> inputVectors, Path path) throws IOException {
		// TODO Auto-generated method stub
		
		Files.deleteIfExists(path);
		Files.write(path, "".getBytes(), StandardOpenOption.CREATE_NEW);		
		for (int i = 0; i < inputVectors.size(); i++) {
			Files.write(path, inputVectors.get(i).toString().getBytes(), StandardOpenOption.APPEND );
			Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);

		}
		return true;
	}

}
