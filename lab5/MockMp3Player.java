package easymock;
import java.util.ArrayList;

import easymock.Mp3Player;

public class MockMp3Player implements Mp3Player {
	private boolean isPlaying = false;
	private String currentPlaying = null;
	private double currentPosition = 0.0;
	private int currentPlaying_index = -1;
	private ArrayList playlist = new ArrayList();
	

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
		return currentPosition;
	}

	@Override
	public String currentSong(){
		return currentPlaying;
	}

	@Override
	public void play(){
		if(!playlist.isEmpty()){
			isPlaying = true;
			currentPlaying = (String)playlist.get(0);
			currentPlaying_index = 0;
			currentPosition = 0.2;
		}
	}

	@Override
	public void pause(){
		if(isPlaying){
			isPlaying = false;
			currentPosition = 0.2;
		}
	}

	@Override
	public void stop(){
		if(isPlaying){
			isPlaying = false;
			currentPosition = 0.0;
		}
		if(!playlist.isEmpty()){
			currentPlaying = (String)playlist.get(0);
		}
		currentPosition = 0.0;
	}

	@Override
	public void next() {
		int next_pos = currentPlaying_index + 1;
		if (next_pos < playlist.size())
			{
				currentPlaying = (String)playlist.get(next_pos);
				currentPlaying_index = next_pos;
			}
		
	}

	@Override
	public void prev() {
		int prev_pos = currentPlaying_index - 1;
		if (prev_pos > -1){
			currentPlaying = (String)playlist.get(prev_pos);
			currentPlaying_index = prev_pos;
		}
	}
}
