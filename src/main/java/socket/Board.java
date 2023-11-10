package socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private List<List<Map<String, Integer>>> board = new ArrayList<>();
    private int size;
    private int stoneCount;
    private String mode;
    
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
    
    public Board(String mode) {
        stoneCount = 0;
        size = 19;
        this.mode = mode;
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
    
    public String getMode() {
        return mode;
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
    
    // 오목 판별
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
    
    // 33 판별
    public boolean doubleThree(int stoneColor, Map<String, Integer> stoneLocation){

        int color;
        int x = stoneLocation.get("h"); //stone location = {h: 0, v: 0} 오목판의 좌표
        int y = stoneLocation.get("v");
        
        
        // 8방향 ( 오른쪽, 오른쪽 아래, 아래, 왼쪽 아래, 왼쪽, 왼쪽 위, 위, 오른쪽 위 )
        int[][] dxy = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};
        
        
        // 각 방향으로 쭉 가보면서 3개가 되면, 한번 더 가보고 비어있는지 확인 & 원래 좌표에서 반대 방향으로 한칸 갔을 때 비어있는지 확인
        int cnt = 0;
        for (int i=0; i < dxy.length; i++) {
           // 다음 위치
           int nx = x + dxy[i][0];
           int ny = y + dxy[i][1];
           // 이 방향으로 3개가 되는 지 확인
           int triple = 1;
           System.out.println("33확인1 " + i);
           System.out.println("33확인1 " + stoneColor);
           while (isInRange(nx, ny, board.size(), board.get(0).size()) && board.get(ny).get(nx).get("c") == stoneColor) {
               System.out.println("33확인2 " + nx);
               System.out.println("33확인2 " + ny);
              triple += 1;
              nx += dxy[i][0];
              ny += dxy[i][1];
           }
           // 다음 위치가 범위를 벗어나거나 색이 다르면 반복 끝
           // 이 때 3개인지 확인
           // 3개라면 한번 더 가봤을 때 비어있는지 & 원래 위치에서 반대로 한칸 갔을 때 비어있는지 확인
           if (triple == 3) {
               System.out.println("33확인3");
              if (isInRange(nx, ny, board.size(), board.get(0).size()) 
                    && board.get(ny).get(nx).get("c") == 0) {
                  System.out.println("33확인4");
                 // 반대 방향
                 int d = (i + 4) % 8;
                 System.out.println(i);
                 int rev_x = x + dxy[d][0];
                 int rev_y = y + dxy[d][1];
                 if (isInRange(rev_x, rev_y, board.size(), board.get(0).size()) 
                        && board.get(ny).get(nx).get("c") == 0) {
                    cnt += 1;
                    System.out.println("d: " + d + " / " + cnt);
                 }
              }
           }
        }
        // 모든 방향을 다 봤을 때, 조건에 맞는 방향이 2개 이상이라면 33 조건 성립
        if (cnt >= 2) {
           return true;
        }
        return false;
    }
    

    //바둑판 범위 벗어나는지 확인하기
    boolean isInRange(int x, int y, int xSize, int ySize) {
        return (x >= 0 && x < xSize && y >= 0 && y < ySize);
    }
}
