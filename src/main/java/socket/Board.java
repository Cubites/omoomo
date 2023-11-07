package socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private List<List<Map<String, Integer>>> board = new ArrayList<>();
    private int size;
    
    public Board() {
        size = 19;
        for(int i=0; i<size; i++) {
            board.add(new ArrayList<>());
            for(int j=0; j<size; j++) {
                Map<String, Integer> temp = new HashMap<>();
                temp.put("h", 0);
                temp.put("v", 0);
                temp.put("c", 0);
                board.get(i).add(temp);
            }
        }
    }
    
    public List<List<Map<String, Integer>>> getBoard() {
        return board;
    }
    
    // 놓인 돌이 있는지 없는지 확인
    public boolean checkOne(int h, int v) {
        return board.get(v).get(h).get("c") == 0;
    }
    
    //
    public void setStone(int h, int v, int c) {
        this.board.get(v).get(h).put("c", c);
    }
}
