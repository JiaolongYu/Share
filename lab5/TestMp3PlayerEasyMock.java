package easymock;

import junit.framework.*;
import static org.easymock.EasyMock.*;
import java.util.ArrayList;

public class TestMp3PlayerEasyMock extends TestCase{
	  private Mp3Player mp3;
	  private Mp3Player mp3_control;
	  
//	  protected Mp3Player mp3;
	  protected ArrayList list = new ArrayList();

	  public void setUp() {
//	    mp3 = new MockMp3Player();
	    mp3_control = createMock(Mp3Player.class);
	    mp3 = mp3_control;
	    list = new ArrayList();
	    list.add("Bill Chase -- Open Up Wide");
	    list.add("Jethro Tull -- Locomotive Breath");
	    list.add("The Boomtown Rats -- Monday");
	    list.add("Carl Orff -- O Fortuna");
	  }

	  public void testPlay() {
	    
	    mp3.loadSongs(list);
	    expect(mp3.isPlaying()).andReturn(false);
	    replay( mp3_control );
	    assertFalse(mp3.isPlaying());
	    reset(mp3_control);
	  	mp3.play();
	  	expect(mp3.isPlaying()).andReturn(true);
	  	expect(mp3.currentPosition()).andReturn(0.2);
	  	replay( mp3_control );
	    assertTrue(mp3.isPlaying());
	    assertTrue(mp3.currentPosition() != 0.0);
	    reset(mp3_control);
	    mp3.pause();
	    expect(mp3.currentPosition()).andReturn(0.2);
	    replay( mp3_control );
	    assertTrue(mp3.currentPosition() != 0.0);
	    reset(mp3_control);
	    mp3.stop();
	    expect(mp3.currentPosition()).andReturn(0.0);
	    replay( mp3_control );
	    assertEquals(mp3.currentPosition(), 0.0, 0.1);
	  }

	  public void testPlayNoList() {

	    // Don't set the list up
		expect(mp3.isPlaying()).andReturn(false);
	  	replay( mp3_control );
	    assertFalse(mp3.isPlaying());
	    
	    reset(mp3_control);
	    mp3.play();
		expect(mp3.isPlaying()).andReturn(false);
		expect(mp3.currentPosition()).andReturn(0.0);
		replay( mp3_control );
	    assertFalse(mp3.isPlaying());
	    assertEquals(mp3.currentPosition(), 0.0, 0.1);
	   
	    reset(mp3_control);
	    mp3.pause();
	    expect(mp3.currentPosition()).andReturn(0.0);
	    expect(mp3.isPlaying()).andReturn(false);
		replay( mp3_control );
	    assertEquals(mp3.currentPosition(), 0.0, 0.1);
	    assertFalse(mp3.isPlaying());
	    
	    reset(mp3_control); 
	    mp3.stop();	
	    expect(mp3.currentPosition()).andReturn(0.0);
	    expect(mp3.isPlaying()).andReturn(false);
	    replay( mp3_control );
	    assertEquals(mp3.currentPosition(), 0.0, 0.1);
	    assertFalse(mp3.isPlaying());
	  }

	  public void testAdvance() {

	    mp3.loadSongs(list);

	    mp3.play();

	    expect(mp3.isPlaying()).andReturn(true);
	    replay( mp3_control );
	    assertTrue(mp3.isPlaying());

	    reset(mp3_control); 
	    mp3.prev();
	    expect(mp3.currentSong()).andReturn((String)list.get(0));
	    expect(mp3.isPlaying()).andReturn(true);
	    replay( mp3_control );
	    assertEquals(mp3.currentSong(), (String)list.get(0));
	    assertTrue(mp3.isPlaying());

	    reset(mp3_control); 
	    mp3.next();
	    expect(mp3.currentSong()).andReturn((String)list.get(1));
	    replay( mp3_control );
	    assertEquals(mp3.currentSong(),(String) list.get(1));
	    
	    reset(mp3_control); 
	    mp3.next();
	    expect(mp3.currentSong()).andReturn((String)list.get(2));
	    replay( mp3_control );
	    assertEquals(mp3.currentSong(), (String)list.get(2));
	    
	    reset(mp3_control); 
	    mp3.prev();
	    expect(mp3.currentSong()).andReturn((String)list.get(1));
	    replay( mp3_control );
	    assertEquals(mp3.currentSong(),(String) list.get(1));
	    
	    reset(mp3_control);
	    mp3.next();
	    expect(mp3.currentSong()).andReturn((String)list.get(2));
	    replay( mp3_control );
	    assertEquals(mp3.currentSong(), (String)list.get(2));
	    
	    reset(mp3_control);
	    mp3.next();
	    expect(mp3.currentSong()).andReturn((String)list.get(3));
	    replay( mp3_control );
	    assertEquals(mp3.currentSong(), (String)list.get(3));
	    
	    reset(mp3_control);
	    mp3.next();
	    expect(mp3.isPlaying()).andReturn(true);
	    expect(mp3.currentSong()).andReturn((String)list.get(3));
	    replay( mp3_control );
	    assertEquals(mp3.currentSong(), (String)list.get(3));
	    assertTrue(mp3.isPlaying());
	  }

}
