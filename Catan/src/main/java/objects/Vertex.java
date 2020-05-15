package objects;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Vertex {
	private static final String BUNDLE_NAME = "objects.vertices"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public static int getVertex(int tile, int corner) {
		try {
			return Integer.parseInt(RESOURCE_BUNDLE.getString(MessageFormat.format("{0}.{1}.{2}.{3}", "Tile", tile, "Corner", corner)));
		} catch (MissingResourceException e) {
			return -1;
		}
	}
}
