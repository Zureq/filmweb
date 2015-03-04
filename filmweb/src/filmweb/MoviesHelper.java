package filmweb;

import info.talacha.filmweb.api.FilmwebApi;
import info.talacha.filmweb.models.Film;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoviesHelper {

	private static FilmwebApi fa = new FilmwebApi();

	public static List<Film> getMoviesFromDirectory(final File folder) {
		FilenameFilter filter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".avi");
			}
		};
		File[] files = folder.listFiles(filter);
		List<Film> moviesList = new ArrayList<Film>();
		for (File file : files) {
			Film movie = getMovieFromFilmWeb(file.getName());
			if (null == movie) {
				System.out.println("movie not found: " + file.getName());
			} else {
				System.out.println("found movie: "
						+ (null != movie.getTitle() ? movie.getTitle() : movie
								.getPolishTitle()));
				moviesList.add(movie);
			}
		}

		return moviesList;
	}

	private static Film getMovieFromFilmWeb(String fileName) {
		FilmWebGetMovieInput movieInput = getInputFromFileName(fileName);
		ArrayList<Film> moviesList = fa.getFilmList(movieInput.getName(),
				movieInput.getYear());
		if (moviesList.size() > 0) {
			return moviesList.get(0);
		}
		return null;
	}

	private static FilmWebGetMovieInput getInputFromFileName(String fileName) {
		FilmWebGetMovieInput toRet = new FilmWebGetMovieInput();
		fileName = fileName.replace(".avi", "");
		fileName = fileName.replaceAll("\\.|_", " ");
		System.out.println("fileName for regex: " + fileName);
		Pattern p = Pattern.compile("(.+) ([0-9]{4})");
		Matcher m = p.matcher(fileName);

		if (m.find()) {
			String movieTitle = m.group(1);
			String movieYear = m.group(2);
			toRet.setName(movieTitle);
			toRet.setYear(Integer.parseInt(movieYear));
		}
		return toRet;
	}
}
