package de.htwg.stratego.model;

import java.awt.Color;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import de.htwg.stratego.model.impl.Cell;
import de.htwg.stratego.model.impl.Field;
import de.htwg.stratego.model.impl.Player;
import de.htwg.stratego.model.impl.character.Sergeant;
import junit.framework.TestCase;

public class FieldTest extends TestCase {
    private Field field;

    private ICharacter character1;
    private ICharacter character2;
    private Player playerOne;
    private Player playerTwo;

    @BeforeClass
	public void setUp() {
		field = new Field(3, 2);
		playerOne = new Player("PlayerOne", "#");
		playerTwo = new Player("PlayerTwo", "!");
		character1 = new Sergeant(playerOne);
		character2 = new Sergeant(playerTwo);

		field.getCell(0, 0).setCharacter(character1);
		field.getCell(1, 1).setCharacter(character2);
	}

    @Test
    public void testGetAllCellsFrom_fieldHasTwoDifferentCharWithDiffPlayersAndNullCases_showOnlyPlayerOneCells() throws Exception {
        int firstCharacterInList = 0;
        field.getCell(2, 0).setCharacter(new Sergeant(null));
        List<ICell> allCellsFromPlayerOne = field.getAllCellsFrom(playerOne);
        assertEquals("[# 4]", allCellsFromPlayerOne.toString());
        // check if playerRef is the same
        assertEquals(playerOne, allCellsFromPlayerOne.get(firstCharacterInList).getCharacter().getPlayer());
    }

    @Test
    public void testGetCells() throws Exception {
        List<ICell> cells = field.getCells();
        System.out.println(cells);
        assertEquals("[# 4,    ,    ,    , ! 4,    ]", cells.toString());
    }

	@Test
	public void testGetWidth() {
		assertEquals(3 , field.getWidth() );
	}
	
	@Test
	public void testGetHeight() {
		assertEquals(2,field.getHeight());
	}
	
	@Test
	public void testGetCell() {
		assertEquals(field.getCell(1, 1), new Cell(1,1));	
	}
	
	@Test
	public void testCells() {
		for (int y = 0; y < field.getHeight(); y++) {
			for (int x = 0; x < field.getWidth(); x++) {
				Cell cell = field.getCell(x, y);
				assertNotNull(cell);
				assertEquals(x, cell.getX());
				assertEquals(y, cell.getY());
			}
		}
	}
	
	@Test
	public void testToString() {
		StringBuilder sb = new StringBuilder();
		sb.append("    0   1   2  \n");
		sb.append("  +---+---+---+\n");
		sb.append("0 |# 4|   |   |\n");
		sb.append("  +---+---+---+\n");
		sb.append("1 |   |! 4|   |\n");
		sb.append("  +---+---+---+\n");
		assertEquals(sb.toString(), field.toString());
	}
}
