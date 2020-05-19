package objects;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class LongestRoadTests {
	@Test
	public void testLongestRoadForPlayerIs3() {
		LongestRoad lr = new LongestRoad(3);
		
		ArrayList<Integer> corners = new ArrayList<>(Arrays.asList(2, 3));
		lr.addRoadForPlayer(0, 0, corners);
		
		corners = new ArrayList<>(Arrays.asList(1, 2));
		lr.addRoadForPlayer(0, 1, corners);
		
		corners = new ArrayList<>(Arrays.asList(0, 1));
		lr.addRoadForPlayer(0, 5, corners);
		
		assertEquals(3, lr.getLongestRoadForPlayer(0));
	}
	
	@Test
	public void testLongestRoadForPlayerIs5() {
		LongestRoad lr = new LongestRoad(3);
		
		ArrayList<Integer> corners = new ArrayList<>(Arrays.asList(2, 3));
		lr.addRoadForPlayer(0, 0, corners);
		
		corners = new ArrayList<>(Arrays.asList(1, 2));
		lr.addRoadForPlayer(0, 1, corners);
		
		corners = new ArrayList<>(Arrays.asList(0, 1));
		lr.addRoadForPlayer(0, 5, corners);
		
		corners = new ArrayList<>(Arrays.asList(5, 0));
		lr.addRoadForPlayer(0, 9, corners);
		
		corners = new ArrayList<>(Arrays.asList(4, 3));
		lr.addRoadForPlayer(0, 3, corners);
		
		assertEquals(5, lr.getLongestRoadForPlayer(0));
	}
	
	@Test
	public void testLongestRoadForPlayerIs3_2Branches() {
		LongestRoad lr = new LongestRoad(3);
		
		ArrayList<Integer> corners = new ArrayList<>(Arrays.asList(2, 3));
		lr.addRoadForPlayer(0, 0, corners);
		
		corners = new ArrayList<>(Arrays.asList(1, 2));
		lr.addRoadForPlayer(0, 1, corners);
		
		corners = new ArrayList<>(Arrays.asList(0, 1));
		lr.addRoadForPlayer(0, 5, corners);
		
		corners = new ArrayList<>(Arrays.asList(1, 0));
		lr.addRoadForPlayer(0, 1, corners);
		
		assertEquals(3, lr.getLongestRoadForPlayer(0));
	}
	
	@Test
	public void testLongestRoadForPlayerIs3_2Branches_Take2() {
		LongestRoad lr = new LongestRoad(3);
		
		ArrayList<Integer> corners = new ArrayList<>(Arrays.asList(2, 3));
		lr.addRoadForPlayer(0, 0, corners);
		
		corners = new ArrayList<>(Arrays.asList(1, 2));
		lr.addRoadForPlayer(0, 1, corners);
		
		corners = new ArrayList<>(Arrays.asList(0, 1));
		lr.addRoadForPlayer(0, 5, corners);
		
		corners = new ArrayList<>(Arrays.asList(2, 3));
		lr.addRoadForPlayer(0, 1, corners);
		
		assertEquals(3, lr.getLongestRoadForPlayer(0));
	}
	
	@Test
	public void testLongestRoadForPlayerIs7_MagnifyingGlassPath() {
		LongestRoad lr = new LongestRoad(3);
		
		ArrayList<Integer> corners = new ArrayList<>(Arrays.asList(1, 2));
		lr.addRoadForPlayer(0, 0, corners);
		
		corners = new ArrayList<>(Arrays.asList(0, 5));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(5, 4));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(4, 3));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(3, 2));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(2, 1));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(1, 0));
		lr.addRoadForPlayer(0, 4, corners);
		
		assertEquals(7, lr.getLongestRoadForPlayer(0));
	}
	
	@Test
	public void testLongestRoadForPlayerIs7_MagnifyingGlassPath_take2() {
		LongestRoad lr = new LongestRoad(3);
		
		ArrayList<Integer> corners = new ArrayList<>(Arrays.asList(0, 5));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(5, 4));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(4, 3));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(3, 2));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(2, 1));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(1, 0));
		lr.addRoadForPlayer(0, 4, corners);
		
		corners = new ArrayList<>(Arrays.asList(1, 2));
		lr.addRoadForPlayer(0, 0, corners);
		
		assertEquals(7, lr.getLongestRoadForPlayer(0));
	}
	
	@Test
	public void testLongestRoadForPlayerIs5_Merge() {
		LongestRoad lr = new LongestRoad(3);
		
		ArrayList<Integer> corners = new ArrayList<>(Arrays.asList(3, 4));
		lr.addRoadForPlayer(0, 9, corners);
		
		corners = new ArrayList<>(Arrays.asList(4, 5));
		lr.addRoadForPlayer(0, 9, corners);
		
		corners = new ArrayList<>(Arrays.asList(4, 3));
		lr.addRoadForPlayer(0, 0, corners);
		
		corners = new ArrayList<>(Arrays.asList(1, 2));
		lr.addRoadForPlayer(0, 1, corners);
		
		corners = new ArrayList<>(Arrays.asList(3, 4));
		lr.addRoadForPlayer(0, 4, corners);
		
		assertEquals(5, lr.getLongestRoadForPlayer(0));
	}
	
	@Test
	public void testLongestRoadForPlayerIs5_Merge2() {
		LongestRoad lr = new LongestRoad(3);
		
		ArrayList<Integer> corners = new ArrayList<>(Arrays.asList(5, 4));
		lr.addRoadForPlayer(0, 9, corners);
		
		corners = new ArrayList<>(Arrays.asList(4, 3));
		lr.addRoadForPlayer(0, 9, corners);
		
		
		corners = new ArrayList<>(Arrays.asList(2, 1));
		lr.addRoadForPlayer(0, 1, corners);
		
		corners = new ArrayList<>(Arrays.asList(3, 4));
		lr.addRoadForPlayer(0, 0, corners);
		
		
		corners = new ArrayList<>(Arrays.asList(3, 4));
		lr.addRoadForPlayer(0, 4, corners);
		
		assertEquals(5, lr.getLongestRoadForPlayer(0));
	}
	
	
