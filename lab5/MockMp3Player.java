package easymock;
import java.util.ArrayList;

import easymock.Mp3Player;

public class MockMp3Player implements Mp3Player {
	private boolean isPlaying = false;
	private String nowPlaying = null;
	private double nowPosition = 0.0;
	private ArrayList playlist = new ArrayList();
	private int nowPlaying_index = -1;

	@Override
	public void loadSongs(ArrayList songnames){
		playlist = songnames;
	}

	@Override
	public boolean isPlaying(){
		return isPlaying;
	}

	@Override
	public double currentPosition(){
		return nowPosition;
	}

	@Override
	public String currentSong(){
		return nowPlaying;
	}

	@Override
	public void play(){
		if(!playlist.isEmpty()){
			isPlaying = true;
			nowPlaying = (String)playlist.get(0);
			nowPlaying_index = 0;
			nowPosition = 0.2;
		}
	}

	@Override
	public void pause(){
		if(isPlaying){
			isPlaying = false;
			nowPosition = 0.2;
		}
	}

	@Override
	public void stop(){
		if(isPlaying){
			isPlaying = false;
			nowPosition = 0.0;
		}
		if(!playlist.isEmpty()){
			nowPlaying = (String)playlist.get(0);
		}
		nowPosition = 0.0;
	}

	@Override
	public void next() {
		int next_pos = nowPlaying_index + 1;
		if (next_pos < playlist.size())
			{
				nowPlaying = (String)playlist.get(next_pos);
				nowPlaying_index = next_pos;
			}
		
	}

	@Override
	public void prev() {
		int prev_pos = nowPlaying_index - 1;
		if (prev_pos > -1){
			nowPlaying = (String)playlist.get(prev_pos);
			nowPlaying_index = prev_pos;
		}
	}
}
