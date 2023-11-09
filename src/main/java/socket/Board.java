package socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private List<List<Map<String, Integer>>> board = new ArrayList<>();
    private int size;
    private int stoneCount;
    
    public Board() {
        stoneCount = 0;
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
    
    public Board(int size) {
        stoneCount = 0;
        this.size = size;
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
    
    // 순서 판별
    public boolean checkTurn(int c) {
        System.out.println(c == -1 ? "Black Stone" : "White Stone");
        System.out.println(stoneCount % 2 == 1 ? "White Turn" : "Black Turn");
        if(stoneCount % 2 == 1 && c == 1) {
            stoneCount++;
            return true;
        } else if(stoneCount % 2 == 0 && c == -1) {
            stoneCount++;
            return true;
        }
        System.out.println("올바르지 않는 턴");
        return false;
    }
    
    // 돌을 놓음
    public void setStone(int h, int v, int c) {
        this.board.get(v).get(h).put("c", c);
    }
    
    // 오목인지 판별
    public boolean win(int stoneColor, Map<String, Integer> stoneLocation){    
        int color;
        int x = stoneLocation.get("h"); //stone location = {h: 0, v: 0} 오목판의 좌표
        int y = stoneLocation.get("v");
        
        int[][] dir1 = {{-1,-1},{-1,1},{1,0},{0,1}};
        int[][] dir2 = {{1,1},{1,-1},{-1,0},{0,-1}};
        
        for(int i=0; i<dir1.length; i++){
            int cnt=1;
            
            for(int j=1; j<=5; j++){
                int x2 = x + dir1[i][0]*j;
                int y2 = y + dir1[i][1]*j;

                
                if(isInRange(x2,y2,board.size(), board.get(0).size()) && board.get(y2).get(x2).get("c") == stoneColor){
                    cnt += 1;
                } else {
                    break;
                }
            }
            
            for(int j=1; j<=5; j++){
                int x2 = x + dir2[i][0]*j;
                int y2 = y + dir2[i][1]*j;
                
                if(isInRange(x2,y2,board.size(), board.get(0).size()) && board.get(y2).get(x2).get("c") == stoneColor){
                    cnt += 1;
                } else {
                    break;
                }
            }
            if(cnt==5){
                return true;
            }
        }
        return false;
    }
    

    //바둑판 범위 벗어나는지 확인하기
    boolean isInRange(int x, int y, int xSize, int ySize) {
        return (x >= 0 && x < xSize && y >= 0 && y < ySize);
    }
}