//	@Test
//	public void testLongestRoadForPlayer() {
//		LongestRoad lr = new LongestRoad(3);
//		
//		ArrayList<Integer> corners = new ArrayList<>(Arrays.asList(0, 1));
//		lr.addRoadForPlayer(0, 1, corners);
//		
//		corners = new ArrayList<>(Arrays.asList(1, 2));
//		lr.addRoadForPlayer(0, 1, corners);
//		
//		corners = new ArrayList<>(Arrays.asList(4, 3));
//		lr.addRoadForPlayer(0, 4, corners);
//		
//		corners = new ArrayList<>(Arrays.asList(3, 4));
//		lr.addRoadForPlayer(0, 8, corners);
//		
//		corners = new ArrayList<>(Arrays.asList(3, 2));
//		lr.addRoadForPlayer(0, 4, corners);
//		
//		assertEquals(5, lr.getLongestRoadForPlayer(0));
//		
//		corners = new ArrayList<>(Arrays.asList(4, 5));
//		lr.addRoadForPlayer(0, 13, corners);
//		
//		assertEquals(6, lr.getLongestRoadForPlayer(0));
//		
//		corners = new ArrayList<>(Arrays.asList(0, 5));
//		lr.addRoadForPlayer(0, 1, corners);
//		
//		assertEquals(6, lr.getLongestRoadForPlayer(0));
//		
//		corners = new ArrayList<>(Arrays.asList(3, 4));
//		lr.addRoadForPlayer(1, 17, corners);
//		
//		corners = new ArrayList<>(Arrays.asList(4, 5));
//		lr.addRoadForPlayer(1, 17, corners);
//		
//		corners = new ArrayList<>(Arrays.asList(5, 0));
//		lr.addRoadForPlayer(1, 17, corners);
//		
//		corners = new ArrayList<>(Arrays.asList(4, 5));
//		lr.addRoadForPlayer(0, 16, corners);
//		
//		corners = new ArrayList<>(Arrays.asList(3, 4));
//		lr.addRoadForPlayer(1, 12, corners);
//		
//		corners = new ArrayList<>(Arrays.asList(4, 5));
//		lr.addRoadForPlayer(1, 12, corners);
//		
//		assertEquals(6, lr.getLongestRoadForPlayer(1));
//		
//		corners = new ArrayList<>(Arrays.asList(3, 4));
//		lr.addRoadForPlayer(0, 7, corners);
//		
//		assertEquals(7, lr.getLongestRoadForPlayer(1));
//	}
}
