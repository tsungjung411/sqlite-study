import java.lang.reflect.Field;
import java.util.Vector;


public class ClassLoaderDumper {

	public static void dump() {
		// class sun.misc.Launcher$AppClassLoader
		//  + class java.net.URLClassLoader
		//     + class java.security.SecureClassLoader
		//        + class java.lang.ClassLoader
		dump(ClassLoader.getSystemClassLoader());
	}
	
	public static void dump(ClassLoader classLoader) {
		Class<?> loaderClass = classLoader.getClass();

		while (loaderClass != java.lang.ClassLoader.class) {
			loaderClass = loaderClass.getSuperclass();
		}
		try {
			Field field = loaderClass.getDeclaredField("classes");
			field.setAccessible(true);

			Vector<?> classes = (Vector<?>) field.get(classLoader);
			for (Object aClass : classes) {
				System.out.println("   Loaded " + aClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

