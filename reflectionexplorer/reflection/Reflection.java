package reflectionexplorer.reflection;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class Reflection {

	public static Class<?> loadClass(final File f, final String className) {
		try {
			URLClassLoader classLoader = URLClassLoader
					.newInstance(new URL[] { f.toURI().toURL() });
			return classLoader.loadClass(className);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
	
}
