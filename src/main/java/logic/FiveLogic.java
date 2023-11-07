package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiveLogic {
    
public boolean win(int stoneColor, Map<String, Integer> stoneLocation, List<ArrayList<HashMap<String, Integer>>> stoneArea){
    
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

                
                if(isInRange(x2,y2,stoneArea.size(), stoneArea.get(0).size()) && stoneArea.get(y2).get(x2).get("c") == stoneColor){
                    cnt += 1;
                } else {
                    break;
                }
                
            }
            
            for(int j=1; j<=5; j++){
                int x2 = x + dir2[i][0]*j;
                int y2 = y + dir2[i][1]*j;
                
                if(isInRange(x2,y2,stoneArea.size(), stoneArea.get(0).size()) && stoneArea.get(y2).get(x2).get("c") == stoneColor){
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
    static boolean isInRange(int x, int y, int xSize, int ySize) {
        return (x >= 0 && x < xSize && y >= 0 && y < ySize);
    }
    

}