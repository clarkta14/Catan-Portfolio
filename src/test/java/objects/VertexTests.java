package objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VertexTests {

	@Test
	public void testConstructVertex() {
		Vertex vertex = new Vertex();
		assertEquals(2, vertex.getVertex(3,4));
	}
	
	
	@Test
	public void testGetAccurateVertex() {
		Vertex vertex = new Vertex();
		assertEquals(2, vertex.getVertex(3, 4));
	}
	
	@Test
	public void testGetErrorBecauseOfIncorrectArguments() {
		Vertex vertex = new Vertex();
		assertEquals(-1, vertex.getVertex(19, 0));
		assertEquals(-1, vertex.getVertex(1, 6));
	}
}
