package filmweb;

import info.talacha.filmweb.models.Film;

import java.io.File;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		File folder = new File("C:/filmy");
		List<Film> movies = MoviesHelper.getMoviesFromDirectory(folder);
		for(Film movie : movies) {
			ImageDrawer.createJPG(movie);
		}
	}

}
