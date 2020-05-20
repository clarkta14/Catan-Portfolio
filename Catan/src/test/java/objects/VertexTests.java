package objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VertexTests {

	@SuppressWarnings("static-access")
	@Test
	public void testConstructVertex() {
		Vertex vertex = new Vertex();
		assertEquals(2, vertex.getVertex(3,4));
	}
	
	
	@Test
	public void testGetAccurateVertex() {
		assertEquals(2, Vertex.getVertex(3, 4));
	}
	
	@Test
	public void testGetErrorBecauseOfIncorrectArguments() {
		assertEquals(-1, Vertex.getVertex(19, 0));
		assertEquals(-1, Vertex.getVertex(1, 6));
	}
}
