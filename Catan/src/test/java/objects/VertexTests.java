package objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VertexTests {

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
